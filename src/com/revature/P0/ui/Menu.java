package com.revature.P0.ui;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.revature.P0.dl.CustomerDAO;
import com.revature.P0.dl.DAO;
import com.revature.P0.dl.StoreDAO;
import com.revature.P0.models.Customer;
import com.revature.P0.models.Order;
import com.revature.P0.models.Product;
import com.revature.P0.models.Store;
//import com.revature.P0.models.newCustomer;
import com.revature.P0.util.Logger;
import com.revature.P0.util.Logger.LogLevel;

public class Menu {
//	private static Logger logger = new Logger();
	static Customer validCust;
	static Store selectedStore;
	static String userInput = "";
	static ArrayList<Product> cart = new ArrayList<>();
	static Scanner scanner = new Scanner(System.in);
	private static DAO<Customer> customerDAO = new CustomerDAO();
	private static DAO<Store> storeDAO = new StoreDAO();
//	private static DAO<Product> productDAO = new ProductDAO();
	public static void displayMenu() {
		Customer.trenton(customerDAO);
		System.out.println("Welcome to Joja Mart Co!");
		do {
			System.out.println("[1] Sign in as admin");
			System.out.println("[2] Sign in as customer");
			System.out.println("[3] New customer");
			System.out.println("[x] Exit out");
			userInput = scanner.nextLine();
			switch (userInput) {
			case "1": // Sign in as admin
				//sign in as admin will call a function that allows you to view/edit all stores,customers,orders
				//call a function like signInAsAdmin(creds)
				//generate a new menu with options
				break;
			case "2": // sign in as customer
				//sign in as a customer display a new UI with stores and past orders as options
				//call a custom like signInAsCustomer(creds) that will search and find customer and disp customer info
				//generates new menu with options
				validCust = Customer.customerSignIn(scanner, customerDAO);
				//if customer sign in succeeds then it will assign it to validCust if not it will return null
				if(validCust != null) {
					userInput = "x";
					println();
					System.out.println("Welcome "+validCust.name);
					//start the customers menu method
					customerMenu();
				}
				break;
			case "3": // new customer
				//call customer.create to add a new customer into the DAO and temp storage 
				Customer.create(scanner, customerDAO);
				println();
				break;
			case "x": // exit 
				System.out.println("Thank you for choosing Joja Mart...");
				scanner.close();
				break;
			default: //invalid input 
				Logger.getLogger().log(LogLevel.warning,"Invalid input");
				System.out.println("Wrong input. try again, please choose an option.");
				break;
			}
		} while(!userInput.equals("x"));
		scanner.close();
	}
	public static void customerMenu() {
		//create stores and add them into the storeDAO / temp storage
		Store.sampleStore(storeDAO);
		System.out.println("Customer Menu");
		do {
			System.out.println("[1] View list of stores");
			System.out.println("[2] View previous orders");
			System.out.println("[x] Exit out");
			userInput = scanner.nextLine();
			switch(userInput) {
			case "1": // list of stores
				println();
				System.out.println("Please Choose a location");
				//iterate through the stores in the tempStorage 
				Store.storeList(storeDAO);
				//open storeMenu wich will choose a certain store 
				storeMenu();
				break;
			case "2": // view previous orders
				println();
				// if customers previous orders are greater than 0 display all of them 
				if(validCust.orders.size() > 0) {
					System.out.println(validCust.orders);
					println();
				} else {
					System.out.println("No orders found");
					println();
				}
				break;
			case "x": // exit 
				System.out.println("Thank you for choosing Joja Mart...");
				scanner.close();
				break;
			default:
				Logger.getLogger().log(LogLevel.warning,"Invalid input");
				System.out.println("Wrong input. try again, please choose an option.");
				break;
			}
		}while(!userInput.equals("x"));
		scanner.close();
	}
	public static void println(){
		System.out.println("---------------------");
	}
	public static void storeMenu() {
		do {
			userInput = scanner.nextLine();
;			switch(userInput) {
			case "1": 
				System.out.println("Welcome to JojaMini");
				selectedStore = Store.jojaMiniMenu(storeDAO);
				productMenu();
				break;
			case "2":
				Store.jojaSupermart();
			break;
			case "x":
				System.out.println("Thank you for choosing Joja Mart...");
				break;
			default:
				Logger.getLogger().log(LogLevel.warning,"Invalid input");
				System.out.println("Wrong input. try again, please choose an option.");
			break;
			}
		} while(!userInput.equals("x"));
		scanner.close();
	}
	public static void productMenu(){
		String choice ="";
		String regex = "[+-]?[0-9]+";
		Pattern p = Pattern.compile(regex);
		do {
			userInput = scanner.nextLine();
			Matcher m = p.matcher(userInput);
			if (m.find() &&m.group().equals(userInput) ) {
				System.out.println("valid Input");
				if (Integer.parseInt(userInput) >=0 && Integer.parseInt(userInput) <= selectedStore.prods.size()) {
					choice="validOption";
				}
			};
			if (userInput.equals("x")) {
				choice = "x";
			} else if (userInput.equals("c")) {
				choice = "c";
			} 
			switch(choice) {
			case "x": 
				System.out.println("Thank you for choosing Joja Mart...");
				userInput="x";
				choice="";
				cart.clear();
				scanner.close();
				break;
			case "c":
				submitOrder();
				//call submit order function - verify cart is larger than 0
				customerMenu();
				userInput="";
				choice="";
				break;
			case "validOption":
				System.out.println("How many?");
				int count = scanner.nextInt();
				scanner.nextLine();
				if(count > selectedStore.prods.get(Integer.parseInt(userInput)).getQuantity()) {
					System.out.println("Not enough stock at this location - remaining quantity " +selectedStore.prods.get(Integer.parseInt(userInput)).getQuantity());
					Store.jojaMiniMenu(storeDAO);
				} else {
					for(int i=0; i<count;i++) {
						if(Integer.parseInt(userInput) <= selectedStore.prods.size()) {
							Product product = selectedStore.prods.get(Integer.parseInt(userInput));
							int remaining = selectedStore.prods.get(Integer.parseInt(userInput)).getQuantity();
							product.setQuantity(remaining - 1);
							selectedStore.prods.set(Integer.parseInt(userInput), product);
							//update store in storage
							((StoreDAO) storeDAO).updateStore(selectedStore);
							addToCart(userInput, selectedStore);
						}
					}
				}
				userInput="";
				choice="";
				break;
				default:
					Logger.getLogger().log(LogLevel.warning,"Invalid input");
					System.out.println("Wrong input. try again, please choose an option.");
					userInput="";
					choice="";
					Store.jojaMiniMenu(storeDAO);
					break;
			}
		} while(!userInput.equals("x"));
		scanner.close();
	}
	public static void addToCart(String userInput, Store selectedStore) {
		cart.add(selectedStore.prods.get(Integer.parseInt(userInput)));
		System.out.println("Items in cart "+cart);
		System.out.println("Added to cart");
		Store.jojaMiniMenu(storeDAO);
	}
	public static void submitOrder() {
		if(cart.size() > 0) {
			System.out.println("Submitting order...........");
			Order order = new Order();
			order.customerName = validCust.name;
			order.items = cart;
			order.storeName = selectedStore.name;
			double totalPrice = 0;
			for(Product item : cart) {
				totalPrice += item.getPrice();
			}
			order.totalCost = totalPrice;
			Random random = new Random();
			String id = String.format("%04d", random.nextInt(10000));
			order.orderNumber = Integer.parseInt(id);
			validCust.orders.add(order);
			System.out.println(validCust.orders);
		} else {
			System.out.println("Your cart is empty");
		}
	}
}
