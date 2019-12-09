package com.angelantonov.eventmaster.services.services.implementations;

import com.angelantonov.eventmaster.data.models.Event;
import com.angelantonov.eventmaster.data.repositories.EventsRepository;
import com.angelantonov.eventmaster.services.model.CreateEventServiceModel;
import com.angelantonov.eventmaster.services.services.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {
    private final EventsRepository eventsRepository;
    private final ModelMapper modelMapper;

    public EventServiceImpl(EventsRepository eventsRepository, ModelMapper modelMapper) {
        this.eventsRepository = eventsRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public long createEvent(CreateEventServiceModel model) {
        return eventsRepository.save(modelMapper.map(model, Event.class)).getId();
    }
}
