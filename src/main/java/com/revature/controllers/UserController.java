package com.revature.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.annotations.JwtUserIsAdmin;
import com.revature.annotations.JwtUserIsSelf;
import com.revature.annotations.JwtUserIsSelfOrAdmin;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.utils.ResponseMap;

@RestController
@RequestMapping("users")
public class UserController {
	
	@Autowired
	private UserService userService;

	
	@GetMapping()
	@JwtUserIsAdmin
	public ResponseEntity<Map<String,Object>> findAll(){
		
		List<User> userList=  userService.findAll();
		if (userList == null) {
			return  ResponseEntity.badRequest().body(ResponseMap.getBadResponse("No users found."));
		}
		return  ResponseEntity.ok().body(ResponseMap.getGoodResponse(userList,"Here's all your users."));
	}
	
	@GetMapping("{id}")
	//Might need to change?
	@JwtUserIsSelf
	public ResponseEntity<Map<String,Object>> findOneById(@PathVariable int id){
		User user =  userService.findOneById(id);
		if (user == null) {
			return  ResponseEntity.badRequest().body(ResponseMap.getBadResponse("User not found."));
		}
		return  ResponseEntity.ok().body(ResponseMap.getGoodResponse(user,"Here is your users."));
	}
	
	@JwtUserIsAdmin
	@GetMapping("cohorts/{id}")
	public ResponseEntity<Map<String,Object>> findAllByCohortId(@PathVariable int id){
		List<User> userList=  userService.findAllByCohortId(id);
		if (userList == null) {
			return  ResponseEntity.badRequest().body(ResponseMap.getBadResponse("Users not found."));
		}
		return  ResponseEntity.ok().body(ResponseMap.getGoodResponse(userList,"Here is your users."));
	}
	
	
	@PostMapping()
	public ResponseEntity<Map<String,Object>> saveUser(@RequestBody User u, @RequestParam(value = "token", required = true) int cohortToken){
	    User user =  userService.saveUser(u);
	    //UserDto or JSON ignore
		
	    if (user == null) {
			return  ResponseEntity.badRequest().body(ResponseMap.getBadResponse("Users not saved."));
		}
		return  ResponseEntity.ok().body(ResponseMap.getGoodResponse(user,"Saved user"));
	}
	
	@PostMapping()
	public ResponseEntity<Map<String,Object>> login(@RequestBody User u){
	    User user =  userService.login(u);
	    //UserDto or JSON ignore
		
	    if (user == null) {
			return  ResponseEntity.badRequest().body(ResponseMap.getBadResponse("Users not saved."));
		}
		return  ResponseEntity.ok().body(ResponseMap.getGoodResponse(user,"Saved user"));
	}
	
	@PatchMapping()
	@JwtUserIsSelfOrAdmin
	public ResponseEntity<Map<String,Object>> updateUser(@RequestBody User u){
	    User user =  userService.updateUser(u);
	    //UserDto or JSON ignore
		
	    if (user == null) {
			return  ResponseEntity.badRequest().body(ResponseMap.getBadResponse("Users not saved."));
		}
		return  ResponseEntity.ok().body(ResponseMap.getGoodResponse(user,"Saved user"));
	}
}
