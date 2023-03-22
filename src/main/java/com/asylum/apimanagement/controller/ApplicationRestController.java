package com.asylum.apimanagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.asylum.apimanagement.dao.Request;
import com.asylum.apimanagement.exceptions.ApplicationNotFoundException;
import com.asylum.apimanagement.exceptions.InvalidRequestException;
import com.asylum.apimanagement.model.Application;
import com.asylum.apimanagement.service.ApplicationService;
import com.asylum.apimanagement.service.FilterSpecification;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ApplicationRestController {

	@Autowired
	private ApplicationService ApplicationService;

	@Autowired
	private FilterSpecification<Application> spec;

	public ApplicationRestController(ApplicationService ApplicationService) {
		this.ApplicationService = ApplicationService;
	}

	@GetMapping("/applications")
	@ResponseStatus(HttpStatus.OK)
	public List<Application> getApplications() {

		return ApplicationService.findAll();
	}

	@GetMapping(value = "/applications", params = { "page", "size", "sort" })
	@ResponseStatus(HttpStatus.OK)
	public List<Application> getApplicationsPaged(@RequestParam("page") int page, @RequestParam("size") int size,
			@RequestParam("sort") String sort) {

		Pageable pageable = PageRequest.of(page, size, Sort.by(Direction.ASC, "id"));
		List<Application> result = ApplicationService.findAll(pageable).getContent();
		return result;
	}

	@GetMapping(value="/applications/", params = {"page", "size", "sort"})
	public List<Application> searchSpec(@RequestBody Request request, @RequestParam("page") int page, @RequestParam("size") int size , @RequestParam("orderBy") String orderBy, @RequestParam("sort") String sort) {
		
		Specification<Application> sSpec = spec.getSearchSpecification(request.getSearchReq(), request.getGlobalOperator());
		List<Application> result = new ArrayList<>();
		if(sort.equals("ASC")){
			Pageable pageable = PageRequest.of(page, size, Sort.by(Direction.ASC, orderBy));
			result = ApplicationService.findAll(sSpec,pageable).getContent();
		}
		else
		{
			Pageable pageable = PageRequest.of(page, size, Sort.by(Direction.DESC, orderBy));
			result = ApplicationService.findAll(sSpec,pageable).getContent();
		}
		
		return result;
	}

	@GetMapping("/applications/{applicationId}")
	public Optional<Application> getApplication(@PathVariable int applicationId) throws ApplicationNotFoundException {

		Optional<Application> res = ApplicationService.findById(applicationId);

		if (res == null) {
			throw new ApplicationNotFoundException("Application ID not found");
		}
		return res;
	}

	@PostMapping(value = "/applications", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public Application addApplication(@Valid @RequestBody Application application) {

		application.setId(0);

		return ApplicationService.saveApplication(application);
	}

	@PutMapping("applications")
	@ResponseStatus(HttpStatus.OK)
	public Application updateApplication(@Valid @RequestBody Application application)
			throws ApplicationNotFoundException {

		if (application == null) {
			throw new InvalidRequestException("Application cannot be null");
		}
		Optional<Application> res = ApplicationService.findById(application.getId());

		if (res == null) {
			throw new ApplicationNotFoundException("Application ID not found");
		}

		return ApplicationService.saveApplication(application);
	}

	@DeleteMapping("/applications/{applicationId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteApplication(@Valid @PathVariable int applicationId) {

		Optional<Application> res = ApplicationService.findById(applicationId);

		if (res == null) {
			throw new ApplicationNotFoundException("Application ID not found");
		}
		ApplicationService.deleteApplication(applicationId);
	}

}
