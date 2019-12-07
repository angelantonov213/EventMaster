package com.angelantonov.eventmaster.data.repositories;

import com.angelantonov.eventmaster.data.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsRepository extends JpaRepository<Event, Long> {
}
