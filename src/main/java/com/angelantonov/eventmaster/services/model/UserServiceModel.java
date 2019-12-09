package com.angelantonov.eventmaster.services.model;

import com.angelantonov.eventmaster.data.models.Role;
import com.angelantonov.eventmaster.data.models.Ticket;
import com.angelantonov.eventmaster.data.models.Venue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserServiceModel {
    private long id;
    private String email;
    private String password;
    private List<Role> roles;
    private String name;
    private List<Ticket> tickets;
    private List<Venue> venues;
}
