package org.springframework.samples.petclinic.repository.api;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "http://localhost:8080/api/vets/", name = "vets-api")
public interface ApiVetRepository extends VetRepository {

	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	Collection<Vet> findAll();

	@RequestMapping(value = "/{vetId}", method = RequestMethod.GET, produces = "application/json")
	Vet findById(@PathVariable("vetId") int id);

	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
	void save(@RequestBody Vet vet);

	@RequestMapping(value = "/{vetId}", method = RequestMethod.DELETE, produces = "application/json")
	void delete(@PathVariable("vetId") int vetId);

}
