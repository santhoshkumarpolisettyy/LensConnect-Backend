package com.photographerplatform.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.photographerplatform.backend.entity.Inquiry;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    List<Inquiry> findByPhotographerIdOrderByCreatedAtDesc(Long photographerId);
}
