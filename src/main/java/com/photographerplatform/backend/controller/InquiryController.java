package com.photographerplatform.backend.controller;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.photographerplatform.backend.entity.Inquiry;
import com.photographerplatform.backend.service.InquiryService;

@CrossOrigin(origins = {
	    "http://localhost:5173",
	    "https://lensconnect-frontend.vercel.app"
	})
@RestController
@RequestMapping("/api/inquiries")
public class InquiryController {
    private final InquiryService inquiryService;

    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    @PostMapping
    public ResponseEntity<Inquiry> createInquiry(@RequestBody Inquiry inquiry) {
        if (inquiry.getPhotographerId() == null || inquiry.getClientName() == null || inquiry.getClientEmail() == null || inquiry.getEventType() == null || inquiry.getMessage() == null) {
            return ResponseEntity.badRequest().build();
        }
        Inquiry created = inquiryService.createInquiry(inquiry);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/photographer/{photographerId}")
    public ResponseEntity<List<Inquiry>> getPhotographerInquiries(@PathVariable Long photographerId) {
        return ResponseEntity.ok(inquiryService.getPhotographerInquiries(photographerId));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String status = body.get("status");
        if (status == null || status.isBlank()) {
            return ResponseEntity.badRequest().body("Status is required");
        }

        Inquiry updated = inquiryService.updateStatus(id, status.toUpperCase());
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Inquiry not found");
        }
        return ResponseEntity.ok(updated);
    }
}
