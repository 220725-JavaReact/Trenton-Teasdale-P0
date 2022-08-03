package com.revature.P0.models;

import java.util.ArrayList;

public class Order {
	public String storeName;
	public double totalCost;
	public String customerName;
	public int orderNumber;
	public ArrayList<Product> items = new ArrayList<>();
	
	@Override
	public String toString() {
		return "Order [Order Number=" + orderNumber + ", Total Cost=" + totalCost + ", Location=" + storeName + "]";
	}
}
