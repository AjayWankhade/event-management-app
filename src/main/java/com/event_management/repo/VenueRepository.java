package com.event_management.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.event_management.model.Venue;

public interface VenueRepository extends JpaRepository<Venue, Long> {
	 boolean existsByNameAndLocation(String name, String location);
}
