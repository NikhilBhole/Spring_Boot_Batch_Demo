package com.yash.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
