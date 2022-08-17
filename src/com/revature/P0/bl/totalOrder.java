package com.revature.P0.bl;

import java.util.ArrayList;

import com.revature.P0.dl.DAO;
import com.revature.P0.models.Product;

public class totalOrder {
	public static double total(ArrayList<Product> cart,DAO<Product> productDAO) {
		double totalPrice = 0;
		for(Product item : cart) {
			totalPrice += item.getPrice();
			productDAO.updateInstance(item);
		}
		return totalPrice;
	}
}
