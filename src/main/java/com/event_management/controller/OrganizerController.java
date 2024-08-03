package com.event_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.event_management.model.Organizer;
import com.event_management.service.OrganizerService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/organizers")
public class OrganizerController {

    @Autowired
    private OrganizerService organizerService;

    @GetMapping
    public ResponseEntity<List<Organizer>> getAllOrganizers() {
        List<Organizer> organizers = organizerService.getAllOrganizers();
        if (organizers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(organizers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrganizerById(@PathVariable Long id) {
        Organizer organizer = null;
		try {
			organizer = organizerService.getOrganizerById(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (organizer != null) {
            return new ResponseEntity<>(organizer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Organizer not found", HttpStatus.NOT_FOUND);
        }
    }

   
    
    
    
    
    
    @PostMapping
    public ResponseEntity<String> createOrganizer(@RequestBody Organizer organizer) {
        try {
            if (organizerService.existsByName(organizer.getName())) {
                return new ResponseEntity<>("Organizer with the name '" + organizer.getName() + "' already exists.", HttpStatus.CONFLICT);
            }
            
            // Create the new organizer
            Organizer createdOrganizer = organizerService.createOrganizer(organizer);
            return new ResponseEntity<>("Organizer created successfully with ID: " + createdOrganizer.getId(), HttpStatus.CREATED);
            
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create organizer: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrganizer(@PathVariable Long id, @RequestBody Organizer organizer) {
        try {
            organizer.setId(id);
            Organizer updatedOrganizer = organizerService.updateOrganizer(id, organizer);
            if (updatedOrganizer != null) {
                return new ResponseEntity<>("Organizer updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Organizer not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update organizer", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrganizer(@PathVariable Long id) {
        try {
            organizerService.deleteOrganizer(id);
            return new ResponseEntity<>("Organizer deleted successfully", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete organizer", HttpStatus.NOT_FOUND);
        }
    }
}
