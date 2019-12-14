package com.angelantonov.eventmaster.services.services.implementations;

import com.angelantonov.eventmaster.data.models.Event;
import com.angelantonov.eventmaster.data.repositories.EventsRepository;
import com.angelantonov.eventmaster.data.repositories.TicketsRepository;
import com.angelantonov.eventmaster.services.model.event.CreateEventServiceModel;
import com.angelantonov.eventmaster.services.model.event.EventAllServiceModel;
import com.angelantonov.eventmaster.services.model.event.EventDetailsServiceModel;
import com.angelantonov.eventmaster.services.services.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public void updateEvent(EventDetailsServiceModel eventModel) {
        Event event = modelMapper.map(eventModel, Event.class);
        event.setTickets(ticketsRepository.getTicketsByEvent_Id(event.getId()));

        eventsRepository.save(event);
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
    public List<EventAllServiceModel> getAllEvents() {
        List<Event> allEvents = eventsRepository.findAll();
        return allEvents.stream()
                .map(event -> {
                        EventAllServiceModel mapped = modelMapper.map(event, EventAllServiceModel.class);
                        mapped.setVenueId(event.getVenue().getId());
                        mapped.setVenueName(event.getVenue().getName());

                        return mapped;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<EventAllServiceModel> getAllEventsThatContainStringInNameOrDescription(String searchText) {
        List<Event> allEvents = eventsRepository.findEventByNameContainingOrDescriptionContaining(searchText, searchText);
        return allEvents.stream()
                .map(event -> {
                    EventAllServiceModel mapped = modelMapper.map(event, EventAllServiceModel.class);
                    mapped.setVenueId(event.getVenue().getId());
                    mapped.setVenueName(event.getVenue().getName());

                    return mapped;
                })
                .collect(Collectors.toList());
    }
}
