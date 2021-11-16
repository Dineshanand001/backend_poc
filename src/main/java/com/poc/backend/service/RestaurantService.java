package com.poc.backend.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.poc.backend.model.Order;

@Component
public interface RestaurantService {

	public List<String> calculateTime(List<Order> order);

}
