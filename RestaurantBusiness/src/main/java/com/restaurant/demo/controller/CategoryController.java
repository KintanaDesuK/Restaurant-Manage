package com.restaurant.demo.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
	public ResponseEntity<Map<String, Object>> createCategory(@RequestBody @Valid Category category, BindingResult result) {
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

			Map<String, Object> response = new HashMap<>();
			response.put("errorCo",errorDetails);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		Category savedCategory = categoryRepository.save(category);
		Map<String, Object> response = new HashMap<>();
		response.put("Category", savedCategory);
		response.put("errorCode", 1);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping("/category")
	public ResponseEntity<Map<String, Object>> listCategories(@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "100") int size) {
		
		try {
			  List<Category> categories = new ArrayList<Category>();
		      Pageable paging = PageRequest.of(page, size);
		      
		      Page<Category> pageTuts;
		      pageTuts = categoryRepository.findAll(paging);

		      categories = pageTuts.getContent();

		      Map<String, Object> response = new HashMap<>();
		      response.put("categories", categories);
		      response.put("currentPage", pageTuts.getNumber());
		      response.put("totalItems", pageTuts.getTotalElements());
		      response.put("totalPages", pageTuts.getTotalPages());

		      return new ResponseEntity<>(response, HttpStatus.OK);
		    } catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	}

	@GetMapping("/category/{id}")
	public Category getCategory(@PathVariable("id") Integer id) {
		return categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No post found with id=" + id));
	}

	@PutMapping("/category/{id}")
	public ResponseEntity<Map<String, Object>> updateCategory(@PathVariable("id") Integer id, @RequestBody @Valid Category category, BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException("Invalod Category data");
		}
		if(categoryRepository.existsById(id)) {
			Optional<Category> categoryData = categoryRepository.findById(id);
			if (categoryData.isPresent()) 
			{
				Category _category = categoryData.get();
				_category.setName(category.getName());
	            Category savedCategory = categoryRepository.save(_category);
			
				Map<String, Object> response = new HashMap<>();  
				response.put("category", savedCategory);
				response.put("errorCode", 1);
 				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			else
			{
				throw new ResourceNotFoundException("Invalid Category data");
			}
	    }
		throw new ResourceNotFoundException("Invalid Category Id");	
	}

	@DeleteMapping("/category/{id}")
	public ResponseEntity<Map<String, Object>> deleteCategory(@PathVariable("id") Integer id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No Category found with id=" + id));
		try {
			categoryRepository.deleteById(id);
			Map<String, Object> response = new HashMap<>();  
			response.put("message", "Delete Success");
			response.put("errorCode", 1);
			return  new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new PostDeletionException("Area with id=" + id + " can't be deleted");
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
