package com.angelantonov.eventmaster.data.models;

import com.angelantonov.eventmaster.data.models.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "venues")
public class Venue extends BaseEntity {

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private String description;

    @Column
    private int capacity;

    @ManyToMany(mappedBy = "venues")
    private List<User> admins;

    @OneToMany(mappedBy = "venue", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Event> events;
}
