package com.poc.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.poc.backend.model.Order;

@Service
public class RestaurantServiceImpl implements RestaurantService {
	
	static int slotCounter = 0;
	List<Order> copyOrder = new ArrayList<Order>();
	List<String> result = new ArrayList<String>();
	
	@Value("${com.restaurant.maxOrders}")
	private int maxAffordableSlot;
	
	@Value("${com.restaurant.timePerKm}")
	private int deliveryPerKm;
	
	@Value("${com.restaurant.maxWaitTime}")
	private float maxWaitTime;
	
	private int totalSlotNeeded = 0;	
	private float deliveryTime = 0.0f;
	private int appCounter = 0;
	private int mealCounter = 0;
	private float minCookTime;
	private float timeNeededToCook;

	public boolean checkPrerequisite(Order order) {
		totalSlotNeeded = 0;
		appCounter = 0;
		mealCounter = 0;
		order.setDeliveryTime(0);
		order.getMeals().forEach(orderItem -> {
			if(orderItem.equals("A")) {
				totalSlotNeeded += 1;
				++appCounter;
			} else {
				totalSlotNeeded += 2;
				++mealCounter;
			}
		});
		order.setTotalWeight(totalSlotNeeded);
		order.setCountOfAppe(appCounter);
		order.setCountOfMeals(mealCounter);
		if(totalSlotNeeded > maxAffordableSlot) {
			order.setAccepted(false);
			result.add("Order " + order.getOrderId() + " is denied because the restaurant cannot accommodate it.");
			System.out.println("Order " + order.getOrderId() + " is denied because the restaurant cannot accommodate it.");
			return false;
		}
		order.setAccepted(true);
		return true;
	}
	
	public boolean checkSlotAvailability(Order order) {
		boolean result = false;
		slotCounter += order.getTotalWeight();
		if(slotCounter <= maxAffordableSlot) {
			return true;
		}
		return result;
	}
	
	public float timeNeededToDeliver(Order order) {
		timeNeededToCook = 0.0f;
		if(order.getCountOfAppe() > order.getCountOfMeals()) {
			timeNeededToCook += 17;
		} else {
			timeNeededToCook += 29;
		}
		timeNeededToCook += (order.getDistance() * deliveryPerKm);
		order.setDeliveryTime(timeNeededToCook);
		return timeNeededToCook;
	}
	
	public float timeNeededToDeliverAfterWaiting(Order order) {
		timeNeededToCook = 0.0f;
		minCookTime = Float.MAX_VALUE;
		copyOrder.forEach(item -> {
			if(item.getAccepted() && !(item.getHasDelivered()) && (item.getDeliveryTime() != 0.0 && minCookTime >item.getDeliveryTime())) {
				minCookTime = item.getDeliveryTime();
			}
		});
		copyOrder.forEach(item -> {
			if(item.getAccepted() && !(item.getHasDelivered()) && (item.getDeliveryTime() == minCookTime)) {
				if(order.getCountOfAppe() > order.getCountOfMeals()) {
					timeNeededToCook += 17;
				} else {
					timeNeededToCook += 29;
				}
				timeNeededToCook += minCookTime + (order.getDistance() * deliveryPerKm);
				order.setDeliveryTime(timeNeededToCook);
				//So that this item won't be considered for calculating next minimum cook time.
				slotCounter -= item.getTotalWeight();
				item.setHasDelivered(true);
			}
		});
		return timeNeededToCook;
	}
	
	public float cookTime(Order order) {
		deliveryTime = 0.0f;
		if(order.getAccepted()) {
			boolean canCook = checkSlotAvailability(order);
			if(canCook) {
				deliveryTime = timeNeededToDeliver(order);
			} else {
				deliveryTime = timeNeededToDeliverAfterWaiting(order);
			}
		}
		return deliveryTime;
	}

	@Override
	public List<String> calculateTime(List<Order> order) {
		copyOrder.addAll(order);
		result.clear();
		order.forEach(item -> {
			boolean flag = checkPrerequisite(item);
			if(flag) {
				float time = cookTime(item);
				if(time > maxWaitTime) {
					result.add("Order " + item.getOrderId() + " is not accepeted as it's waiting time exceeds 150 minutes");
					System.out.println("Order " + item.getOrderId() + " is not accepeted as it's waiting time exceeds 150 minutes");
				} else {
					result.add("Order " + item.getOrderId() + " will get delivered in " + time + " minutes");
					System.out.println("Order " + item.getOrderId() + " will get delivered in " + time + " minutes");
				}
			}
		});	
		return result;
	}

		

}
