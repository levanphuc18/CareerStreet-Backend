package com.careerstreet.employer_service.repository;


import com.careerstreet.employer_service.entity.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployerRepository extends JpaRepository<Employer, Long> {
    Employer findEmployerByUsername(String username);
}
