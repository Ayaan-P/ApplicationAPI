package com.asylum.apimanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.asylum.apimanagement.model.Application;

public interface ApplicationRepository extends JpaRepository<Application, Integer>, JpaSpecificationExecutor<Application>{

}
