package com.photographerplatform.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.photographerplatform.backend.entity.Photographer;

public interface PhotographerRepository
        extends JpaRepository<Photographer, Long> {

    @Query("""
            SELECT p
            FROM Photographer p
            WHERE LOWER(TRIM(p.email)) = LOWER(TRIM(:email))
            """)
    Optional<Photographer> findByEmail(
            @Param("email") String email
    );
}