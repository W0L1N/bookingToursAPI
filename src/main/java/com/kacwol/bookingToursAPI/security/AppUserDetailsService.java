package com.kacwol.bookingToursAPI.security;

import com.kacwol.bookingToursAPI.security.userdata.UserData;
import com.kacwol.bookingToursAPI.security.userdata.UserDataRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AppUserDetailsService implements UserDetailsService {

    private final UserDataRepo repo;

    private final PasswordEncoder passwordEncoder;

    public AppUserDetailsService(UserDataRepo repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserData userData = repo.findByUsername(username).orElseThrow(()->{
            throw  new UsernameNotFoundException("User does not exist.");
        });
        return new AppUserDetails(userData);
    }
}
