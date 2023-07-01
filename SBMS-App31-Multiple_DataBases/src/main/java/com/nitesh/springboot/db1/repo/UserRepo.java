package com.nitesh.springboot.db1.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nitesh.springboot.db1.entities.UserDetails;

public interface UserRepo extends JpaRepository<UserDetails, Integer>{

}
