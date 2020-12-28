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
public class CategoryController {
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	DishRepository dishRepository;
	
	@PostMapping("/category")
	public ResponseEntity<?> createCategory(@RequestBody @Valid Category category, BindingResult result) {
		if (result.hasErrors()) {
			StringBuilder devErrorMsg = new StringBuilder();
			List<ObjectError> allErrors = result.getAllErrors();
			for (ObjectError objectError : allErrors) {
				devErrorMsg.append(objectError.getDefaultMessage() + "\n");
			}
			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode("ERR-1400");// Business specific error codes
			errorDetails.setErrorMessage("Invalid Post data received");
			errorDetails.setDevErrorMessage(devErrorMsg.toString());

			return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
		}
		Category savedCategory = categoryRepository.save(category);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("MyResponseHeader", "MyValue");
		return new ResponseEntity<>(savedCategory, responseHeaders, HttpStatus.CREATED);
	}

	@GetMapping("/category")
	public Page<Category> listCategories(Pageable pageable) {
		return categoryRepository.findAll(pageable);
	}

	@GetMapping("/category/{id}")
	public Category getCategory(@PathVariable("id") Integer id) {
		return categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No post found with id=" + id));
	}

	@PutMapping("/category/{id}")
	public Category updateCategory(@PathVariable("id") Integer id, @RequestBody @Valid Category category, BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException("Invalod Category data");
		}
		categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Category found with id=" + id));
		return categoryRepository.save(category);
	}

	@DeleteMapping("/category/{id}")
	public void deleteCategory(@PathVariable("id") Integer id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No Category found with id=" + id));
		try {
			categoryRepository.deleteById(id);
		} catch (Exception e) {
			throw new PostDeletionException("Category with id=" + id + " can't be deleted");
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
