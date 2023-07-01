package com.nitesh.springboot.db2.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nitesh.springboot.db2.entities.Products;

public interface ProductRepo extends JpaRepository<Products, Integer>{

}
