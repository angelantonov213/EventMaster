package com.angelantonov.eventmaster.data.repositories;

import com.angelantonov.eventmaster.data.models.Role;
import com.angelantonov.eventmaster.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findByEmail(String email);
    List<User> findByRolesEquals(Role role);
}
