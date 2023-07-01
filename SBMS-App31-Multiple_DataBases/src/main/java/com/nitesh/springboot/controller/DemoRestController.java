package com.nitesh.springboot.controller;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nitesh.springboot.db1.entities.UserDetails;
import com.nitesh.springboot.db1.repo.UserRepo;
import com.nitesh.springboot.db2.entities.Products;
import com.nitesh.springboot.db2.repo.ProductRepo;

@RestController
public class DemoRestController {

	@Autowired
	private UserRepo user;
	
	
	@Autowired
	private ProductRepo product;
	
	@GetMapping("/add")
	public String addData() {
		
		user.saveAll(Stream.of(new UserDetails(433, "John", "Nagpur"), new UserDetails(455, "Smith", "Pune")).collect(Collectors.toList()));
		product.saveAll(Stream.of(new Products(54, "Book", 87), new Products(98, "Laptop", 32)).collect(Collectors.toList()));
		return "Data Added Succefully!!";
	}
	
	
	
}
