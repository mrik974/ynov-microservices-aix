package org.springframework.samples.petclinic.repository.api;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.PetTypeRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "http://localhost:8081/api/pettypes/", name = "pettypes-api")
public interface ApiPetTypeRepositoryImpl extends PetTypeRepository {

	@Override
	@RequestMapping(value = "/{petTypeId}", method = RequestMethod.GET, produces = "application/json")
	public PetType findById(@PathVariable("petTypeId") int id) throws DataAccessException;

	@Override
	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	public Collection<PetType> findAll() throws DataAccessException;

	@Override
	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
	public void save(@RequestBody PetType petType) throws DataAccessException;

	@Override
	@RequestMapping(value = "/{petTypeId}", method = RequestMethod.DELETE, produces = "application/json")
	public void delete(@PathVariable("petTypeId") int petTypeId) throws DataAccessException;
}
