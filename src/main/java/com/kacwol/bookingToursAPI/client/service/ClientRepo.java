package com.kacwol.bookingToursAPI.client.service;

import com.kacwol.bookingToursAPI.client.model.ClientData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepo extends JpaRepository<ClientData,Long> {
    boolean existsByPassportNumber(String passportNumber);

    Optional<ClientData> findByUserDataId(Long userDataId);
}
