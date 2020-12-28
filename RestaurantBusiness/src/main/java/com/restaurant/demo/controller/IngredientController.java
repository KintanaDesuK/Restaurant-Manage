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
public class IngredientController {

	@Autowired
	IngredientRepository ingredientRepository;
	
	
	@PostMapping("/ingredient")
	public ResponseEntity<?> createIngredient(@RequestBody @Valid Ingredient ingredient, BindingResult result) {
		if (result.hasErrors()) {
			StringBuilder devErrorMsg = new StringBuilder();
			List<ObjectError> allErrors = result.getAllErrors();
			for (ObjectError objectError : allErrors) {
				devErrorMsg.append(objectError.getDefaultMessage() + "\n");
			}
			ErrorDetails errorDetails = new ErrorDetails();
			errorDetails.setErrorCode("ERR-1400");// Business specific error codes
			errorDetails.setErrorMessage("Invalid IngredientCategory data received");
			errorDetails.setDevErrorMessage(devErrorMsg.toString());

			return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
		}
		Ingredient savedIngredient = ingredientRepository.save(ingredient);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("MyResponseHeader", "MyValue");
		return new ResponseEntity<>(savedIngredient, responseHeaders, HttpStatus.CREATED);
	}

	@GetMapping("/ingredient")
	public Page<Ingredient> listCategories(Pageable pageable) {
		return ingredientRepository.findAll(pageable);
	}

	@GetMapping("/ingredient/{id}")
	public Ingredient ingredient(@PathVariable("id") Long id) {
		return ingredientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No IngredientCategory found with id=" + id));
	}

	@PutMapping("/ingredient/{id}")
	public Ingredient updateIngredient(@PathVariable("id") Long id, @RequestBody @Valid Ingredient ingredient, BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException("Invalod IngredientCategory data");
		}
		ingredientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No IngredientCategory found with id=" + id));
		return ingredientRepository.save(ingredient);
	}

	@DeleteMapping("/ingredient/{id}")
	public void deletetIngredient(@PathVariable("id") Long id) {
		Ingredient ingredient = ingredientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No IngredientCategory found with id=" + id));
		try {
			ingredientRepository.deleteById(id);
		} catch (Exception e) {
			throw new PostDeletionException("IngredientCategory with id=" + id + " can't be deleted");
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
