package com.revature.P0.models;

import java.util.ArrayList;

import com.revature.P0.dl.DAO;

public class Order {
	public String storeName;
	public double totalCost;
	public String customerName;
	public int orderNumber;
	public int storeId;
	public ArrayList<Product> items = new ArrayList<>();
	
	public Order(int orderNumber, double totalCost, String customerName, String storeName, int storeId) {
		// TODO Auto-generated constructor stub
		this.orderNumber = orderNumber;
		this.totalCost = totalCost;
		this.customerName = customerName;
		this.storeName = storeName;
		this.storeId = storeId;
	}

	public Order() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "\nOrder [Order Number=" + orderNumber + ", Customer="+ customerName +", Total Cost=" + totalCost + ", Location=" + storeName + "\n\t" +items +"]";
	}
}
