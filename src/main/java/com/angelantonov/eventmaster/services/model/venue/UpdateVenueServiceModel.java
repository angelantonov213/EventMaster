package com.angelantonov.eventmaster.services.model.venue;

import com.angelantonov.eventmaster.data.models.Event;
import com.angelantonov.eventmaster.data.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UpdateVenueServiceModel {
    private long id;
    private String name;
    private String address;
    private String description;
    private int capacity;
    private List<User> admins;
    private List<Event> events;
}
