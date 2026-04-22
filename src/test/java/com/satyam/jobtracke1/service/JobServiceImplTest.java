package com.satyam.jobtracke1.service;


import com.satyam.jobtracke1.entity.Job;
import com.satyam.jobtracke1.exception.ResourceNotFoundException;
import com.satyam.jobtracke1.repository.JobRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JobServiceImplTest {
    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private JobServiceImpl jobService;

    private Job job;

    @BeforeEach
    void setUp() {
        job = new Job();
        job.setId(1L);
        job.setCompanyName("Google");
        job.setPosition("Backend Developer");
        job.setStatus("Applied");
        job.setLocation("Bangalore");
        job.setAppliedDate(LocalDate.now());
        job.setNotes("Test note");
    }

    // Test 1 — Sabhi jobs aati hain?
    @Test
    void getAllJobs_ShouldReturnAllJobs() {
        // Arrange — fake data set karo
        when(jobRepository.findAll()).thenReturn(Arrays.asList(job));

        // Act — method call karo
        List<Job> result = jobService.getAllJobs();

        // Assert — check karo
        assertEquals(1, result.size());
        assertEquals("Google", result.get(0).getCompanyName());
        verify(jobRepository, times(1)).findAll();
    }

    // Test 2 — ID se job milti hai?
    @Test
    void getJobById_ShouldReturnJob_WhenExists() {
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));

        Job result = jobService.getJobById(1L);

        assertEquals("Google", result.getCompanyName());
        assertEquals("Applied", result.getStatus());
    }

    // Test 3 — Galat ID pe exception aata hai?
    @Test
    void getJobById_ShouldThrowException_WhenNotFound() {
        when(jobRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            jobService.getJobById(999L);
        });
    }

    // Test 4 — Job save hoti hai?
    @Test
    void createJob_ShouldSaveAndReturnJob() {
        when(jobRepository.save(job)).thenReturn(job);

        Job result = jobService.createJob(job);

        assertNotNull(result);
        assertEquals("Google", result.getCompanyName());
        verify(jobRepository, times(1)).save(job);
    }

    // Test 5 — Job delete hoti hai?
    @Test
    void deleteJob_ShouldCallDeleteById() {
        doNothing().when(jobRepository).deleteById(1L);

        jobService.deleteJob(1L);

        verify(jobRepository, times(1)).deleteById(1L);
    }

}
