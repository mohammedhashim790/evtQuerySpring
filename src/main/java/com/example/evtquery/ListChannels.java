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

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


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
    public String List(@PathVariable(value="query") String query) {
        try{
            if(query.isEmpty()){
                return "Invalid Query Param";
            }

            System.out.println(query);
            String res = new Resolver().Query(query);

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
    public String NextList(
            @PathVariable(value="query") String query,
            @PathVariable(value="fromEventRecordId") int fromEventRecordId
    ) {
        try{
            if(query.isEmpty()){
                return "Invalid Query Param";
            }

            System.out.println(query);
            String res = new Resolver().QueryChannelsNext(query,fromEventRecordId);

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



    @GetMapping("/Get")
    public EventParams GetEvents(){
        try{

            List<Event> events = new ArrayList<>();

            events = this.eventService.ListEvents();

            events.sort(new Comparator<Event>() {
                @Override
                public int compare(Event ev1, Event ev2) {
                    return (
                            Integer.parseInt(ev2.system.eventRecordID) -
                            Integer.parseInt(ev1.system.eventRecordID)
                            );
                }
            });

            EventParams eventParams = new EventParams();
            eventParams.event = events;
            return eventParams;


        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/Refresh")
    public ResponseEntity<String> RefreshEvents(){
        try{
            String res = new Resolver().GetEvents("Security");

//            System.out.println(res);
//            Convert XML-formatted String to JSON
            XmlMapper xml = new XmlMapper();
            JsonNode node = xml.readTree(res);
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            EventParams eventList = mapper.readValue(node.toString(),EventParams.class);

            for(Event event : eventList.event){
                try{
                    this.eventService.Save(event);
                }catch (Exception e){

                }
            }

            return new ResponseEntity<>("OK", HttpStatus.OK);


        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("FAILED", HttpStatus.BAD_REQUEST);

    }





}