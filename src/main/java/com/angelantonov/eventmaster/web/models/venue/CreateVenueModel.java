package com.angelantonov.eventmaster.web.models.venue;

import com.angelantonov.eventmaster.data.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateVenueModel {
    private List<User> admins;
    private String address;
    private int capacity;
    private String name;
    private String description;
}
