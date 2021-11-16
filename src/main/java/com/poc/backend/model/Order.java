package com.poc.backend.model;

import java.util.List;

public class Order {

	int orderId;
	List<String> meals;
	float distance;
	int totalWeight;
	float deliveryTime;
	int countOfAppe;
	int countOfMeals;
	boolean isAccepted;
	boolean hasDelivered;
	
	public float getDistance() {
		return distance;
	}
	
	public List<String> getMeals() {
		return meals;
	}
	
	public int getOrderId() {
		return orderId;
	}
	
	public void setTotalWeight(int totalWeight) {
		this.totalWeight = totalWeight;
	}
	
	public int getTotalWeight() {
		return totalWeight;
	}
	
	public void setDeliveryTime(float deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	
	public float getDeliveryTime() {
		return deliveryTime;
	}
	
	public void setAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted;
	}
	
	public boolean getAccepted() {
		return isAccepted;
	}
	
	public void setCountOfAppe(int countOfAppe) {
		this.countOfAppe = countOfAppe;
	}
	
	public void setCountOfMeals(int countOfMeals) {
		this.countOfMeals = countOfMeals;
	}
	
	public int getCountOfAppe() {
		return countOfAppe;
	}
	
	public int getCountOfMeals() {
		return countOfMeals;
	}
	
	public void setHasDelivered(boolean hasDelivered) {
		this.hasDelivered = hasDelivered;
	}
	
	public boolean getHasDelivered() {
		return hasDelivered;
	}
	
	
}
