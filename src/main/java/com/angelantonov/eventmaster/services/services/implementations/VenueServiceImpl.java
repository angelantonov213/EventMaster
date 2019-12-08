package com.angelantonov.eventmaster.services.services.implementations;

import com.angelantonov.eventmaster.data.models.Venue;
import com.angelantonov.eventmaster.data.repositories.VenuesRepository;
import com.angelantonov.eventmaster.services.model.AllVenueServiceModel;
import com.angelantonov.eventmaster.services.model.CreateVenueServiceModel;
import com.angelantonov.eventmaster.services.model.VenueDetailsServiceModel;
import com.angelantonov.eventmaster.services.model.venue.UpdateVenueServiceModel;
import com.angelantonov.eventmaster.services.services.VenueService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VenueServiceImpl implements VenueService {
    private final VenuesRepository venuesRepository;
    private final ModelMapper modelMapper;

    public VenueServiceImpl(VenuesRepository venuesRepository, ModelMapper modelMapper) {
        this.venuesRepository = venuesRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public long createVenue(CreateVenueServiceModel model) {
        Venue venue = venuesRepository.save(modelMapper.map(model, Venue.class));
        return venue.getId();
    }

    @Override
    public List<AllVenueServiceModel> getAllVenues() {
        return venuesRepository.findAll()
                .stream()
                .map(venue -> modelMapper.map(venue, AllVenueServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<VenueDetailsServiceModel> getVenueById(long id) {
        Optional<Venue> optionalVenue = venuesRepository.findById(id);

        if (optionalVenue.isEmpty()) {
            return null;
        }

        return Optional.of(modelMapper.map(optionalVenue.get(), VenueDetailsServiceModel.class));
    }

    @Override
    public void updateVenue(UpdateVenueServiceModel model) {
        Venue venue = modelMapper.map(model, Venue.class);
        venue.setEvents(List.of());
        venuesRepository.save(venue);
    }
}
