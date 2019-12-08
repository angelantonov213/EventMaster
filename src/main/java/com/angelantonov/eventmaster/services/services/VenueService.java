package com.angelantonov.eventmaster.services.services;

import com.angelantonov.eventmaster.services.model.AllVenueServiceModel;
import com.angelantonov.eventmaster.services.model.CreateVenueServiceModel;
import com.angelantonov.eventmaster.services.model.VenueDetailsServiceModel;
import com.angelantonov.eventmaster.services.model.venue.UpdateVenueServiceModel;

import java.util.List;
import java.util.Optional;

public interface VenueService {
    long createVenue(CreateVenueServiceModel model);
    List<AllVenueServiceModel> getAllVenues();
    Optional<VenueDetailsServiceModel> getVenueById(long id);
    void updateVenue(UpdateVenueServiceModel model);
}
