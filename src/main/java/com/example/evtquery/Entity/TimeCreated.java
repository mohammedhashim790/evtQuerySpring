package com.example.evtquery.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;

@Embeddable
public class TimeCreated{
    @JsonProperty("SystemTime")
    public String systemTime;

    @Override
    public String toString() {
        return "TimeCreated{" +
                "systemTime='" + systemTime + '\'' +
                '}';
    }
}
