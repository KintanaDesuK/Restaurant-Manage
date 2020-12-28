package com.restaurant.demo.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import com.restaurant.demo.exception.*;
import com.restaurant.demo.entity.*;
import com.restaurant.demo.repository.*;


@RestController
@RequestMapping(value = "/api")
public class DishController {

	@Autowired
	DishRepository dishRepository;
	
	
	@PostMapping("/dish")
	public ResponseEntity<?> createDish(@RequestBody @Valid Dish dish, BindingResult result) {
		if (result.hasErrors()) {
			StringBuilder devErrorMsg = new StringBuilder();
			List<ObjectError> allErrors = result.getAllErrors();
			for (ObjectError objectError : allErrors) {
				devErrorMsg.append(objectError.getDefaultMessage() + "\n");
			}
			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode("ERR-1400");// Business specific error codes
			errorDetails.setErrorMessage("Invalid Dish data received");
			errorDetails.setDevErrorMessage(devErrorMsg.toString());

			return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
		}
		
		Dish savedDish = dishRepository.save(dish);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("MyResponseHeader", "MyValue");
		return new ResponseEntity<>(savedDish, responseHeaders, HttpStatus.CREATED);
	}

	@GetMapping("/dish")
	public Page<Dish> listDishs(Pageable pageable) {
		return dishRepository.findAll(pageable);
	}

	@GetMapping("/dish/{id}")
	public Dish getCategory(@PathVariable("id") Long id) {
		return dishRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No dish found with id=" + id));
	}

	@PatchMapping("/dish/{id}")
	public Dish updateDish(@PathVariable("id") Long id, @RequestBody @Valid Dish dish, BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException("Invalod Dish data");
		}
		dishRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Dish found with id=" + id));
		return dishRepository.save(dish);
	}

	@DeleteMapping("/dish/{id}")
	public void deleteCategory(@PathVariable("id") Long id) {
		Dish dish = dishRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No Dish found with id=" + id));
		try {
			dishRepository.deleteById(id);
		} catch (Exception e) {
			throw new PostDeletionException("Dish with id=" + id + " can't be deleted");
		}
	}

	@ExceptionHandler(PostDeletionException.class)
	public ResponseEntity<?> servletRequestBindingException(PostDeletionException e) {
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setErrorMessage(e.getMessage());
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		errorDetails.setDevErrorMessage(sw.toString());
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
