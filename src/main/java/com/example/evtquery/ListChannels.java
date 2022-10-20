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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.nio.charset.StandardCharsets;


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

    @GetMapping("/List")
    public String List() {
        try{
            String test = "<note>\n" +
                    "<to>Tove</to>\n" +
                    "<from>Jani</from>\n" +
                    "<heading>Reminder</heading>\n" +
                    "<body>Don't forget me this weekend!</body>\n" +
                    "</note>";
            String res = new Resolver().Query();

            System.out.println(res);

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




}