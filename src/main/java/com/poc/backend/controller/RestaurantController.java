package com.poc.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poc.backend.model.Order;
import com.poc.backend.service.RestaurantService;

@RestController()
@RequestMapping("/api")
public class RestaurantController {
	
	@Autowired
	RestaurantService restaurantService;
	
	@PostMapping(value = "/orderTime")
	public List<String> hello(@RequestBody List<Order> orders) {
		return restaurantService.calculateTime(orders);
	}

}
