package com.angelantonov.eventmaster.services.services;

import com.angelantonov.eventmaster.services.model.AllVenueServiceModel;
import com.angelantonov.eventmaster.services.model.CreateVenueServiceModel;
import com.angelantonov.eventmaster.services.model.VenueDetailsServiceModel;

import java.util.List;
import java.util.Optional;

public interface VenueService {
    void createVenue(CreateVenueServiceModel model);
    List<AllVenueServiceModel> getAllVenues();
    Optional<VenueDetailsServiceModel> getVenueById(long id);
}
