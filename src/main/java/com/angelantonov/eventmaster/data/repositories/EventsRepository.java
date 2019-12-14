package com.angelantonov.eventmaster.data.repositories;

import com.angelantonov.eventmaster.data.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventsRepository extends JpaRepository<Event, Long> {
    List<Event> findEventByNameContainingOrDescriptionContaining(String name, String description);
}
