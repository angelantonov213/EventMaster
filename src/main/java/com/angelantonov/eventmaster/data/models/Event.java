package com.angelantonov.eventmaster.data.models;

import com.angelantonov.eventmaster.data.models.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event extends BaseEntity {
    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String imageUrl;

    @Column
    private Date date;

    @Column
    private double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "venue_id"
    )
    private Venue venue;

    @OneToMany(mappedBy = "event", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Ticket> tickets;
}
