package com.example.evtquery;
import Resolvers.Resolver;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.fge.jackson.JsonLoader;
import org.json.JSONException;
import org.json.XML;
import org.json.JSONObject;
import org.json.XMLTokener;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.nio.charset.StandardCharsets;


@CrossOrigin(origins = "*")
@RestController
public class ListChannels {

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
            String test = "<note>\n" +
                    "<to>Tove</to>\n" +
                    "<from>Jani</from>\n" +
                    "<heading>Reminder</heading>\n" +
                    "<body>Don't forget me this weekend!</body>\n" +
                    "</note>";
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
            String test = "<note>\n" +
                    "<to>Tove</to>\n" +
                    "<from>Jani</from>\n" +
                    "<heading>Reminder</heading>\n" +
                    "<body>Don't forget me this weekend!</body>\n" +
                    "</note>";
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



}