package com.example.evtquery.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class EventData{
    @JsonProperty("Data")
    public List<EvtData> data;

    @Override
    public String toString() {
        return "EventData{" +
                "data=" + data +
                '}';
    }
}

@Embeddable
class EvtData{
    @JsonProperty("Name")
    public String name;
    @JsonProperty("")
    public String value;

    @Override
    public String toString() {
        return "EvtData{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}