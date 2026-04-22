package com.satyam.jobtracke1.service;

import com.satyam.jobtracke1.entity.Job;
import com.satyam.jobtracke1.exception.ResourceNotFoundException;
import com.satyam.jobtracke1.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service                  // ← yeh add karo
@RequiredArgsConstructor  // ← yeh add karo
public class JobServiceImpl implements JobService {

    private static final Logger log = LoggerFactory.getLogger(JobServiceImpl.class);
    private final JobRepository jobRepository;

    @Override
    public List<Job> getAllJobs() {
        log.info("Fetching all jobs");
        List<Job> jobs = jobRepository.findAll();
        log.info("Total jobs found: {}", jobs.size());
        return jobs;
    }

    @Override
    public Job getJobById(Long id) {
        log.info("Fetching job with id: {}", id);
        return jobRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Job not found with id: {}", id);
                    return new ResourceNotFoundException("Job not found with id: " + id);
                });
    }

    @Override
    public Job createJob(Job job) {
        log.info("Creating new job for company: {}", job.getCompanyName());
        Job savedJob = jobRepository.save(job);
        log.info("Job created successfully with id: {}", savedJob.getId());
        return savedJob;
    }

    @Override
    public Job updateJob(Long id, Job updatedJob) {
        log.info("Updating job with id: {}", id);
        Job existingJob = getJobById(id);
        existingJob.setCompanyName(updatedJob.getCompanyName());
        existingJob.setPosition(updatedJob.getPosition());
        existingJob.setStatus(updatedJob.getStatus());
        existingJob.setLocation(updatedJob.getLocation());
        existingJob.setAppliedDate(updatedJob.getAppliedDate());
        existingJob.setNotes(updatedJob.getNotes());
        Job saved = jobRepository.save(existingJob);
        log.info("Job updated successfully with id: {}", id);
        return saved;
    }

    @Override
    public void deleteJob(Long id) {
        log.info("Deleting job with id: {}", id);
        jobRepository.deleteById(id);
        log.info("Job deleted successfully with id: {}", id);
    }

    @Override
    public Page<Job> getJobsByPage(int page, int size) {
        log.info("Fetching jobs - page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.by("appliedDate").descending());
        return jobRepository.findAll(pageable);
    }

    @Override
    public Page<Job> getJobsByStatus(String status, int page, int size) {
        log.info("Fetching jobs by status: {}, page: {}, size: {}", status, page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.by("appliedDate").descending());
        return jobRepository.findByStatus(status, pageable);
    }
}
