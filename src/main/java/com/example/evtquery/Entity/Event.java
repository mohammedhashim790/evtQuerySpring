package com.example.evtquery.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "event",uniqueConstraints = @UniqueConstraint(columnNames = {"eventRecordID"}))
public class Event {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long Id;


    @JsonProperty("System")
    @Embedded
    public System system;

//    @JsonProperty("EventData")
//    @Embedded
//    public EventData eventData;

    @JsonProperty("RenderingInfo")
    @Embedded
    public RenderingInfo renderingInfo;


    @Override
    public String toString() {
        return "Event{" +
                "Id=" + Id +
                ", system=" + system +
                ", renderingInfo=" + renderingInfo +
                '}';
    }
}
