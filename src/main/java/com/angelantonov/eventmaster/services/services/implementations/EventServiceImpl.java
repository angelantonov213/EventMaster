package com.angelantonov.eventmaster.services.services.implementations;

import com.angelantonov.eventmaster.data.models.Event;
import com.angelantonov.eventmaster.data.repositories.EventsRepository;
import com.angelantonov.eventmaster.data.repositories.TicketsRepository;
import com.angelantonov.eventmaster.services.model.CreateEventServiceModel;
import com.angelantonov.eventmaster.services.model.EventDetailsServiceModel;
import com.angelantonov.eventmaster.services.services.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {
    private final EventsRepository eventsRepository;
    private final TicketsRepository ticketsRepository;
    private final ModelMapper modelMapper;

    public EventServiceImpl(EventsRepository eventsRepository, TicketsRepository ticketsRepository, ModelMapper modelMapper) {
        this.eventsRepository = eventsRepository;
        this.ticketsRepository = ticketsRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public long createEvent(CreateEventServiceModel model) {
        return eventsRepository.save(modelMapper.map(model, Event.class)).getId();
    }

    @Override
    public Optional<EventDetailsServiceModel> getEventById(long id) {
        Optional<Event> optionalEvent = eventsRepository.findById(id);

        if (optionalEvent.isEmpty()) {
            return null;
        }

        Optional<EventDetailsServiceModel> eventDetails =
                Optional.of(modelMapper.map(optionalEvent.get(), EventDetailsServiceModel.class));

        eventDetails.get().setVenueId(optionalEvent.get().getVenue().getId());
        eventDetails.get().setVenueName(optionalEvent.get().getVenue().getName());
        eventDetails.get().setVenueCapacity(optionalEvent.get().getVenue().getCapacity());

        int soldTickets = ticketsRepository.countTicketByEvent_Id(optionalEvent.get().getId());
        eventDetails.get().setTicketsSold(soldTickets);

        return eventDetails;
    }

    @Override
    public void updateEvent(EventDetailsServiceModel eventModel) {
        Event event = modelMapper.map(eventModel, Event.class);
        event.setTickets(ticketsRepository.getTicketsByEvent_Id(event.getId()));

        eventsRepository.save(event);
    }
}
