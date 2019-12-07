package com.angelantonov.eventmaster.services.model.auth;


import com.angelantonov.eventmaster.data.models.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RegisterUserWithRolesServiceModel {
    private String email;
    private String password;
    private List<Role> roles;
    private String name;
}
