package com.angelantonov.eventmaster.services.services.implementations;

import com.angelantonov.eventmaster.data.models.User;
import com.angelantonov.eventmaster.data.repositories.UsersRepository;
import com.angelantonov.eventmaster.services.model.*;
import com.angelantonov.eventmaster.services.services.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;

    public AuthServiceImpl(UsersRepository usersRepository, ModelMapper modelMapper) {
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void registerUserWithRoles(RegisterUserWithRolesServiceModel model) {
        // TODO: Add validation
        User user = modelMapper.map(model, User.class);
        // TODO: Add pass hasing

        usersRepository.save(user);
    }

    @Override
    public void refreshPasswordForUser(RefreshPasswordForUserServiceModel model) {

    }

    @Override
    public LoginResultServiceModel login(LoginServiceModel model) {
        return usersRepository
                .findByEmailAndPassword(model.getEmail(), model.getPassword())
                .map(user -> {
                    return modelMapper.map(user, LoginResultServiceModel.class);
                })
                .orElseThrow();
    }

    @Override
    public void setRoleForUser(SetRoleForServiceModel model) {

    }
}
