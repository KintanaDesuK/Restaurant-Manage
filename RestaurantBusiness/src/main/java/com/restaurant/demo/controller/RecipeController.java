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
public class RecipeController {

	@Autowired
	RecipeRepository recipeRepository;
	
	
	@PostMapping("/recipe")
	public ResponseEntity<?> createRecipe(@RequestBody @Valid Recipe recipe, BindingResult result) {
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
		Recipe savedRecipe = recipeRepository.save(recipe);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("MyResponseHeader", "MyValue");
		return new ResponseEntity<>(savedRecipe, responseHeaders, HttpStatus.CREATED);
	}

	@GetMapping("/recipe")
	public Page<Recipe> listRecipes(Pageable pageable) {
		return recipeRepository.findAll(pageable);
	}

	@GetMapping("/recipe/{id}")
	public Recipe getRecipe(@PathVariable("id") Long id) {
		return recipeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No post found with id=" + id));
	}

	@PutMapping("/recipe/{id}")
	public Recipe updateRecipe(@PathVariable("id") Long id, @RequestBody @Valid Recipe recipe, BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException("Invalod Category data");
		}
		recipeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Category found with id=" + id));
		return recipeRepository.save(recipe);
	}

	@DeleteMapping("/recipe/{id}")
	public void deleteCategory(@PathVariable("id") Long id) {
		Recipe recipe = recipeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No Category found with id=" + id));
		try {
			recipeRepository.deleteById(id);
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
