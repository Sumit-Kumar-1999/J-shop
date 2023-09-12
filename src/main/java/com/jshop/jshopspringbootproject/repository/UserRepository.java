package com.jshop.jshopspringbootproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jshop.jshopspringbootproject.dto.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	/*
	 * get User Details By Passing userEmail
	 */
	public User findByUserEmail(String userEmail);
}
