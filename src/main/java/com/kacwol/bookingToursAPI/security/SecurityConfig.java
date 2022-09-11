package com.kacwol.bookingToursAPI.security;

import com.kacwol.bookingToursAPI.security.userdata.UserData;
import com.kacwol.bookingToursAPI.security.userdata.UserDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final UserDataRepo userDataRepo;

    @Autowired
    public SecurityConfig(UserDataRepo userDataRepo) {
        this.userDataRepo = userDataRepo;
    }

    @Bean
    public PasswordEncoder getBCryptEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AppUserDetailsService getUserDetailsService() {
        AppUserDetailsService userDetailsService = new AppUserDetailsService(userDataRepo, getBCryptEncoder());

        userDataRepo.save(new UserData(1L,"admin", getBCryptEncoder().encode("admin"), List.of("ADMIN")));

        userDataRepo.save(new UserData(2L,"user", getBCryptEncoder().encode("user"), List.of("USER")));

        return userDetailsService;
    }

    @Bean
    public SecurityFilterChain getFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/tour/edit/**", "/destination/**","/user/guide", "/client").hasAnyAuthority("ADMIN")
                .antMatchers("/tour**","/client/add","/reservation").authenticated()
                .anyRequest().permitAll()
                .and().httpBasic()
                .and()
                .csrf()
                .disable();
        return http.build();
    }


    @Bean
    public AuthenticationProvider getAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(getUserDetailsService());
        provider.setPasswordEncoder(getBCryptEncoder());
        return provider;
    }


}
