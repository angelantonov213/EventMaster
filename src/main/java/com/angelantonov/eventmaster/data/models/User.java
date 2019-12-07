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
@Table(name = "users")
public class User extends BaseEntity {

    // TODO: Unique as well
    @Column
    private String email;

    @Column
    private String password;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Column
    private List<Role> roles;

    @Column
    private String name;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Ticket> tickets;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "venue_admins",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "venue_id")}
    )
    private List<Venue> venues;
}
