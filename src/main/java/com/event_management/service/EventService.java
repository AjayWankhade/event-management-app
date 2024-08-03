package com.event_management.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.event_management.model.EventModel;
import com.event_management.repo.EventRepository;


@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Transactional
    public EventModel createEvent(EventModel event) {
        return eventRepository.save(event);
    }

    @Transactional
    public EventModel updateEvent(Long id, EventModel event) throws Exception {
        // Check if the event exists before updating
        if (!eventRepository.existsById(id)) {
            throw new Exception("Event not found with id: " + id);
        }
        event.setId(id);
        return eventRepository.save(event);
    }

    @Transactional
    public void deleteEvent(Long id) throws Exception {
        try {
            eventRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new Exception("Event not found with id: " + id);
        }
    }

    public EventModel getEventById(Long id) throws Exception {
        return eventRepository.findById(id)
            .orElseThrow(() -> new Exception("Event not found with id: " + id));
    }

    public List<EventModel> getAllEvents() {
        return eventRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return eventRepository.existsById(id);
    }

}
