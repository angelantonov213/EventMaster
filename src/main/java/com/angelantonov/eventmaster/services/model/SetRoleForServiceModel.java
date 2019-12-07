package com.angelantonov.eventmaster.services.model;


import com.angelantonov.eventmaster.data.models.Role;
import com.angelantonov.eventmaster.data.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SetRoleForServiceModel {
    private User user;
    private List<Role> roles;
}
