package com.angelantonov.eventmaster.services.model.venue;

import com.angelantonov.eventmaster.data.models.User;

import java.util.List;

public class CreateVenueServiceModel {
    private String address;
    private String name;
    private String description;
    private int capacity;
    private List<User> admins;
}
