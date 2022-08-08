package com.revature.P0.ui;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.revature.P0.dl.CustomerDBDAO;
import com.revature.P0.dl.DAO;
import com.revature.P0.dl.OrderDBDAO;
import com.revature.P0.dl.ProductDBDAO;
import com.revature.P0.dl.StoreDBDAO;
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
	private static DAO<Customer> customerDAO = new CustomerDBDAO();
	private static DAO<Store> storeDAO = new StoreDBDAO();
	private static DAO<Product> productDAO = new ProductDBDAO();
	private static DAO<Order> orderDAO = new OrderDBDAO();
	public static void displayMenu() {
//		Customer.trenton(customerDAO);
// commented out as they are already created 
//		Store.sampleStore(storeDAO);
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
				Admin.adminMenu(scanner, storeDAO);
				userInput= "x";
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
				//open storeMenu which will choose a certain store 
				storeMenu();
				break;
			case "2": // view previous orders
				println();
				// if customers previous orders are greater than 0 display all of them 
				if(validCust.orders.size() > 0) {
					System.out.println(((OrderDBDAO) orderDAO).getAllByName(validCust.name));
					println();
				} else {
					System.out.println("No orders found");
					println();
				}
				break;
			case "x": // exit 
				System.out.println("Thank you for choosing Joja Mart...");
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
				String store = "Joja Minimart";
				System.out.println("Welcome to Joja Minimart");
				selectedStore = Store.storeMenu(storeDAO.getByName(store));
				productMenu();
				break;
			case "2":
				String store2 = "Joja Supermart";
				System.out.println("Welcome to "+ store2);
				selectedStore = Store.storeMenu(storeDAO.getByName(store2));
				productMenu();
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
				if (Integer.parseInt(userInput) >=0 && Integer.parseInt(userInput) <= selectedStore.prods.size()) {
					choice="validOption";
				}
			};
			if (userInput.equals("x")) {
				choice = "x";
			} else if (userInput.equals("c")) {
				choice = "c";
				userInput="x";
			} 
			switch(choice) {
			case "x": 
				System.out.println("Thank you for choosing Joja Mart...");
				userInput="x";
				choice="";
				cart.clear();
				break;
			case "c":
				submitOrder();
				println();
				//call submit order function - verify cart is larger than 0
				customerMenu();
				choice="";
				break;
			case "validOption":
				System.out.println("How many?");
				int count = scanner.nextInt();
				scanner.nextLine();
				if(count > selectedStore.prods.get(Integer.parseInt(userInput)).getQuantity()) {
					System.out.println("Not enough stock at this location - remaining quantity " +selectedStore.prods.get(Integer.parseInt(userInput)).getQuantity());
					Store.storeMenu(selectedStore);
				} else {
					for(int i=0; i<count;i++) {
						if(Integer.parseInt(userInput) <= selectedStore.prods.size()) {
							Product product = selectedStore.prods.get(Integer.parseInt(userInput));
							int remaining = selectedStore.prods.get(Integer.parseInt(userInput)).getQuantity();
							product.setQuantity(remaining - 1);
							selectedStore.prods.set(Integer.parseInt(userInput), product);
							//update store in storage
//							((StoreDAO) storeDAO).updateStore(selectedStore);
							addToCart(userInput, selectedStore);
						}
					}
					System.out.println("Items in cart \n"+cart);
					System.out.println("Added to cart");
					Store.storeMenu(selectedStore);
				}
				choice="";
				break;
				default:
					Logger.getLogger().log(LogLevel.warning,"Invalid input");
					System.out.println("Wrong input. try again, please choose an option.");
					userInput="";
					choice="";
					Store.storeMenu(selectedStore);
					break;
			}
		} while(!userInput.equals("x"));
		scanner.close();
	}
	public static void addToCart(String userInput, Store selectedStore) {
		cart.add(selectedStore.prods.get(Integer.parseInt(userInput)));
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
				productDAO.updateInstance(item);
			}
			order.totalCost = totalPrice;
			Random random = new Random();
			String id = String.format("%04d", random.nextInt(10000));
			order.orderNumber = Integer.parseInt(id);
			order.storeId = selectedStore.getId();
			validCust.orders.add(order);
			System.out.println(order);
			orderDAO.addInstance(order);
			cart.clear();
		} else {
			System.out.println("Your cart is empty");
		}
	}
}
