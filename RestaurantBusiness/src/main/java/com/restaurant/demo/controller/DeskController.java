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

@RestController
@RequestMapping(value = "/api")
public class DeskController {
	@Autowired
	DeskRepository deskRepository;
	
	@Autowired
	AreaRepository areaRepository;
	
	//Create Desk
	@PostMapping("/area/{areaId}/desk")
	public ResponseEntity<Map<String, Object>> createDesk(@PathVariable Integer areaId, @RequestBody @Valid Desk desk, BindingResult result) {
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
		
		 if(!areaRepository.existsById(areaId)) {
	        	throw new ResourceNotFoundException("No are found with id=" + areaId);
	        }
		 else {
			  Area _area = areaRepository.findById(areaId).orElse(null);
			  desk.setArea(_area);
		 }
		Desk savedDesk = deskRepository.save(desk);
		Map<String, Object> response = new HashMap<>();
		response.put("desk", savedDesk);
		response.put("errorCode", 1);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	//GetAllDesk
	@GetMapping("/desk")
	public ResponseEntity<Map<String, Object>> listDesks(  @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "100") int size) {
		try {
		  List<Desk> desks = new ArrayList<Desk>();
	      Pageable paging = PageRequest.of(page, size);
	      
	      Page<Desk> pageTuts;
	      pageTuts = deskRepository.findAll(paging);

	      desks = pageTuts.getContent();

	      Map<String, Object> response = new HashMap<>();
	      response.put("desks", desks);
	      response.put("currentPage", pageTuts.getNumber());
	      response.put("totalItems", pageTuts.getTotalElements());
	      response.put("totalPages", pageTuts.getTotalPages());
	      response.put("errorCode", 1);

	      return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	//Get Desk By Id
	@GetMapping("/desk/{id}")
	public Desk getDesk(@PathVariable("id") Integer id) {
		return deskRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No De found with id=" + id));
	}

	
	//Get Desk By Area
	@GetMapping("/area/{areaId}/desk/")
	public ResponseEntity<Map<String, Object>> listDeskByArea( @PathVariable Integer areaId,@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "100") int size) {
		try {
			List<Desk> desks = new ArrayList<Desk>();
		      Pageable paging = PageRequest.of(page, size);
		      
		      Page<Desk> pageTuts;
		      pageTuts = deskRepository.findByAreaAreaId(areaId, paging);

		      desks = pageTuts.getContent();
		      Map<String, Object> response = new HashMap<>();
		      response.put("desks", desks);
		      response.put("currentPage", pageTuts.getNumber());
		      response.put("totalItems", pageTuts.getTotalElements());
		      response.put("totalPages", pageTuts.getTotalPages());
		      response.put("errorCode", 1);
		      return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/area/{areaId}/desk/{deskId}")
	public ResponseEntity<Map<String, Object>> updateDesk(@PathVariable Integer areaId, @PathVariable Integer deskId, @RequestBody @Valid Desk desk, BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException("Invalid Category data");
		}
		if(areaRepository.existsById(areaId)) {
			
			Optional<Desk> deskData = deskRepository.findById(deskId);
			if (deskData.isPresent()) 
			{
				Desk _desk = deskData.get();
	            _desk.setNumOfSeat(desk.getNumOfSeat());
	            _desk.setStatus(desk.getStatus());
	            _desk.setTableNum(desk.getTableNum());
	            _desk.setArea(areaRepository.findById(areaId).orElseThrow());
	            Desk savedDesk = deskRepository.save(_desk);
			
				Map<String, Object> response = new HashMap<>();  
				response.put("desk", savedDesk);
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

	@DeleteMapping("/desk/{id}")
	public ResponseEntity<Map<String, Object>> deleteDesk(@PathVariable("id") Integer id) {
		Desk desk = deskRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No Desk found with id=" + id));
		try {
			deskRepository.deleteById(id);
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
