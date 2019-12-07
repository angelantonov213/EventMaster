package com.angelantonov.eventmaster.services.services.implementations;

import com.angelantonov.eventmaster.data.models.Role;
import com.angelantonov.eventmaster.data.repositories.UsersRepository;
import com.angelantonov.eventmaster.services.model.UsersForRoleServiceModel;
import com.angelantonov.eventmaster.services.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UsersRepository usersRepository, ModelMapper modelMapper) {
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UsersForRoleServiceModel> getUsersForRole(Role role) {
        return usersRepository.findByRolesEquals(role)
                .stream()
                .map(user -> modelMapper.map(user, UsersForRoleServiceModel.class))
                .collect(Collectors.toList());
    }
}
