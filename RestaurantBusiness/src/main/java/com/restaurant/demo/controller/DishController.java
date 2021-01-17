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
import com.restaurant.demo.payload.response.MessageResponse;
import com.restaurant.demo.entity.*;
import com.restaurant.demo.repository.*;

import net.bytebuddy.agent.builder.AgentBuilder.RawMatcher.Disjunction;


@RestController
@RequestMapping(value = "/api")
public class DishController {

	@Autowired
	DishRepository dishRepository;
	
	@Autowired
	CategoryRepository categoryRepository;

	
	@PostMapping("/category/{categoryId}/dish")
	public ResponseEntity<Map<String, Object>> createDish(@PathVariable Integer categoryId ,@RequestBody @Valid Dish dish, BindingResult result) {
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

			Map<String, Object> response = new HashMap<>();
			response.put("errorCo",errorDetails);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		 if(!categoryRepository.existsById(categoryId)) {
	        	throw new ResourceNotFoundException("No are found with id=" + categoryId);
	        }
		 else {
			  Category _category = categoryRepository.findById(categoryId).orElse(null);
			  dish.setCategory(_category);
		 }
		Dish savedDish = dishRepository.save(dish);
		Map<String, Object> response = new HashMap<>();
		response.put("dish", savedDish);
		response.put("errorCode", 1);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping("/dish")
	public ResponseEntity<Map<String, Object>> listDishs(@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "100") int size) {
		try {
			  List<Dish> dishs = new ArrayList<Dish>();
		      Pageable paging = PageRequest.of(page, size);
		      
		      Page<Dish> pageTuts;
		      pageTuts = dishRepository.findAll(paging);

		      dishs = pageTuts.getContent();

		      Map<String, Object> response = new HashMap<>();
		      response.put("dishs", dishs);
		      response.put("currentPage", pageTuts.getNumber());
		      response.put("totalItems", pageTuts.getTotalElements());
		      response.put("totalPages", pageTuts.getTotalPages());
		      response.put("errorCode", 1);

		      return new ResponseEntity<>(response, HttpStatus.OK);
		    } catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	}

	@GetMapping("/dish/{id}")
	public ResponseEntity<Map<String, Object>> getDish(@PathVariable("id") Long id) {
		try {
			Integer cateId = 0;
			Optional<Dish> dishData = dishRepository.findById(id);
				if (dishData.isPresent()) 
					
				{
					Dish _dish = dishData.get();
					cateId = _dish.getCategory().getId();
					Map<String, Object> response = new HashMap<>();
				      response.put("dishs", dishData);
				      response.put("cateId", cateId);
				      response.put("errorCode", 1);
				      return new ResponseEntity<>(response, HttpStatus.OK);
				}
				else {
					throw new ResourceNotFoundException("Invalid Desk data");
				}
		      
		    } catch (Exception e) {
		    	Map<String, Object> response = new HashMap<>();
		    	response.put("errorCode", 0);
		      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	}

	@PutMapping("/category/{categoryId}/dish/{dishId}")
	public ResponseEntity<Map<String, Object>> updateDish( @PathVariable Integer categoryId  ,@PathVariable Long dishId, @RequestBody @Valid Dish dish, BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException("Invalid Dish data");
		}
		if(categoryRepository.existsById(categoryId)) {
			Category _category = categoryRepository.findById(categoryId).orElse(null);
			Optional<Dish> dishData = dishRepository.findById(dishId);
			if (dishData.isPresent()) 
			{
				Dish _dish = dishData.get();
	            _dish.setDishName(dish.getDishName());
	            _dish.setDishPrice(dish.getDishPrice());
	            _dish.setImagePath(dish.getImagePath());
	            _dish.setOnSale(dish.getOnSale());
	            _dish.setStatus(dish.getStatus());
	            _dish.setCategory(_category);
	            
	            Dish savedDish = dishRepository.save(_dish);
			
				Map<String, Object> response = new HashMap<>();  
				response.put("dish", savedDish);
				response.put("errorCode", 1);
 				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			else
			{
				throw new ResourceNotFoundException("Invalid Dish data");
			}
	    }
		throw new ResourceNotFoundException("Invalid Category Id");	
	}

	@DeleteMapping("/dish/{id}")
	public ResponseEntity<Map<String, Object>> deleteCategory(@PathVariable("id") Long id) {
		Dish dish = dishRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No Dish found with id=" + id));
		try {
			dishRepository.deleteById(id);
			Map<String, Object> response = new HashMap<>();  
			response.put("message", "Delete Success");
			response.put("errorCode", 1);
			return  new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new PostDeletionException("Desk with id=" + id + " can't be deleted");
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
