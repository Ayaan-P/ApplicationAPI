package com.asylum.apimanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.asylum.apimanagement.dao.ApplicationRepository;
import com.asylum.apimanagement.model.Application;

@Service
public class ApplicationServiceImpl implements ApplicationService {

	private ApplicationRepository appRep;

	public ApplicationServiceImpl(ApplicationRepository appRep) {
		this.appRep = appRep;
	}

	@Override
	public Optional<Application> findById(int id) {
		Optional<Application> res = appRep.findById(id);

		if (res.isPresent()) {
			return Optional.ofNullable(res.get());
		}
		return null;
	}

	@Override
	public List<Application> findAll() {

		return appRep.findAll();
	}
	
	@Override
	public List<Application> findPaginated(int page, int size, String sort ) {

		return appRep.findAll();
	}

	
	@Override
	public Page<Application> findAll(Specification<Application> spec, Pageable pageable) {
	
		return appRep.findAll(spec, pageable);
	}

	@Override
	public Page<Application> findAll(Pageable pageable) {
		
		return appRep.findAll(pageable);

	}

	@Override
	public Application saveApplication(Application application) {

		appRep.save(application);

		return application;
	}

	@Override
	public void deleteApplication(int id) {

		appRep.deleteById(id);

	}



}
