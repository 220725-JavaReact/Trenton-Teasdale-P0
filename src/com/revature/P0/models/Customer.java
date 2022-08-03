package com.revature.P0.models;

import java.util.ArrayList;
import java.util.Scanner;

import com.revature.P0.dl.DAO;

public class Customer {
	public String name;
	private String address;
	private String email;
	private int number;
	public ArrayList<Order> orders = new ArrayList<>();
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public void addStr(Order str) {
		this.orders.add(str);
	}
	@Override
	public String toString() {
		return "customer [name=" + name + ", address=" + address + ", email=" + email + ", number=" + number + ", orders=" +orders+"]";
	}
	public static Customer customerSignIn(Scanner scanner,DAO<Customer> customerDAO) {
		System.out.println("What is your email");
		String email = scanner.nextLine();
		for(Customer cust: customerDAO.getAllInstances()) {
			if(email.equals(cust.email)) {
				return cust;
			}
		}
		System.out.println("No match found");
		return null;
}
	public static Customer create(Scanner scanner,DAO<Customer> customerDAO) {
		Customer customer = new Customer();
		System.out.println("What is your first name?");
		customer.name = scanner.nextLine();
		System.out.println("What is your Address?");
		customer.setAddress(scanner.nextLine());
		System.out.println("What is your email?");
		customer.setEmail(scanner.nextLine());
		System.out.println("What is your phone number? (no dashes)");
		try {
			customer.setNumber(scanner.nextInt());
			scanner.nextLine();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		customerDAO.addInstance(customer);
		System.out.println(customer);
		System.out.println("Customer Added");
		return customer;
	}
	public static void trenton(DAO<Customer> customerDAO) {
		Customer tre = new Customer();
		tre.name = "Trenton";
		tre.address = "123 Street";
		tre.email = "email";
		tre.number = 801;
		customerDAO.addInstance(tre);
	}
}
