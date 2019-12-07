package com.angelantonov.eventmaster.web.controllers;

import com.angelantonov.eventmaster.data.models.Role;
import com.angelantonov.eventmaster.services.model.LoginResultServiceModel;
import com.angelantonov.eventmaster.services.model.LoginServiceModel;
import com.angelantonov.eventmaster.services.model.RegisterUserWithRolesServiceModel;
import com.angelantonov.eventmaster.services.services.AuthService;
import com.angelantonov.eventmaster.web.models.LoginUserModel;
import com.angelantonov.eventmaster.web.models.RegisterUserModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class AuthController {
    private final AuthService authService;
    private final ModelMapper modelMapper;

    public AuthController(AuthService authService, ModelMapper modelMapper) {
        this.authService = authService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public String getLoginForm() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String getRegisterForm() {
        return "auth/register";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginUserModel model) {
        LoginResultServiceModel loginResultServiceModel = authService.login(modelMapper.map(model, LoginServiceModel.class));

        if (loginResultServiceModel.getEmail() != null) {
            return "redirect:/home";
        }

        return "redirect:/users/login";
    }

    @PostMapping("register")
    public String registerUser(@ModelAttribute RegisterUserModel model) {
        model.setRoles(List.of(Role.USER));
        authService.registerUserWithRoles(modelMapper.map(model, RegisterUserWithRolesServiceModel.class));

        return "redirect:/users/login";
    }

    @PostMapping("registerAdmin")
    public String registerAdmin(@ModelAttribute RegisterUserModel model) {
        model.setRoles(List.of(Role.USER, Role.ADMIN));
        authService.registerUserWithRoles(modelMapper.map(model, RegisterUserWithRolesServiceModel.class));

        return "redirect:/users/login";
    }

}
