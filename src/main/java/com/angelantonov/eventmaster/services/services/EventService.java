package com.angelantonov.eventmaster.services.services;

import com.angelantonov.eventmaster.services.model.CreateEventServiceModel;

public interface EventService {
    long createEvent(CreateEventServiceModel model);
}
