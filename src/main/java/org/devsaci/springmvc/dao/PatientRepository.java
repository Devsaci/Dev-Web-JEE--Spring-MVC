package org.devsaci.springmvc.dao;

import org.devsaci.springmvc.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
public Page<Patient>  findByNameContains(String name,Pageable pageable);
}
