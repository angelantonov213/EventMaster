package com.angelantonov.eventmaster.web.models;

import com.angelantonov.eventmaster.data.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateVenueModel {
    private String address;
    private int capacity;
    private String name;
    private String description;
    private List<User> admins;
}
