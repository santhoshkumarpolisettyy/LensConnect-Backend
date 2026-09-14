package com.photographerplatform.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.photographerplatform.backend.entity.Photographer;
import com.photographerplatform.backend.service.PhotographerService;

@CrossOrigin(origins = {
	    "http://localhost:5173",
	    "https://lensconnect-frontend.vercel.app"
	})
@RestController
@RequestMapping("/api/photographers")
public class PhotographerController {

    private final PhotographerService photographerService;

    public PhotographerController(
            PhotographerService photographerService) {

        this.photographerService = photographerService;
    }

    // ========================================
    // REGISTER PHOTOGRAPHER
    // ========================================

    @PostMapping
    public Photographer registerPhotographer(
            @RequestBody Photographer photographer) {

        return photographerService.registerPhotographer(
                photographer
        );
    }

    // ========================================
    // PHOTOGRAPHER LOGIN
    // ========================================

    @PostMapping("/login")
    public ResponseEntity<?> loginPhotographer(
            @RequestBody Map<String, String> loginData) {

        String email = loginData.get("email");
        String password = loginData.get("password");

        Photographer photographer =
                photographerService.loginPhotographer(
                        email,
                        password
                );

        if (photographer == null) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }

        return ResponseEntity.ok(photographer);
    }

    // ========================================
    // GET ALL PHOTOGRAPHERS
    // ========================================

    @GetMapping
    public List<Photographer> getAllPhotographers() {

        return photographerService.getAllPhotographers();
    }

    // ========================================
    // GET PHOTOGRAPHER BY ID
    // ========================================

    @GetMapping("/{id}")
    public ResponseEntity<?> getPhotographerById(
            @PathVariable Long id) {

        Photographer photographer =
                photographerService.getPhotographerById(id);

        if (photographer == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Photographer not found");
        }

        return ResponseEntity.ok(photographer);
    }

    // ========================================
    // UPDATE PHOTOGRAPHER PROFILE
    // ========================================

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePhotographer(
            @PathVariable Long id,
            @RequestBody Photographer photographer) {

        Photographer updatedPhotographer =
                photographerService.updatePhotographer(
                        id,
                        photographer
                );

        if (updatedPhotographer == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Photographer not found");
        }

        return ResponseEntity.ok(updatedPhotographer);
    }

    // ========================================
    // UPLOAD PROFILE PHOTO
    // ========================================

    @PostMapping(
            value = "/{id}/photo",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> uploadProfilePhoto(
            @PathVariable Long id,
            @RequestParam("photo") MultipartFile photo) {

        if (photo == null || photo.isEmpty()) {

            return ResponseEntity
                    .badRequest()
                    .body("Please select a photo");
        }

        try {

            Photographer photographer =
                    photographerService.uploadProfilePhoto(
                            id,
                            photo
                    );

            if (photographer == null) {

                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Photographer not found");
            }

            return ResponseEntity.ok(
                    "Profile photo uploaded successfully"
            );

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload profile photo");
        }
    }

    // ========================================
    // GET PROFILE PHOTO
    // ========================================

    @GetMapping("/{id}/photo")
    public ResponseEntity<?> getProfilePhoto(
            @PathVariable Long id) {

        Photographer photographer =
                photographerService.getProfilePhoto(id);

        if (photographer == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Photographer not found");
        }

        if (photographer.getProfilePhoto() == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Profile photo not found");
        }

        String contentType =
                photographer.getProfilePhotoContentType();

        if (contentType == null || contentType.isBlank()) {
            contentType = "image/jpeg";
        }

        return ResponseEntity
                .ok()
                .contentType(
                        MediaType.parseMediaType(contentType)
                )
                .body(photographer.getProfilePhoto());
    }

    // ========================================
    // DELETE PROFILE PHOTO
    // ========================================

    @DeleteMapping("/{id}/photo")
    public ResponseEntity<?> deleteProfilePhoto(
            @PathVariable Long id) {

        Photographer photographer =
                photographerService.deleteProfilePhoto(id);

        if (photographer == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Photographer not found");
        }

        return ResponseEntity.ok(
                "Profile photo deleted successfully"
        );
    }

    // ========================================
    // DELETE PHOTOGRAPHER
    // ========================================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePhotographer(
            @PathVariable Long id) {

        photographerService.deletePhotographer(id);

        return ResponseEntity.ok(
                "Photographer deleted successfully"
        );
    }
}