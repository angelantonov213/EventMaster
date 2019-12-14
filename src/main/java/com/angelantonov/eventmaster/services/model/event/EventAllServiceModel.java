package com.angelantonov.eventmaster.services.model.event;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class EventAllServiceModel {
    private long id;
    private String name;
    private String description;
    private String imageUrl;
    private Date date;
    private double price;
    private long venueId;
    private String venueName;
}
