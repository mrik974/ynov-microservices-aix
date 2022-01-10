package org.springframework.samples.petclinic.repository.api;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "http://localhost:8081/api/owners/", name = "owners-api")
public interface ApiOwnerRepository extends OwnerRepository {

	@RequestMapping(value = "/*/lastname/{lastName}", method = RequestMethod.GET, produces = "application/json")
	Collection<Owner> findByLastName(@PathVariable("lastName") String lastName);
	
	@RequestMapping(value = "/{ownerId}", method = RequestMethod.GET, produces = "application/json")
	Owner findById(@PathVariable("ownerId") int id);
	
	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
	void save(@RequestBody Owner owner);
	
	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	Collection<Owner> findAll();
	
	@RequestMapping(value = "/{ownerId}", method = RequestMethod.DELETE, produces = "application/json")
	void delete(@PathVariable("ownerId") int ownerId);
	
	@RequestMapping(value = "/*/petname/{petName}", method = RequestMethod.GET, produces = "application/json")
	Collection<Owner> findByPets_Name(@PathVariable("petName") String petName);
	
}
