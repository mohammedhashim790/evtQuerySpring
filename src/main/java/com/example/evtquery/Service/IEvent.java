package com.example.evtquery.Service;

import com.example.evtquery.Entity.Event;

import java.util.List;

public interface IEvent {

    List<Event> ListEvents(int pageNumber);

    Event GetEventById(Long Id);



    Event Save(Event vent);


    List<Event> Save(List<Event> event);



}
