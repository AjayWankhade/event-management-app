package com.event_management.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.event_management.model.Organizer;
import com.event_management.repo.OrganizerRepository;

@Service
public class OrganizerService {

    @Autowired
    private OrganizerRepository organizerRepository;

    @Transactional(readOnly = true)
    public List<Organizer> getAllOrganizers() {
        return organizerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Organizer getOrganizerById(Long id) throws Exception {
        return organizerRepository.findById(id)
            .orElseThrow(() -> new Exception("Organizer not found with id: " + id));
    }

    @Transactional
    public Organizer createOrganizer(Organizer organizer) throws Exception {
        // Check if an organizer with the same name already exists
        if (organizerRepository.existsByName(organizer.getName())) {
            throw new Exception("Organizer with the name '" + organizer.getName() + "' already exists.");
        }
        return organizerRepository.save(organizer);
    }

    @Transactional
    public Organizer updateOrganizer(Long id, Organizer organizer) throws Exception {
        Organizer existingOrganizer = organizerRepository.findById(id)
            .orElseThrow(() -> new Exception("Organizer not found with id: " + id));
        
        // Update fields as needed
        existingOrganizer.setName(organizer.getName());
        existingOrganizer.setContactInfo(organizer.getContactInfo());
        // Save the updated organizer
        return organizerRepository.save(existingOrganizer);
    }

    @Transactional
    public void deleteOrganizer(Long id) throws Exception {
        try {
            organizerRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new Exception("Organizer not found with id: " + id);
        }
    }
    
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return organizerRepository.existsByName(name);
    }

}
