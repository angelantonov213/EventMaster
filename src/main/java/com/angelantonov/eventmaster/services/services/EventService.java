package com.angelantonov.eventmaster.services.services;

import com.angelantonov.eventmaster.services.model.CreateEventServiceModel;
import com.angelantonov.eventmaster.services.model.EventDetailsServiceModel;

import java.util.Optional;

public interface EventService {
    long createEvent(CreateEventServiceModel model);
    Optional<EventDetailsServiceModel> getEventById(long id);

    void updateEvent(EventDetailsServiceModel event);
}
