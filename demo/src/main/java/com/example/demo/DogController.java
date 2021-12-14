package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dog")
public class DogController {

	/*
	 * //@RequestMapping("/hello")
	 * 
	 * @GetMapping("/hello") public String sayHello() { return "Hello World!"; }
	 * 
	 * @PostMapping(value="/hello", produces = MediaType.APPLICATION_JSON_VALUE)
	 * public String sayHelloPost() { return "Hello World with POST verb!"; }
	 */
	
	/*
	 * @GetMapping("/dog/{name}") public Dog getDog(@PathVariable("name") String
	 * name, @RequestParam("lastName") ArrayList<String> lastName) { Dog dog = new
	 * Dog(); dog.name = name; dog.lastName = lastName.get(1); return dog; }
	 */
	
	@Autowired
	DogRepository dogRepository;
	
	@PostMapping("/")
	public ResponseEntity<Dog> addDog(@RequestBody Dog dog) {
		dogRepository.save(dog);
		return ResponseEntity.status(HttpStatus.CREATED).body(dog);
	}
	
	@GetMapping("/")
	public ResponseEntity<Iterable<Dog>> getDogs(@RequestParam(value="name", required = false) String name) {
		if (name == null) {
			return ResponseEntity.ok(dogRepository.findAll());			
		}
		Iterable<Dog> dogs = dogRepository.findByName(name);
		return ResponseEntity.ok(dogs);
	}
}
