package com.angelantonov.eventmaster.data.repositories;

import com.angelantonov.eventmaster.data.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketsRepository extends JpaRepository<Ticket, Long> {
}
