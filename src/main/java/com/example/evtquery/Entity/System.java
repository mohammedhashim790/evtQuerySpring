package com.example.evtquery.Entity;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class System{
    @JsonProperty("Provider")
    @Embedded
    public Provider provider;
    @JsonProperty("EventID")
    public String eventID;
    @JsonProperty("Version")
    public String version;
//    @JsonProperty("Level")
//    public String level;
    @JsonProperty("Task")
    public String task;
//    @JsonProperty("Opcode")
//    public String opcode;
    @JsonProperty("Keywords")
    public String keywords;
    @Embedded
    @JsonProperty("TimeCreated")
    public TimeCreated timeCreated;
    @JsonProperty("EventRecordID")
    public String eventRecordID;
    @JsonProperty("Channel")
    public String channel;
    @JsonProperty("Computer")
    public String computer;
    @JsonProperty("Security")
    public String security;

    public TimeCreated getTimeCreated() {
        return timeCreated;
    }


    @Override
    public String toString() {
        return "System{" +
                "provider=" + provider +
                ", eventID='" + eventID + '\'' +
                ", version='" + version + '\'' +
                ", task='" + task + '\'' +
                ", keywords='" + keywords + '\'' +
                ", timeCreated=" + timeCreated +
                ", eventRecordID='" + eventRecordID + '\'' +
                ", channel='" + channel + '\'' +
                ", computer='" + computer + '\'' +
                ", security='" + security + '\'' +
                '}';
    }
}



@Embeddable
class Provider{
    @JsonProperty("Name")
    public String name;
    @JsonProperty("Guid")
    public String guid;

    @Override
    public String toString() {
        return "Provider{" +
                "name='" + name + '\'' +
                ", guid='" + guid + '\'' +
                '}';
    }
}
