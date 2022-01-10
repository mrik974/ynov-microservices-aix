package org.springframework.samples.petclinic.repository.api;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.repository.SpecialtyRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "http://localhost:8080/api/specialties/", name = "specialty-api")
public interface ApiSpecialtyRepository extends SpecialtyRepository {

	@RequestMapping(value = "{specialtyId}", method = RequestMethod.GET, produces = "application/json")
	public Specialty findById(@PathVariable("specialtyId") int id) throws DataAccessException;

	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	public Collection<Specialty> findAll() throws DataAccessException;

	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
	public void save(@RequestBody Specialty specialty) throws DataAccessException;

	@RequestMapping(value = "{specialtyId}", method = RequestMethod.DELETE, produces = "application/json")
	public void delete(@PathVariable("specialtyId") Integer specialtyId) throws DataAccessException;

}
