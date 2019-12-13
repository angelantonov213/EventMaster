package com.angelantonov.eventmaster.data.repositories;

import com.angelantonov.eventmaster.data.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketsRepository extends JpaRepository<Ticket, Long> {
    int countTicketByEvent_Id(long eventId);
    List<Ticket> getTicketsByEvent_Id(long eventId);
}
