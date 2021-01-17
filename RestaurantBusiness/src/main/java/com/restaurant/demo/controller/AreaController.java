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
public class AreaController {

	@Autowired
	AreaRepository areaRepository;
	
	@Autowired
	DeskRepository deskRepository;
	
	@PostMapping("/area")
	public ResponseEntity<Map<String, Object>> createArea(@RequestBody @Valid Area area, BindingResult result) {
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

		Area savedArea = areaRepository.save(area);
		Map<String, Object> response = new HashMap<>();
		response.put("desk", savedArea);
		response.put("errorCode", 1);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping("/area")
	public ResponseEntity<Map<String, Object>> listAreas(@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "100") int size) {
		try {
			  List<Area> areas = new ArrayList<Area>();
		      Pageable paging = PageRequest.of(page, size);
		      
		      Page<Area> pageTuts;
		      pageTuts = areaRepository.findAll(paging);

		      areas = pageTuts.getContent();

		      Map<String, Object> response = new HashMap<>();
		      response.put("areas", areas);
		      response.put("currentPage", pageTuts.getNumber());
		      response.put("totalItems", pageTuts.getTotalElements());
		      response.put("totalPages", pageTuts.getTotalPages());

		      return new ResponseEntity<>(response, HttpStatus.OK);
		    } catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	}

	@GetMapping("/area/{id}")
	public Area getArea(@PathVariable("id") Integer id) {
		return areaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No post found with id=" + id));
	}

//	@GetMapping("/area/desk/{deskId}")
//	public Area getAreaByDeskId(@PathVariable("id") Integer id) {
//		return areaRepository.findAreaByDesk(id).or;
//	}
	@PutMapping("/area/{id}")
	public ResponseEntity<Map<String, Object>> updateArea(@PathVariable("id") Integer id, @RequestBody @Valid Area area, BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException("Invalod Category data");
		}
		if(areaRepository.existsById(id)) {
			
			Optional<Area> areaData = areaRepository.findById(id);
			if (areaData.isPresent()) 
			{
				Area _area = areaData.get();
	            _area.setAreaName(area.getAreaName());
	            Area savedArea = areaRepository.save(_area);
			
				Map<String, Object> response = new HashMap<>();  
				response.put("area", savedArea);
				response.put("errorCode", 1);
 				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			else
			{
				throw new ResourceNotFoundException("Invalid Desk data");
			}
	    }
		throw new ResourceNotFoundException("Invalid Area Id");	
	}

	@DeleteMapping("/area/{id}")
	public ResponseEntity<Map<String, Object>> deleteArea(@PathVariable("id") Integer id) {
		Area area = areaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No Category found with id=" + id));
		try {
			areaRepository.deleteById(id);
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
