package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface DogRepository extends CrudRepository<Dog, Integer>{

	Iterable<Dog> findByName(String name);
}
