package com.angelantonov.eventmaster.data.repositories;

import com.angelantonov.eventmaster.data.models.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenuesRepository extends JpaRepository<Venue, Long> {
}
