package com.angelantonov.eventmaster.services.model;

import com.angelantonov.eventmaster.data.models.Venue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CreateEventServiceModel {
    private String name;
    private String description;
    private String imageUrl;
    private Date date;
    private double price;
    private Venue venue;
}
