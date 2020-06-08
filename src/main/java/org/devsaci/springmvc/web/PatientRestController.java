package org.devsaci.springmvc.web;

import java.util.List;
import org.devsaci.springmvc.dao.PatientRepository;
import org.devsaci.springmvc.entities.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
    

/**
 * @author Zakaria
 *
 */
@RestController
public class PatientRestController {
	@Autowired
	private PatientRepository patientRepository;

	/* Retourne une liste Patients en format JSON */
	@GetMapping("/listPatients")

	public List<Patient> listPatients(){
		
		return patientRepository.findAll();
	}
	
	/* Retourne un ID  Patient en format JSON 
	 * {id} PathParam
	 */
	@GetMapping("/patient/{id}")

	public Patient getOne (@PathVariable Long id){
		
		return patientRepository.findById(id).get();
	}

}
