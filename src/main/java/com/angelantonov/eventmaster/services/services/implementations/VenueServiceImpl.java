package com.angelantonov.eventmaster.services.services.implementations;

import com.angelantonov.eventmaster.data.models.Event;
import com.angelantonov.eventmaster.data.models.User;
import com.angelantonov.eventmaster.data.models.Venue;
import com.angelantonov.eventmaster.data.repositories.EventsRepository;
import com.angelantonov.eventmaster.data.repositories.UsersRepository;
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
    private final EventsRepository eventsRepository;
    private final UsersRepository usersRepository;

    public VenueServiceImpl(VenuesRepository venuesRepository, ModelMapper modelMapper, EventsRepository eventsRepository, UsersRepository usersRepository) {
        this.venuesRepository = venuesRepository;
        this.modelMapper = modelMapper;
        this.eventsRepository = eventsRepository;
        this.usersRepository = usersRepository;
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
    public List<AllVenueServiceModel> getVenuesForUser(long userId) {
        Optional<User> optionalUser = usersRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            return List.of();
        }

        return venuesRepository.findAllByAdminsContains(optionalUser.get())
                .stream()
                .map(venue -> modelMapper.map(venue, AllVenueServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void updateVenue(UpdateVenueServiceModel model) {
        Venue venue = modelMapper.map(model, Venue.class);
        venue.setEvents(List.of());
        venuesRepository.save(venue);
    }

    @Override
    public void addEventForVenue(long eventId, long venueId) {
        List<Event> venuesEvents = venuesRepository.findById(venueId).get().getEvents();
        Event eventToAdd = eventsRepository.findById(eventId).get();

        if (!venuesEvents.contains((eventToAdd))) {
            venuesEvents.add(eventToAdd);
            Venue venue = venuesRepository.findById(venueId).get();
            venue.setEvents(venuesEvents);
            venuesRepository.save(venue);
        }
    }
}
