package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Dog {
	
	@Id
	@GeneratedValue
	public Integer id;
	public String name;
	public String lastName;
	
	
}
