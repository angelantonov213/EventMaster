package com.angelantonov.eventmaster.services.services;

import com.angelantonov.eventmaster.data.models.Role;
import com.angelantonov.eventmaster.services.model.UsersForRoleServiceModel;

import java.util.List;

public interface UserService {
    List<UsersForRoleServiceModel> getUsersForRole(Role role);
    void addVenueForUser(long venueId, long userId);
    void removeVenueForUser(long venueId, long userId);
}
