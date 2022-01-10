package org.springframework.samples.petclinic.repository.api;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.PetTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiPetTypeRepositoryImpl implements PetTypeRepository {

	@Override
	public PetType findById(int id) throws DataAccessException {
		RestTemplate restClient = new RestTemplate();
		return restClient.getForEntity("http://localhost:8081/api/pettypes/" + String.valueOf(id), PetType.class)
				.getBody();
	}

	@Override
	public Collection<PetType> findAll() throws DataAccessException {
		RestTemplate restClient = new RestTemplate();
		PetType[] petTypes = restClient.getForEntity("http://localhost:8081/api/pettypes/", PetType[].class).getBody();
		return Arrays.asList(petTypes);
	}

	@Override
	public void save(PetType petType) throws DataAccessException {
		RestTemplate restClient = new RestTemplate();
		ResponseEntity<?> response = restClient.postForEntity("http://localhost:8081/api/pettypes/", petType,
				petType.getClass());
		if (!response.getStatusCode().equals(HttpStatus.CREATED)) {
			throw new RuntimeException("Could not create petType");
		}
	}

	@Override
	public void delete(PetType petType) throws DataAccessException {
		RestTemplate restClient = new RestTemplate();
		restClient.delete("http://localhost:8081/api/pettypes/" + String.valueOf(petType.getId()));
	}

}
