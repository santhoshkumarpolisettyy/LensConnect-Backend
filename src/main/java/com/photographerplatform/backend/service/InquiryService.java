package com.photographerplatform.backend.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.photographerplatform.backend.entity.Inquiry;
import com.photographerplatform.backend.repository.InquiryRepository;

@Service
public class InquiryService {
    private final InquiryRepository inquiryRepository;

    public InquiryService(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    public Inquiry createInquiry(Inquiry inquiry) {
        inquiry.setStatus("NEW");
        return inquiryRepository.save(inquiry);
    }

    public List<Inquiry> getPhotographerInquiries(Long photographerId) {
        return inquiryRepository.findByPhotographerIdOrderByCreatedAtDesc(photographerId);
    }

    public Inquiry updateStatus(Long id, String status) {
        Inquiry inquiry = inquiryRepository.findById(id).orElse(null);
        if (inquiry == null) {
            return null;
        }
        inquiry.setStatus(status);
        return inquiryRepository.save(inquiry);
    }
}
