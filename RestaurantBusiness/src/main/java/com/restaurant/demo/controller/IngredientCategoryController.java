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

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.validation.Valid;
import java.util.Optional;

import com.restaurant.demo.exception.*;
import com.restaurant.demo.entity.*;
import com.restaurant.demo.repository.*;

@RestController
@RequestMapping(value = "/api")
public class IngredientCategoryController {

	@Autowired
	IngredientCategoryRepository ingredientCategoryRepository;
	
	
	@PostMapping("/ingredientcategory")
	public ResponseEntity<Map<String, Object>> createIngredientCategory(@RequestBody @Valid IngredientCategory ingredientCategory, BindingResult result) {
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
		
		IngredientCategory savedIngredientCategory = ingredientCategoryRepository.save(ingredientCategory);
		Map<String, Object> response = new HashMap<>();
		response.put("errorCode",1);
		response.put("ingredientCategory", savedIngredientCategory);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}

	@GetMapping("/ingredientcategory")
	public ResponseEntity<Map<String, Object>> listIngredientCategory(@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "100") int size) {
		try {
			  List<IngredientCategory> ingredientCategories = new ArrayList<IngredientCategory>();
		      Pageable paging = PageRequest.of(page, size);
		      
		      Page<IngredientCategory> pageTuts;
		      pageTuts = ingredientCategoryRepository.findAll(paging);

		      ingredientCategories = pageTuts.getContent();

		      Map<String, Object> response = new HashMap<>();
		      response.put("areas", ingredientCategories);
		      response.put("currentPage", pageTuts.getNumber());
		      response.put("totalItems", pageTuts.getTotalElements());
		      response.put("totalPages", pageTuts.getTotalPages());

		      return new ResponseEntity<>(response, HttpStatus.OK);
		    } catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	}

	@GetMapping("/ingredientcategory/{id}")
	public IngredientCategory ingredientCategory(@PathVariable("id") Integer id) {
		return ingredientCategoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No IngredientCategory found with id=" + id));
	}

	@PutMapping("/ingredientcategory/{id}")
	public  ResponseEntity<Map<String, Object>> updateIngredientCategory(@PathVariable("id") Integer id, @RequestBody @Valid IngredientCategory ingredientCategory, BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException("Invalod IngredientCategory data");
		}
		if(ingredientCategoryRepository.existsById(id)) {
			
			Optional<IngredientCategory> ingredientCategoryData = ingredientCategoryRepository.findById(id);
			if (ingredientCategoryData.isPresent()) 
			{
				IngredientCategory _ingredientCategory = ingredientCategoryData.get();
				_ingredientCategory.setIngCateName(ingredientCategory.getIngCateName());
	            IngredientCategory saveIngredientCategory = ingredientCategoryRepository.save(_ingredientCategory);
			
				Map<String, Object> response = new HashMap<>();  
				response.put("ingredientCategory", saveIngredientCategory);
				response.put("errorCode", 1);
 				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			else
			{
				throw new ResourceNotFoundException("Invalid Ingredient Category data");
			}
		}
		throw new ResourceNotFoundException("Invalid IngredientCategory Id");	
	}

	@DeleteMapping("/ingredientcategory/{id}")
	public ResponseEntity<Map<String, Object>> deletetIngredientCategory(@PathVariable("id") Integer id) {
		IngredientCategory ingredientCategory = ingredientCategoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No IngredientCategory found with id=" + id));
		try {
			ingredientCategoryRepository.deleteById(id);
			Map<String, Object> response = new HashMap<>();  
			response.put("message", "Delete Success");
			response.put("errorCode", 1);
			return  new ResponseEntity<>(response, HttpStatus.OK);
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
