package com.example.evtquery.Service;

import com.example.evtquery.Entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService implements IEvent{

    @Autowired
    private IEventRepository eventRepository;

    @Override
    public List<Event> ListEvents(int pageNumber) {
        return this.eventRepository.findAll(
                PageRequest.of(pageNumber, 25, Sort.by(Sort.Direction.DESC,"System.eventRecordID"))).get().collect(Collectors.toList());
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
