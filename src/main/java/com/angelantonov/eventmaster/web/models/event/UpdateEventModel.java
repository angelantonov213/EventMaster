package com.angelantonov.eventmaster.web.models.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UpdateEventModel {
    private long Id;
    private String name;
    private String description;
    private String imageUrl;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private double price;
    private long venueId;
}
