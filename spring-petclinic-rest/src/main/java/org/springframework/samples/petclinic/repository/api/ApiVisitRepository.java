package org.springframework.samples.petclinic.repository.api;

import java.util.Collection;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "${feign.visitServiceURL}/api/visits/", name = "visits-api")
public interface ApiVisitRepository extends VisitRepository {

	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    void save(Visit visit);

	@RequestMapping(value = "/find-by-pet-id/{petId}", method = RequestMethod.GET, produces = "application/json")
    List<Visit> findByPetId(@PathVariable("petId") Integer petId);
    
	@RequestMapping(value = "/{visitId}", method = RequestMethod.GET, produces = "application/json")
	Visit findById(@PathVariable("visitId") int id);
	
	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	Collection<Visit> findAll();

	@RequestMapping(value = "/{visitId}", method = RequestMethod.DELETE, produces = "application/json")
	void delete(@PathVariable("visitId") int visitId);
	
}
