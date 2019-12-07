package com.angelantonov.eventmaster.services.model.auth;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RefreshPasswordForUserServiceModel {
    private String email;
}
