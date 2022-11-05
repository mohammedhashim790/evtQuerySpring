package com.example.evtquery.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class RenderingInfo{
    @JsonProperty("Culture")
    public String culture;
    @JsonProperty("Message")
    public String message;
    @JsonProperty("Level")
    public String level;
//    @JsonProperty("Task")
//    public String task;
    @JsonProperty("Opcode")
    public String opcode;
//    @JsonProperty("Channel")
//    public String channel;
    @JsonProperty("Provider")
    public String provider;
    @JsonProperty("Keywords")
    @Embedded
    public Keywords keywords;


    @Override
    public String toString() {
        return "RenderingInfo{" +
                "culture='" + culture + '\'' +
                ", message='" + message + '\'' +
                ", level='" + level + '\'' +
                ", opcode='" + opcode + '\'' +
                ", provider='" + provider + '\'' +
                ", keywords=" + keywords +
                '}';
    }
}

@Embeddable
class Keywords{
    @JsonProperty("Keyword")
    public String keyword;

    @Override
    public String toString() {
        return "Keywords{" +
                "keyword='" + keyword + '\'' +
                '}';
    }
}