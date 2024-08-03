package com.event_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.event_management.model.EventModel;
import com.event_management.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventModel>> getAllEvents() {
        List<EventModel> events = eventService.getAllEvents();
        if (events.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Long id) {
        try {
            EventModel event = eventService.getEventById(id);
            return new ResponseEntity<>(event, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Event not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> createEvent(@RequestBody EventModel event) {
        try {
            EventModel createdEvent = eventService.createEvent(event);
            return new ResponseEntity<>("Event created successfully with ID: " + createdEvent.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create event", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEvent(@PathVariable Long id, @RequestBody EventModel event) {
        try {
            EventModel updatedEvent = eventService.updateEvent(id, event);
            if (updatedEvent != null) {
                return new ResponseEntity<>("Event updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Event not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update event", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
        try {
            // Check if the event exists before attempting to delete it
            if (!eventService.existsById(id)) {
                return new ResponseEntity<>("Event not found with ID: " + id, HttpStatus.NOT_FOUND);
            }

            eventService.deleteEvent(id);
            return new ResponseEntity<>("Event deleted successfully", HttpStatus.NO_CONTENT);
            
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete event: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
