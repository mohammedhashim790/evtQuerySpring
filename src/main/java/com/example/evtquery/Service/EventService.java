package com.example.evtquery.Service;

import com.example.evtquery.Entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService implements IEvent{

    @Autowired
    private IEventRepository eventRepository;

    @Override
    public List<Event> ListEvents() {
        return this.eventRepository.findAll();
    }

    @Override
    public Event GetEventById(Long Id) {
        return this.eventRepository.findById(Id).get();
    }

    @Override
    public Event Save(Event event) {
        return this.eventRepository.save(event);
    }

    @Override
    public List<Event> Save(List<Event> event) {
        return this.eventRepository.saveAll(event);
    }
}
