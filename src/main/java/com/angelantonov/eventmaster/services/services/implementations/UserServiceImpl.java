package com.angelantonov.eventmaster.services.services.implementations;

import com.angelantonov.eventmaster.data.models.Role;
import com.angelantonov.eventmaster.data.models.User;
import com.angelantonov.eventmaster.data.models.Venue;
import com.angelantonov.eventmaster.data.repositories.UsersRepository;
import com.angelantonov.eventmaster.data.repositories.VenuesRepository;
import com.angelantonov.eventmaster.services.model.UserServiceModel;
import com.angelantonov.eventmaster.services.model.UsersForRoleServiceModel;
import com.angelantonov.eventmaster.services.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UsersRepository usersRepository;
    private final VenuesRepository venuesRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UsersRepository usersRepository, VenuesRepository venuesRepository, ModelMapper modelMapper) {
        this.usersRepository = usersRepository;
        this.venuesRepository = venuesRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UsersForRoleServiceModel> getUsersForRole(Role role) {
        return usersRepository.findByRolesEquals(role)
                .stream()
                .map(user -> modelMapper.map(user, UsersForRoleServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addVenueForUser(long venueId, long userId) {
        List<Venue> usersVenues = usersRepository.findById(userId).get().getVenues();
        Venue venueToAdd = venuesRepository.findById(venueId).get();

        if (usersVenues.contains(venueToAdd) == false) {
            usersVenues.add(venueToAdd);
            User user = usersRepository.findById(userId).get();
            user.setVenues(usersVenues);
            usersRepository.save(user);
        }
    }

    @Override
    public void removeVenueForUser(long venueId, long userId) {
        List<Venue> usersVenues = usersRepository.findById(userId).get().getVenues();
        Venue venueToAdd = venuesRepository.findById(venueId).get();

        if (usersVenues.contains(venueToAdd)) {
            usersVenues.remove(venueToAdd);
            User user = usersRepository.findById(userId).get();
            user.setVenues(usersVenues);
            usersRepository.save(user);
        }
    }

    @Override
    public Optional<UserServiceModel> getUserById(long userId) {
        Optional<User> optionalUser = usersRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            return null;
        }

        return Optional.of(modelMapper.map(optionalUser.get(), UserServiceModel.class));
    }
}
