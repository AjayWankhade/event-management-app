package com.event_management.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.event_management.model.Venue;
import com.event_management.repo.VenueRepository;

@Service
public class VenueService {

    @Autowired
    private VenueRepository venueRepository;

    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    public Venue getVenueById(Long id) {
        return venueRepository.findById(id).orElse(null);
    }

    public Venue createVenue(Venue venue) {
        // Check if a venue with the same name and location already exists
        // Customize the check based on your requirements (e.g., name + location)
        if (venueRepository.existsByNameAndLocation(venue.getName(), venue.getLocation())) {
            throw new RuntimeException("Venue already exists with the same name and location");
        }

        // Optionally add validation or checks before saving
        return venueRepository.save(venue);
    }

    public Venue updateVenue(Long id, Venue venue) {
        // Check if the venue exists before updating
        if (venueRepository.existsById(id)) {
            venue.setId(id);  // Ensure the id is set for the update
            return venueRepository.save(venue);
        }
        return null; // or throw an exception if preferred
    }

    public void deleteVenue(Long id) {
        if (venueRepository.existsById(id)) {
            venueRepository.deleteById(id);
        } else {
            // Throwing an exception with a meaningful message
            throw new IllegalArgumentException("Venue not found with id: " + id);
        }
    }

}
