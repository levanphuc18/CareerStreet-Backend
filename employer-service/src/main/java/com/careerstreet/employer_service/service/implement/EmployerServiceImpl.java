package com.careerstreet.employer_service.service.implement;


import com.careerstreet.employer_service.dto.EmployerRequest;
import com.careerstreet.employer_service.dto.EmployerResponse;
import com.careerstreet.employer_service.entity.Employer;
import com.careerstreet.employer_service.exception.EntityNotFoundException;
import com.careerstreet.employer_service.exception.GlobalCode;
import com.careerstreet.employer_service.repository.EmployerRepository;
import com.careerstreet.employer_service.service.EmployerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployerServiceImpl implements EmployerService {
    private final ModelMapper modelMapper;
    private final EmployerRepository employerRepository;

    @Override
    public EmployerResponse createEmployer(EmployerRequest employerRequest){
        Employer employer = modelMapper.map(employerRequest, Employer.class);

        employer = employerRepository.save(employer);

        EmployerResponse employerResponse =modelMapper.map(employer, EmployerResponse.class);

        return employerResponse;
    }

    @Override
    public EmployerResponse updateEmployerById(EmployerRequest employerRequest, Long employerId){
        Employer employer = employerRepository.findById(employerId).orElseThrow(
                () -> new EntityNotFoundException("Employer not found with id: " + employerId, GlobalCode.ERROR_ENTITY_NOT_FOUND)
        );

        modelMapper.map(employerRequest, employer);

        employer = employerRepository.save(employer);

        EmployerResponse employerResponse = modelMapper.map(employer, EmployerResponse.class);

        return employerResponse;
    }

    @Override
    public EmployerResponse getEmployerByUserName(String username) {
        Employer employer = employerRepository.findEmployerByUsername(username);
        System.out.println("employer ID: "+employer.getEmployerId());
        EmployerResponse employerResponse = modelMapper.map(employer, EmployerResponse.class);
        return employerResponse;
    }

    @Override
    public List<EmployerResponse> getAllEmployer(){
        List<Employer> list = employerRepository.findAll();
        return list
                .stream()
                .map(employer -> {
                    EmployerResponse employerResponse = modelMapper.map(employer,EmployerResponse.class);
                    return employerResponse;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Long getEployerIdByUsername (String username){
        System.out.println(employerRepository.findEmployerByUsername(username) + "employer");
        Employer employer = employerRepository.findEmployerByUsername(username);
        Long id = employer.getEmployerId();
        return id;
    }

    @Override
    public EmployerResponse getEmployerById(Long employerId){
        Employer employer = employerRepository.findById(employerId).orElseThrow(
                () -> new EntityNotFoundException("Employer not found with id: " + employerId, GlobalCode.ERROR_ENTITY_NOT_FOUND)
        );
        EmployerResponse employerResponse = modelMapper.map(employer, EmployerResponse.class);
        return employerResponse;
    }
}
