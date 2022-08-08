package com.revature.P0.dl;

import java.util.ArrayList;

import com.revature.P0.models.Customer;

public class CustomerDAO implements DAO<Customer>{
	@Override
	public void addInstance(Customer newInstance) {
		TempStorage.customers.add(newInstance);
	}
	@Override
	public ArrayList<Customer> getAllInstances() {
		return TempStorage.customers;
	}
	@Override
	public Customer getByName(String Name) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void updateInstance(Customer updatedInstance) {
		// TODO Auto-generated method stub
		
	}
}