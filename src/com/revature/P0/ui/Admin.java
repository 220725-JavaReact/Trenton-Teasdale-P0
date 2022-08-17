package com.revature.P0.ui;

import java.util.ArrayList;
import java.util.Scanner;

import com.revature.P0.dl.DAO;
import com.revature.P0.dl.OrderDBDAO;
import com.revature.P0.dl.ProductDBDAO;
import com.revature.P0.models.Order;
import com.revature.P0.models.Product;
import com.revature.P0.models.Store;

public class Admin {
	static String userInput = "";
	public static void adminMenu(Scanner scanner, DAO<Store> storeDAO){
		DAO<Order> orderDAO = new OrderDBDAO();
		System.out.println("Admin password");
		String passkey = scanner.nextLine();
		if (passkey.equals("password")) {
			System.out.println("Admin Logged in-");
			do {
				System.out.println("[1] manage stores");
				System.out.println("[2] view orders");
				System.out.println("[x] exit");
				userInput = scanner.nextLine();
				switch(userInput) {
				case "1": 
					Store.storeList(storeDAO);
					int storeChoice = scanner.nextInt();
					scanner.nextLine();
					manageStore(scanner, storeDAO.getAllInstances().get(storeChoice-1));
					//edit store method
					break;
				case "2":
//					ArrayList<Order> orders = ;
					for(Order order : orderDAO.getAllInstances()) {
						System.out.println(order);
					}
					break;
				case "x":
					System.out.println("Exiting Admin Menu....");
					break;
				default: 
					System.out.println("Wrong input. try again, please choose an option.");
					break;
				}
			} while (!userInput.equals("x"));
			scanner.close();
		}
	}
	public static void manageStore(Scanner scanner, Store store) {
		DAO<Product> productDAO = new ProductDBDAO();
		DAO<Order> orderDAO = new OrderDBDAO();
		userInput = "";
		do {
			store.prods.clear();
			store.prods.addAll(((ProductDBDAO) productDAO).getAllByStoreId(store.getId()));
			System.out.println("Now managing "+store.name);
			System.out.println("[1] Add product");
			System.out.println("[2] Remove product");
			System.out.println("[3] Update inventory");
			System.out.println("[4] View Orders at this store");
			System.out.println("[x] Exit");
			
			userInput= scanner.nextLine();
			switch(userInput) {
			case "1":
				Product item = new Product();
				System.out.println("Item Name?");
				item.name = scanner.nextLine();
				System.out.println(item.name + " Price?");
				item.setPrice(scanner.nextDouble());
				scanner.nextLine();
				System.out.println(item.name + " Quantity?");
				item.setQuantity(scanner.nextInt());
				item.setStoreId(store.getId());
				scanner.nextLine();
				productDAO.addInstance(item);
				userInput = "";
				break;
			case "2":
				System.out.println("Select a product to remove");
				for(int i=0;i<store.prods.size();i++) {
					System.out.println("["+(i+1)+"] "+ store.prods.get(i).name);
				}
				System.out.println("[x] Exit");
				userInput = scanner.nextLine();
				if(userInput.equals("x")) {
					userInput="";
					break;
				}
				((ProductDBDAO) productDAO).removeInstance(store.prods.get(Integer.parseInt(userInput)-1));
				store.prods.remove(Integer.parseInt(userInput)-1);
				System.out.println("Item Removed");
				userInput = "";
				break;
			case "3":
				System.out.println("Select a product to update");
				for(int i=0;i<store.prods.size();i++) {
					System.out.println("["+(i+1)+"] "+ store.prods.get(i).toString());
				}
				System.out.println("[x] Exit");
				if(scanner.nextLine().equals("x")) {
						userInput="";
						break;
				}
				Product product = productDAO.getByName(store.prods.get(scanner.nextInt()-1).name);
				scanner.nextLine();
				System.out.println(product);
				System.out.println("Update Price? (Y/N)");
				userInput = scanner.nextLine();
				if (userInput.equals("Y")||userInput.equals("y")) {
					product.setPrice(scanner.nextDouble());
					scanner.nextLine();
				}
				System.out.println("Set Quantity");
				product.setQuantity(scanner.nextInt());
				scanner.nextLine();
				productDAO.updateInstance(product);
				System.out.println("Product Updated");
				break;
			case "4":
				ArrayList<Order> orders = ((OrderDBDAO) orderDAO).getAllByStore(store.name);
				if(orders.size() > 0) {
					for(Order order : orders) {
						System.out.println(order);
					}	
				} else {
					System.out.println("No active orders");
				}
				break;
			case "x":
				System.out.println("Exiting Admin Menu....");
				break;
			default :
				break;
			}
		}
		while (!userInput.equals("x"));
	}
}
