package com.angelantonov.eventmaster.web.models.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ForgottenPasswordModel {
    private String email;
}
