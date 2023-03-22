package com.asylum.apimanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.asylum.apimanagement.model.Application;

public interface ApplicationService {
    public List<Application> findAll();
    
    public Page<Application> findAll(Specification<Application> spec, Pageable pageable);

    public Page<Application> findAll(Pageable pageable);

    public List<Application> findPaginated(int page, int size, String sort);

    public Optional<Application> findById(int id);

    public Application saveApplication(Application application);

    public void deleteApplication(int id);
}
