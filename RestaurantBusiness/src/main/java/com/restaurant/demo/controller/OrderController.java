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
import java.security.Principal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import javax.validation.Valid;
import java.util.Optional;

import com.restaurant.demo.exception.*;
import com.restaurant.demo.entity.*;
import com.restaurant.demo.repository.*;
import com.restaurant.demo.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class OrderController {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	DeskRepository deskRepository;
	
	@Autowired
	OrderDetailRepository orderDetailRepository;
	
	@Autowired
	DishRepository dishRepository;
	
	@PostMapping("/desk/{deskId}/order")
	@PreAuthorize("hasRole('CASHIER')")
	public ResponseEntity<Map<String, Object>> createOrder(Authentication authentication, @PathVariable Integer deskId, @RequestBody @Valid Order order, BindingResult result) {
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
		if(!deskRepository.existsById(deskId)) {
        	throw new ResourceNotFoundException("No are found with id=" + deskId);
        }
		else {
			UserDetailsImpl user = (UserDetailsImpl)authentication.getPrincipal();
			Long id = user.getId();
			User _user = userRepository.findById(id).orElse(null);
			Desk _desk = deskRepository.findById(deskId).orElse(null);
			order.setDesk(_desk);
			order.setUser(_user);
		}
		Order savedOrder = orderRepository.save(order);
		Map<String, Object> response = new HashMap<>();
		response.put("order", savedOrder);
		response.put("errorCode", 1);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PostMapping("/order/{orderId}/dish/{dishId}/orderdetail")
	@PreAuthorize("hasRole('CASHIER')")
	public ResponseEntity<Map<String, Object>> createOrderDetail(@PathVariable Long orderId, @PathVariable Long dishId ,@RequestBody @Valid OrderDetail orderDetail, BindingResult result) {
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
		if(!orderRepository.existsById(orderId)) {
        	throw new ResourceNotFoundException("No are found with id=" + orderId);
        }
		else {
			
			Order _order = orderRepository.findById(orderId).orElseThrow();
			Dish _dish = dishRepository.findById(dishId).orElseThrow();
			
			orderDetail.setOrder(_order);
			orderDetail.setDish(_dish);
			Optional<Order> orderData = orderRepository.findById(orderId);
			if (orderData.isPresent()) 
			{
				double totalPrice;
				Order _orderUpdate = orderData.get();
				if(_orderUpdate.getTotalPrice() == null)
				{
					totalPrice = 0;
				} else
				{
					totalPrice = _orderUpdate.getTotalPrice();
				}
				double updateTotalPrice;
				updateTotalPrice = totalPrice+ orderDetail.getPrice();
				_orderUpdate.setTotalPrice(updateTotalPrice);
				orderRepository.save(_orderUpdate);
			}
			
		}
		OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);
		Map<String, Object> response = new HashMap<>();
		response.put("orderDetail", savedOrderDetail);
		response.put("errorCode", 1);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

		@GetMapping("/order/{id}")
		@PreAuthorize("hasRole('CASHIER') or hasRole('ADMIN')")
		public Order getOrder(@PathVariable("id") Long id) {
			return orderRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("No Order found with id=" + id));
		}
	
		@GetMapping("/order")
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<Map<String, Object>> listDeskByArea(@RequestParam(defaultValue = "0") int page,
		        @RequestParam(defaultValue = "100") int size) {
			try {
				List<Order> orders = new ArrayList<Order>();
			      Pageable paging = PageRequest.of(page, size);
			      
			      Page<Order> pageTuts;
			      pageTuts = orderRepository.findAll(paging);

			      orders = pageTuts.getContent();
			      Map<String, Object> response = new HashMap<>();
			      response.put("orders", orders);
			      response.put("currentPage", pageTuts.getNumber());
			      response.put("totalItems", pageTuts.getTotalElements());
			      response.put("totalPages", pageTuts.getTotalPages());
			      response.put("errorCode", 1);
			      return new ResponseEntity<>(response, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	@PutMapping("/desk/{deskId}/order/{orderId}")
	@PreAuthorize("hasRole('CASHIER')")
	public ResponseEntity<Map<String, Object>> updateDesk(Authentication authentication, @PathVariable Integer deskId, @PathVariable Long orderId, @RequestBody @Valid Order order, BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException("Invalid Category data");
		}
		UserDetailsImpl user = (UserDetailsImpl)authentication.getPrincipal();
		Long id = user.getId();
		if(deskRepository.existsById(deskId)) {
			
			Optional<Order> orderData = orderRepository.findById(orderId);
			if (orderData.isPresent()) 
			{
				Order _order = orderData.get();
	            _order.setCustomerName(order.getCustomerName());
	            _order.setStatus(order.getStatus());
	            _order.setMoneyPaid(order.getMoneyPaid());
	            _order.setTotalPrice(order.getTotalPrice());
	            _order.setPromotion(order.getPromotion());
	          
	            _order.setDesk(deskRepository.findById(deskId).orElseThrow());
	            _order.setUser(userRepository.findById(id).orElseThrow());
	            Order savedOrder = orderRepository.save(_order);
				Map<String, Object> response = new HashMap<>();  
				response.put("order", savedOrder);
				response.put("errorCode", 1);
 				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			else
			{
				throw new ResourceNotFoundException("Invalid Order data");
			}
	    }
		throw new ResourceNotFoundException("Invalid Desk Id");	
	}
	@DeleteMapping("/order/{id}")
	@PreAuthorize("hasRole('CASHIER')")
	public ResponseEntity<Map<String, Object>> deleteArea(@PathVariable("id") Long id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No Category found with id=" + id));
		try {
			orderRepository.deleteById(id);
			Map<String, Object> response = new HashMap<>();  
			response.put("message", "Delete Success");
			response.put("errorCode", 1);
			return  new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new PostDeletionException("Area with id=" + id + " can't be deleted");
		}
		
		
	}
}
