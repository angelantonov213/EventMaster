package com.angelantonov.eventmaster.services.model;

import com.angelantonov.eventmaster.data.models.Ticket;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EventDetailsServiceModel {
    private long id;
    private String name;
    private String description;
    private String imageUrl;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private double price;
    private String venueName;
    private long venueId;
    private int ticketsSold;
    private int venueCapacity;
    private List<Ticket> tickets;
}
