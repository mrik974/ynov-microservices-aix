package org.springframework.samples.petclinic.repository.api;

import java.util.Collection;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "${feign.petServiceURL}/api/pets/", name = "pets-api")
public interface ApiPetRepository extends PetRepository {


	@RequestMapping(value = "/{petId}", method = RequestMethod.GET, produces = "application/json")
    Pet findById(@PathVariable("petId") int id);

	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    void save(@RequestBody Pet pet);
    
	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	Collection<Pet> findAll();

	@RequestMapping(value = "/{petId}", method = RequestMethod.DELETE, produces = "application/json")
	void delete(@PathVariable("petId") int petId);
}
