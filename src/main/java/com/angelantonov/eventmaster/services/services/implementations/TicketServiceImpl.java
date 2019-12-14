package com.angelantonov.eventmaster.services.services.implementations;

import com.angelantonov.eventmaster.data.models.Ticket;
import com.angelantonov.eventmaster.data.repositories.EventsRepository;
import com.angelantonov.eventmaster.data.repositories.TicketsRepository;
import com.angelantonov.eventmaster.data.repositories.UsersRepository;
import com.angelantonov.eventmaster.services.services.TicketsService;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketsService {
    private final TicketsRepository ticketsRepository;
    private final EventsRepository eventsRepository;
    private final UsersRepository usersRepository;

    public TicketServiceImpl(TicketsRepository ticketsRepository, EventsRepository eventsRepository, UsersRepository usersRepository) {
        this.ticketsRepository = ticketsRepository;
        this.eventsRepository = eventsRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public void addTicketFor(long eventId, long userId) {
        Ticket ticket = new Ticket();
        ticket.setEvent(eventsRepository.findById(eventId).get());
        ticket.setUser(usersRepository.findById(userId).get());

        ticketsRepository.save(ticket);
    }
}
