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
public class IngredientController {

	@Autowired
	IngredientRepository ingredientRepository;
	
	@Autowired
	IngredientCategoryRepository ingredientCategoryRepository;
	
	@PostMapping("/ingredientcategory/{categoryId}/ingredient")
	public ResponseEntity<Map<String, Object>> createIngredient(@PathVariable Integer categoryId, @RequestBody @Valid Ingredient ingredient, BindingResult result) {
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

			Map<String, Object> response = new HashMap<>();
			response.put("errorCo",errorDetails);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		if(!ingredientCategoryRepository.existsById(categoryId)) {
        	throw new ResourceNotFoundException("No are found with id=" + categoryId);
        }
		else {
			  IngredientCategory _inIngredientCategory = ingredientCategoryRepository.findById(categoryId).orElse(null);
			  ingredient.setIngredientCategory(_inIngredientCategory);
		}
		Ingredient savedIngredient = ingredientRepository.save(ingredient);
		Map<String, Object> response = new HashMap<>();
		response.put("ingredient", savedIngredient);
		response.put("errorCode", 1);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping("/ingredient")
	public ResponseEntity<Map<String, Object>>  listCategories(@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "100") int size) {
		try {
			  List<Ingredient> ingredients = new ArrayList<Ingredient>();
		      Pageable paging = PageRequest.of(page, size);
		      
		      Page<Ingredient> pageTuts;
		      pageTuts = ingredientRepository.findAll(paging);

		      ingredients = pageTuts.getContent();

		      Map<String, Object> response = new HashMap<>();
		      response.put("ingredients", ingredients);
		      response.put("currentPage", pageTuts.getNumber());
		      response.put("totalItems", pageTuts.getTotalElements());
		      response.put("totalPages", pageTuts.getTotalPages());
		      response.put("errorCode", 1);

		      return new ResponseEntity<>(response, HttpStatus.OK);
		    } catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	}

	@GetMapping("/ingredient/{id}")
	public Ingredient ingredient(@PathVariable("id") Long id) {
		return ingredientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No IngredientCategory found with id=" + id));
	}

	@PutMapping("/ingredientcategory/{categoryId}/ingredient/{ingredientId}")
	public ResponseEntity<Map<String, Object>> updateIngredient(@PathVariable Integer categoryId, @PathVariable Long ingredientId, @RequestBody @Valid Ingredient ingredient, BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException("Invalid Category data");
		}
		if(ingredientCategoryRepository.existsById(categoryId)) {
			Optional<Ingredient> ingredientData = ingredientRepository.findById(ingredientId);
			if (ingredientData.isPresent()) 
			{
				Ingredient _ingredient = ingredientData.get();
	            _ingredient.setIngName(ingredient.getIngName());
	            _ingredient.setIngPrice(ingredient.getIngPrice());
	            _ingredient.setQuantity(ingredient.getQuantity());
	            _ingredient.setUnit(ingredient.getUnit());
	            
	            Ingredient savedIngredient = ingredientRepository.save(_ingredient);
			
				Map<String, Object> response = new HashMap<>();  
				response.put("ingredient", savedIngredient);
				response.put("errorCode", 1);
 				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			else
			{
				throw new ResourceNotFoundException("Invalid Category data");
			}
	    }
		throw new ResourceNotFoundException("Invalid Area Id");	
	}

	@DeleteMapping("/ingredient/{id}")
	public ResponseEntity<Map<String, Object>>  deletetIngredient(@PathVariable("id") Long id) {
		Ingredient ingredient = ingredientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No IngredientCategory found with id=" + id));
		try {
			ingredientRepository.deleteById(id);
			Map<String, Object> response = new HashMap<>();  
			response.put("message", "Delete Success");
			response.put("errorCode", 1);
			return  new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new PostDeletionException("Ingredient with id=" + id + " can't be deleted");
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
