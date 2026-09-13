package com.photographerplatform.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.photographerplatform.backend.entity.Photographer;
import com.photographerplatform.backend.repository.PhotographerRepository;

@Service
public class PhotographerService {

    private final PhotographerRepository photographerRepository;

    public PhotographerService(
            PhotographerRepository photographerRepository) {

        this.photographerRepository = photographerRepository;
    }

    // ========================================
    // REGISTER PHOTOGRAPHER
    // ========================================

    public Photographer registerPhotographer(
            Photographer photographer) {

        return photographerRepository.save(photographer);
    }

    // ========================================
    // GET ALL PHOTOGRAPHERS
    // ========================================

    public List<Photographer> getAllPhotographers() {

        return photographerRepository.findAll();
    }

    // ========================================
    // GET PHOTOGRAPHER BY ID
    // ========================================

    public Photographer getPhotographerById(Long id) {

        return photographerRepository
                .findById(id)
                .orElse(null);
    }

    // ========================================
    // PHOTOGRAPHER LOGIN
    // ========================================

    public Photographer loginPhotographer(
            String email,
            String password) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("        LOGIN DEBUG START");
        System.out.println("========================================");

        System.out.println(
                "Email received: [" + email + "]"
        );

        System.out.println(
                "Password received: [" + password + "]"
        );

        // Find photographer by email
        Optional<Photographer> photographerOptional =
                photographerRepository.findByEmail(email);

        System.out.println(
                "Photographer found: "
                        + photographerOptional.isPresent()
        );

        // Photographer does not exist
        if (photographerOptional.isEmpty()) {

            System.out.println("LOGIN FAILED: EMAIL NOT FOUND");

            System.out.println(
                    "========================================"
            );

            return null;
        }

        Photographer photographer =
                photographerOptional.get();

        System.out.println(
                "Photographer ID: "
                        + photographer.getId()
        );

        System.out.println(
                "Database email: ["
                        + photographer.getEmail()
                        + "]"
        );

        System.out.println(
                "Database password: ["
                        + photographer.getPassword()
                        + "]"
        );

        // Password check
        if (photographer.getPassword() == null) {

            System.out.println(
                    "LOGIN FAILED: PASSWORD IS NULL"
            );

            System.out.println(
                    "========================================"
            );

            return null;
        }

        if (!photographer.getPassword().equals(password)) {

            System.out.println(
                    "LOGIN FAILED: PASSWORD DOES NOT MATCH"
            );

            System.out.println(
                    "========================================"
            );

            return null;
        }

        System.out.println("LOGIN SUCCESS");

        System.out.println(
                "========================================"
        );

        return photographer;
    }

    // ========================================
    // UPDATE PHOTOGRAPHER PROFILE
    // ========================================

    public Photographer updatePhotographer(
            Long id,
            Photographer updatedPhotographer) {

        Optional<Photographer> existingOptional =
                photographerRepository.findById(id);

        if (existingOptional.isEmpty()) {
            return null;
        }

        Photographer existing =
                existingOptional.get();

        existing.setFullName(
                updatedPhotographer.getFullName()
        );

        existing.setBusinessName(
                updatedPhotographer.getBusinessName()
        );

        existing.setEmail(
                updatedPhotographer.getEmail()
        );

        existing.setPhoneNumber(
                updatedPhotographer.getPhoneNumber()
        );

        existing.setCity(
                updatedPhotographer.getCity()
        );

        existing.setPincode(
                updatedPhotographer.getPincode()
        );

        existing.setExperienceYears(
                updatedPhotographer.getExperienceYears()
        );

        existing.setSpecialization(
                updatedPhotographer.getSpecialization()
        );

        existing.setStartingPrice(
                updatedPhotographer.getStartingPrice()
        );

        existing.setBio(
                updatedPhotographer.getBio()
        );

        /*
         * Password is intentionally NOT changed.
         *
         * Rating and totalReviews are also preserved.
         *
         * Profile photo is also preserved here.
         * It is handled separately by the photo methods below.
         */

        return photographerRepository.save(existing);
    }

    // ========================================
    // UPLOAD PROFILE PHOTO
    // ========================================

    public Photographer uploadProfilePhoto(
            Long id,
            MultipartFile file) throws Exception {

        Optional<Photographer> existingOptional =
                photographerRepository.findById(id);

        if (existingOptional.isEmpty()) {
            return null;
        }

        Photographer photographer =
                existingOptional.get();

        photographer.setProfilePhoto(
                file.getBytes()
        );

        photographer.setProfilePhotoContentType(
                file.getContentType()
        );

        return photographerRepository.save(photographer);
    }

    // ========================================
    // GET PROFILE PHOTO
    // ========================================

    public Photographer getProfilePhoto(Long id) {

        return photographerRepository
                .findById(id)
                .orElse(null);
    }

    // ========================================
    // DELETE PROFILE PHOTO
    // ========================================

    public Photographer deleteProfilePhoto(Long id) {

        Optional<Photographer> existingOptional =
                photographerRepository.findById(id);

        if (existingOptional.isEmpty()) {
            return null;
        }

        Photographer photographer =
                existingOptional.get();

        photographer.setProfilePhoto(null);
        photographer.setProfilePhotoContentType(null);

        return photographerRepository.save(photographer);
    }

    // ========================================
    // DELETE PHOTOGRAPHER
    // ========================================

    public void deletePhotographer(Long id) {

        photographerRepository.deleteById(id);
    }
}