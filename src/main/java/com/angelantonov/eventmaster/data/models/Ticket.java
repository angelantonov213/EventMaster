package com.angelantonov.eventmaster.data.models;

import com.angelantonov.eventmaster.data.models.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "event_id"
    )
    private Event event;

    @ManyToOne()
    @JoinColumn(
            name = "user_id"
    )
    private User user;
}
