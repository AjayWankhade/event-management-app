package com.event_management.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.event_management.model.Organizer;

public interface OrganizerRepository extends JpaRepository<Organizer, Long> {

	boolean existsByName(String name);

}
