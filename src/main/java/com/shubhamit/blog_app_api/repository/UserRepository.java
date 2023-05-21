package com.shubhamit.blog_app_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shubhamit.blog_app_api.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{
	
	Optional<User>findByEmail(String email);

}
