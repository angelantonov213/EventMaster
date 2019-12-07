package com.angelantonov.eventmaster.web.models;

import com.angelantonov.eventmaster.data.models.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RegisterUserModel {
    private String email;
    private String password;
    private String confirmPassword;
    private String name;
    private List<Role> roles;
}
