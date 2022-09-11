package com.kacwol.bookingToursAPI.security.userdata;

import com.kacwol.bookingToursAPI.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserDataRepo repo;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserDataRepo repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegistrationInfoDto info) {
        if(repo.existsByUsername(info.getUsername())) throw new UserAlreadyExistsException("User with given username already exists.");

        repo.save(new UserData(
                null,
                info.getUsername(),
                passwordEncoder.encode(info.getPassword()),
                List.of("USER")
                )
        );
    }

    public UserData getEntityByUsername(String username) {
        return repo.findByUsername(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("User does not exist.");
        }
        );
    }

    public boolean containsAuthority(Authentication auth, String authority ) {
        return auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList()
                .contains(authority);
    }

}
