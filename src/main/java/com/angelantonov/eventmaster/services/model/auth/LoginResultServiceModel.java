package com.angelantonov.eventmaster.services.model.auth;


import com.angelantonov.eventmaster.data.models.Ticket;
import com.angelantonov.eventmaster.data.models.Venue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LoginResultServiceModel {
    private String email;
    private String password;
    private List<Ticket> tickets;
    private String role;
    private String name;
    private List<Venue> venues;
}