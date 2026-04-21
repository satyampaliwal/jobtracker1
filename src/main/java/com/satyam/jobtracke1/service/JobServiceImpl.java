package com.satyam.jobtracke1.service;

import com.satyam.jobtracke1.entity.Job;
import com.satyam.jobtracke1.exception.ResourceNotFoundException;
import com.satyam.jobtracke1.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service                  // ← yeh add karo
@RequiredArgsConstructor  // ← yeh add karo
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;

    @Override
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    @Override
    public Job getJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));
    }

    @Override
    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

    @Override
    public Job updateJob(Long id, Job updatedJob) {
        Job existingJob = getJobById(id);
        existingJob.setCompanyName(updatedJob.getCompanyName());
        existingJob.setPosition(updatedJob.getPosition());
        existingJob.setStatus(updatedJob.getStatus());
        existingJob.setLocation(updatedJob.getLocation());
        existingJob.setAppliedDate(updatedJob.getAppliedDate());
        existingJob.setNotes(updatedJob.getNotes());
        return jobRepository.save(existingJob);
    }

    @Override
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }
}
