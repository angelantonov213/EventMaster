package com.angelantonov.eventmaster.services.services;

import com.angelantonov.eventmaster.services.model.event.CreateEventServiceModel;
import com.angelantonov.eventmaster.services.model.event.EventAllServiceModel;
import com.angelantonov.eventmaster.services.model.event.EventDetailsServiceModel;

import java.util.List;
import java.util.Optional;

public interface EventService {
    long createEvent(CreateEventServiceModel model);
    void updateEvent(EventDetailsServiceModel event);

    Optional<EventDetailsServiceModel> getEventById(long id);
    List<EventAllServiceModel> getAllEvents();
    List<EventAllServiceModel> getAllEventsThatContainStringInNameOrDescription(String searchText);
}
