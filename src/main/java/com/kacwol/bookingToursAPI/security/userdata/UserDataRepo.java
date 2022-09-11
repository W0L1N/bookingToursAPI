package com.kacwol.bookingToursAPI.security.userdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDataRepo extends JpaRepository<UserData, Long> {
    Optional<UserData> findByUsername(String username);

    boolean existsByUsername(String username);
}
