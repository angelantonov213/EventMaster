package com.angelantonov.eventmaster.data.repositories;

import com.angelantonov.eventmaster.data.models.User;
import com.angelantonov.eventmaster.data.models.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VenuesRepository extends JpaRepository<Venue, Long> {
    List<Venue> findAllByAdminsContains(User user);
}
