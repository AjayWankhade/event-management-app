package com.event_management.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.event_management.model.EventModel;
import com.event_management.model.Organizer;
import com.event_management.model.Venue;

public interface EventRepository extends JpaRepository<EventModel, Long> {
	
	   List<EventModel> findByVenue(Venue venue); 
	  List<EventModel> findByOrganizer(Organizer organizer);
	  boolean existsById(Long id);

	  
}
