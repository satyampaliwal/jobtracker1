package com.satyam.jobtracke1.service;

import com.satyam.jobtracke1.entity.Job;

import java.util.List;

public interface JobService {
    List<Job> getAllJobs();

    Job getJobById(Long id);

    Job createJob(Job job);

    Job updateJob(Long id, Job updatedJob);

    void deleteJob(Long id);
}
