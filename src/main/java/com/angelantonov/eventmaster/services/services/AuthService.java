package com.angelantonov.eventmaster.services.services;

import com.angelantonov.eventmaster.services.model.auth.*;

public interface AuthService {
    void registerUserWithRoles(RegisterUserWithRolesServiceModel model);
    void refreshPasswordForUser(RefreshPasswordForUserServiceModel model);
    LoginResultServiceModel login(LoginServiceModel model);
    void setRoleForUser(SetRoleForServiceModel model);
}
