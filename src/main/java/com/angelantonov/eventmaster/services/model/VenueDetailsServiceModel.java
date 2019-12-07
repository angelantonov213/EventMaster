package com.angelantonov.eventmaster.services.model;

import com.angelantonov.eventmaster.data.models.Event;
import com.angelantonov.eventmaster.data.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class VenueDetailsServiceModel {
    private long id;
    private String name;
    private String address;
    private String description;
    private int capacity;
    private List<User> admins;
    private List<Event> events;
}
