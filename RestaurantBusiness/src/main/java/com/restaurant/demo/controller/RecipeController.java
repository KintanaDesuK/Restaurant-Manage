package com.restaurant.demo.controller;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	@Autowired
	DishRepository dishRepository;
	
	@Autowired
	IngredientRepository ingredientRepository;
	
	
	@PostMapping("/dish/{dishId}/ingredient/{ingredientId}/recipe")
	public ResponseEntity<Map<String, Object>> createRecipe(@PathVariable Long dishId, @PathVariable Long ingredientId  ,@RequestBody @Valid Recipe recipe, BindingResult result) {
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
		 if(!dishRepository.existsById(dishId) || !ingredientRepository.existsById(ingredientId)) {
	        	throw new ResourceNotFoundException("No are found with Dish id=" + dishId + " and Ingredient id=" + ingredientId);
	        }
		 else {
			  Dish _dish = dishRepository.findById(dishId).orElse(null);
			  Ingredient _inIngredient = ingredientRepository.findById(ingredientId).orElse(null);
			  recipe.setDish(_dish);
			  recipe.setIngredient(_inIngredient);	 
		 }
		double sum = 0;
		Optional<Ingredient> ingredientData = ingredientRepository.findById(ingredientId);
		Ingredient _ingredient = ingredientData.get();
		sum = (recipe.getQuantity() *_ingredient.getIngPrice()) / _ingredient.getQuantity();
		
		
		Recipe savedRecipe = recipeRepository.save(recipe);
		Map<String, Object> response = new HashMap<>();
		response.put("recipe", savedRecipe);
		response.put("totalPrice", sum );
		response.put("errorCode", 1);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	
	@GetMapping("/recipe/{id}")
	public Recipe getRecipe(@PathVariable("id") Long id) {
		return recipeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No post found with id=" + id));
	}

	@PutMapping("/dish/{dishId}/ingredient/{ingredientId}/recipe/{recipeId}")
	public ResponseEntity<Map<String, Object>> updateRecipe(@PathVariable Long dishId, @PathVariable Long ingredientId, @PathVariable Long recipeId, @RequestBody @Valid Recipe recipe, BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException("Invalod Category data");
		}
		if(dishRepository.existsById(dishId) && ingredientRepository.existsById(ingredientId)) {
			Dish _dish = dishRepository.findById(dishId).orElse(null);
			Ingredient _inIngredient = ingredientRepository.findById(ingredientId).orElse(null);
			Optional<Recipe> recipeData = recipeRepository.findById(recipeId);
			if (recipeData.isPresent()) 
			{
				Recipe _recipe = recipeData.get();
				_recipe.setQuantity(recipe.getQuantity());
				_recipe.setDish(_dish);
				_recipe.setIngredient(_inIngredient);
	            Recipe savedRecipe = recipeRepository.save(_recipe);
			
				Map<String, Object> response = new HashMap<>();  
				response.put("recipe", savedRecipe);
				response.put("errorCode", 1);
 				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			else
			{
				throw new ResourceNotFoundException("Invalid Category data");
			}
	    }
		throw new ResourceNotFoundException("Invalid Recipe Id");	
	}

	@DeleteMapping("/recipe/{id}")
	public ResponseEntity<Map<String, Object>> deleteRecipe(@PathVariable("id") Long id) {
		Recipe recipe = recipeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No Recipe found with id=" + id));
		try {
			recipeRepository.deleteById(id);
			Map<String, Object> response = new HashMap<>();  
			response.put("message", "Delete Success");
			response.put("errorCode", 1);
			return  new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new PostDeletionException("Recipe with id=" + id + " can't be deleted");
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
