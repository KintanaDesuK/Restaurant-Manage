package com.restaurant.demo.controller;


import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties.Value;
import com.restaurant.demo.entity.User;
import com.restaurant.demo.repository.RoleRepository;
import com.restaurant.demo.repository.UserRepository;
import com.restaurant.demo.security.jwt.JwtUtils;
import com.restaurant.demo.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	JwtUtils jwtUtils;
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('WAITER') or hasRole('CHEF') or hasRole('CASHIER')")
	public String userAccess() {
		return "User Content.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> adminAccess(Authentication authentication) {
		UserDetailsImpl user = (UserDetailsImpl)authentication.getPrincipal();
		Long id = user.getId();
		System.out.println(id);
		Map<String, Object> response = new HashMap<>();
		response.put("user", authentication.getPrincipal());
		response.put("errorCode", 1);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
