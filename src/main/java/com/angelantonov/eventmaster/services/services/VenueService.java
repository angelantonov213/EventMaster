package com.angelantonov.eventmaster.services.services;

import com.angelantonov.eventmaster.services.model.venue.AllVenueServiceModel;
import com.angelantonov.eventmaster.services.model.venue.CreateVenueServiceModel;
import com.angelantonov.eventmaster.services.model.venue.VenueDetailsServiceModel;
import com.angelantonov.eventmaster.services.model.venue.UpdateVenueServiceModel;

import java.util.List;
import java.util.Optional;

public interface VenueService {
    long createVenue(CreateVenueServiceModel model);
    void updateVenue(UpdateVenueServiceModel model);
    void addEventForVenue(long eventId, long venueId);

    List<AllVenueServiceModel> getAllVenues();
    Optional<VenueDetailsServiceModel> getVenueById(long id);

    List<AllVenueServiceModel> getVenuesForUser(long userId);
}
