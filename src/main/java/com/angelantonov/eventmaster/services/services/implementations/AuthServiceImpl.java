package com.angelantonov.eventmaster.services.services.implementations;

import com.angelantonov.eventmaster.data.models.User;
import com.angelantonov.eventmaster.data.repositories.UsersRepository;
import com.angelantonov.eventmaster.helpers.RandomStringUtil;
import com.angelantonov.eventmaster.services.model.auth.*;
import com.angelantonov.eventmaster.services.services.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        // TODO: Add pass hashing

        usersRepository.save(user);
    }

    @Override
    public void refreshPasswordForUser(RefreshPasswordForUserServiceModel model) {
        Optional<User> user = usersRepository.findByEmail(model.getEmail());

        if (user.isPresent()) {
            String newPassword = RandomStringUtil.generateRandomString(10);
            User foundUser = user.get();
            foundUser.setPassword(newPassword);
            usersRepository.save(foundUser);

            System.out.println("new password is: " + newPassword);
        }
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
        model.getUser().setRoles(model.getRoles());
        usersRepository.save(model.getUser());
    }
}
