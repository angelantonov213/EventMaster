package com.angelantonov.eventmaster.web.models.event;

import com.angelantonov.eventmaster.data.models.Venue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CreateEventModel {
    private String name;
    private String description;
    private String imageUrl;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private double price;
    private Venue venue;
}
