package com.angelantonov.eventmaster.services.services.implementations;

import com.angelantonov.eventmaster.data.models.Venue;
import com.angelantonov.eventmaster.data.repositories.VenuesRepository;
import com.angelantonov.eventmaster.services.model.CreateVenueServiceModel;
import com.angelantonov.eventmaster.services.services.VenueService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class VenueServiceImpl implements VenueService {
    private final VenuesRepository venuesRepository;
    private final ModelMapper modelMapper;

    public VenueServiceImpl(VenuesRepository venuesRepository, ModelMapper modelMapper) {
        this.venuesRepository = venuesRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createVenue(CreateVenueServiceModel model) {
        venuesRepository.save(modelMapper.map(model, Venue.class));
    }
}
