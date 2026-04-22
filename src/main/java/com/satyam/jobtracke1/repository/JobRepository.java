package com.satyam.jobtracke1.repository;

import com.satyam.jobtracke1.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
    Page<Job> findByStatus(String status, Pageable pageable);
}
