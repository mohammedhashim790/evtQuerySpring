package com.example.evtquery;
import Resolvers.Resolver;
import com.example.evtquery.Entity.Event;
import com.example.evtquery.Service.EventService;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.io.File;
import java.util.*;


class EventParams{
    @JsonProperty("Event")
    List<Event> event;

    @Override
    public String toString() {
        return "EventParams{" +
                "event=" + event +
                '}';
    }
}


@CrossOrigin(origins = "*")
@RestController
public class ListChannels {

    @Autowired
    private EventService eventService;

    private static Queue<String> events = new LinkedList<String>();

    private Thread thread;
    private boolean heapOver = false;

    @GetMapping("/")
    public String index() {
        return new File("./").getAbsolutePath();
    }

    @GetMapping("/Add")
    public Integer Add() {
        return new Resolver().Add(10,12);
    }


    /**
     * Returns First 25 fetched Records sorted by EventRecordID
     * @param query
     * @return
     */
    @GetMapping("/List/{query}")
    public List<Event> List(
            @PathVariable(value="query") String query,
            @RequestParam(defaultValue = "0") int page
    ) {
        try{
            System.out.println(page);
            return this.eventService.ListEvents(page);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @GetMapping("/Hi")
    public String SayHi() {
        return (String) new Resolver().QueryObject();
    }


    /**
     * Returns Next List from the current EventRecordID
     * @param query
     * @param fromEventRecordId
     * @return
     */
    @GetMapping("/List/Next/{query}/{fromEventRecordId}")
    public void NextList(
            @PathVariable(value="query") String query,
            @PathVariable(value="fromEventRecordId") int fromEventRecordId
    ) {
        try{
            heapOver = false;
            thread = new Thread(){
                @Override
                public void run() {
                    System.out.println("Persistence Thread started");
                    while(!events.isEmpty() || !heapOver){
                        PersistData();
                    }

                    System.out.println("***************************");
                    System.out.println("\n\nThread Finished \n\n ");
                    System.out.println("***************************");

                }
            };
            thread.setDaemon(true);
            thread.start();
            new Resolver().QueryChannelsNext(query,fromEventRecordId);

            heapOver = true;

        }catch (Exception e)
        {
            e.getMessage();
        }
    }


    /**
     * Returns Previous list from the present EventRecordId
     * @param query
     * @param fromEventRecordId
     * @return
     */
    @GetMapping("/List/Prev/{query}/{fromEventRecordId}")
    public String PrevList(
            @PathVariable(value="query") String query,
            @PathVariable(value="fromEventRecordId") int fromEventRecordId
    ) {
        try{
            if(query.isEmpty()){
                return "Invalid Query Param";
            }

            System.out.println(query);
            String res = new Resolver().QueryChannelsPrev(query,fromEventRecordId);

            System.out.println(res);
//            Convert XML-formatted String to JSON
            XmlMapper xml = new XmlMapper();
            JsonNode node = xml.readTree(res);

            return node.toPrettyString();
        }catch (Exception e)
        {
            return e.getMessage();
        }
    }


    /**
     *
     * Returns Total Number of Logs in Channel ∀ query
     *
     * @param query
     * @return
     */
    @GetMapping("/List/Total/{query}")
    public String TotalLogs(
            @PathVariable(value="query") String query
    ) {
        try{
            if(query.isEmpty()){
                return "Invalid Query Param";
            }
            int res = new Resolver().GetTotalLogsFromChannel(query);

            System.out.println(res);

            return "{\"" +query + "\" : " + res + "}";
        }catch (Exception e)
        {
            return e.getMessage();
        }
    }

    @PostMapping("/Refresh")
    public ResponseEntity<String> RefreshEvents(){
        try{
//            String res = new Resolver().GetEvents("Security");

            heapOver = false;
            thread = new Thread(){
                @Override
                public void run() {
                    System.out.println("Persistence Thread started");
                    while(!events.isEmpty() || !heapOver){
                        PersistData();
                    }

                    System.out.println("***************************");
                    System.out.println("\n\nThread Finished \n\n ");
                    System.out.println("***************************");

                }
            };
            thread.setDaemon(true);
            thread.start();
            new Resolver().FetchAllData("Security");

            heapOver = true;

//            System.out.println(res);
//            Convert XML-formatted String to JSON




            return new ResponseEntity<>("OK", HttpStatus.OK);


        }catch (Exception e){
            heapOver = true;
            thread = null;
            e.printStackTrace();
        }
        return new ResponseEntity<>("FAILED", HttpStatus.BAD_REQUEST);

    }


    @GetMapping("/test")
    public void Test() {
        new Resolver().FetchAllData("Security");
    }



    private static void EventReceiver(byte[] input){
        String res = new String(input);

//        System.out.println("Recieved");
//        System.out.println(res.length());
        if(events!=null && res.length()>0)
            events.add(res);

    }

    private void PersistData(){
        if(!events.isEmpty()){
            try{
                String res = events.poll();
                XmlMapper xml = new XmlMapper();
                JsonNode node = xml.readTree(res);
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                EventParams eventList = mapper.readValue(node.toString(),EventParams.class);

                for(Event event : eventList.event) {
                    try {
                        this.eventService.Save(event);
                    } catch (Exception e) {

                    }
                }

            }catch (Exception e){
//                System.err.println(e);
            }
        }
    }


//    @GetMapping("/Get")
//    public EventParams GetEvents(){
//        try{
//
//            List<Event> events = new ArrayList<>();
//
//            events = this.eventService.ListEvents();
//
//            events.sort(new Comparator<Event>() {
//                @Override
//                public int compare(Event ev1, Event ev2) {
//                    return (
//                            Integer.parseInt(ev2.system.eventRecordID) -
//                                    Integer.parseInt(ev1.system.eventRecordID)
//                    );
//                }
//            });
//
//            EventParams eventParams = new EventParams();
//            eventParams.event = events;
//            return eventParams;
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }



}