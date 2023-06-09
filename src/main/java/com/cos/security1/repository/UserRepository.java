package com.cos.security1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.security1.model.User;

// CRUD 함수를 JpaRepository가 들고 있음.
// @Repository 가 없어도 IoC가 된다. 이유는 JpaRepository를 상속했기 때문에...
public interface UserRepository extends JpaRepository<User, Integer>{

	User findByUsername(String username);

}
