package com.example.tasksmanager.jwt;


import com.example.tasksmanager.model.RoleEntity;
import com.example.tasksmanager.model.UserEntity;
import com.example.tasksmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for loading user-specific data during authentication.
 * This service implements {@link UserDetailsService}
 * and is used to retrieve user details from the repository
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Autowired
    public JwtUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * This method retrieves a {@link UserEntity} from the repository based on the provided email.
     *
     * @param email The email of the user to load.
     * @return The {@link UserDetails} containing the user's email, password, and authorities.
     * @throws UsernameNotFoundException If no user is found with the provided email.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    /**
     * This method converts a list of {@link RoleEntity} objects into a collection of {@link GrantedAuthority}
     *
     * @param roles The list of roles to map.
     * @return A collection of {@link GrantedAuthority} objects representing the user's roles.
     */
    private Collection<GrantedAuthority> mapRolesToAuthorities(List<RoleEntity> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
