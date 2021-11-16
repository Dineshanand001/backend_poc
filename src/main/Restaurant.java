package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Restaurant {
	
	static int inProgressPreparations = 0;
	static float minCookTime = 0.0f;
	
	int orderId;
	List<MenuList> orderList;
	List<Integer> slotList;
	int maxAffordableSlot;
	int totalSlotNeeded;
	int appetizerCookTime;
	int mainCourseCookTime;
	float travelTime;
	
	public Restaurant(int orderId, ArrayList<MenuList> orderList, int distance) {
		this.orderId = orderId;
		this.orderList = orderList;
		this.slotList  = new ArrayList<Integer>();
		this.maxAffordableSlot = 7;
		this.totalSlotNeeded = 0;
		this.appetizerCookTime = 17;
		this.mainCourseCookTime = 29;
		this.travelTime = distance * 8;
	}
	
	public boolean checkPreRequisites() {		
		this.orderList.forEach(orderItem -> {
			if(orderItem == MenuList.Appetizer) {
				this.slotList.add(1);
				totalSlotNeeded += 1;
			} else {
				this.slotList.add(2);
				totalSlotNeeded += 2;
			}
		});
		if(totalSlotNeeded > this.maxAffordableSlot) {
			System.out.println("Order " + this.orderId + " is denied because the restaurant cannot accommodate it.");
			return false;
		}
		return true;
	}
	
	public void calculateCookTime() {
		int appetizerCookTime = 0;
		int mealCookTime = 0;
		for(int i = 0 ; i < this.slotList.size() ; i++) {
			if(this.slotList.get(i) == 1) {
				appetizerCookTime += this.appetizerCookTime;
				Restaurant.inProgressPreparations += 1;
			} else {
				mealCookTime += this.mainCourseCookTime;
				Restaurant.inProgressPreparations += 2;
			}
		}
		if(Restaurant.inProgressPreparations <= this.maxAffordableSlot) {
			if(appetizerCookTime > mealCookTime) {
				if(Restaurant.minCookTime > appetizerCookTime) {
					Restaurant.minCookTime = appetizerCookTime;
				}
				System.out.println("");
			} else {
				if(Restaurant.minCookTime > mealCookTime) {
					Restaurant.minCookTime = mealCookTime;
				}
				System.out.println("");
			}
		} else {
			
		}
	}
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
	}

}
