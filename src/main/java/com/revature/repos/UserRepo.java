package com.revature.repos;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.revature.models.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	
	
 	Page<User> findAll(Pageable pageable);
 	
	public List<User> findAllByCohortsCohortId(int id);

	public User findByEmailIgnoreCase(String email);
	
  
	@Query("FROM User user WHERE user.email LIKE %:email%")
	public Page<User> findUsersByEmailIgnoreCase(String email, Pageable pageable);
  
	@Query("FROM User user WHERE LOWER(user.email) IN :emailList")
	public Page<User> findAllUserByEmailIgnoreCase(List<String> emailList, Pageable pageable);
  
}
