package com.revature.P0.models;

import java.util.ArrayList;

import com.revature.P0.dl.DAO;

public class Store {
	public String name;
	public int address;
	public ArrayList<Product> prods = new ArrayList<>();
	public ArrayList<Order> orders = new ArrayList<>();
	
	public int getAddress() {
		return address;
	}
	public void setAddress(int address) {
		this.address = address;
	}
	public void addProduct(Product item) {
		this.prods.add(item);
	}
	@Override
	public String toString() {
		return "Store [name=" + name + ", address=" + address +"]";
	}
	public static void sampleStore(DAO<Store> storeDAO) {
		Store store = new Store();
		store.name= "Joja Minimart";
		store.address = 111;
		for(int i=1; i<=5;i++) {
			Product prod = new Product();
			prod.setPrice(i);
			prod.setQuantity(i);
			prod.name("Store"+Integer.toString(i));
			store.addProduct(prod);
		}
		storeDAO.addInstance(store);
		Store store2 = new Store();
		store2.name="Joja Supermart";
		store2.address = 7500;
		for(int i=0; i<5;i++) {
			Product prod = new Product();
			prod.setPrice(i);
			prod.setQuantity(i);
			prod.name(Integer.toString(i));
			store2.addProduct(prod);
		}
		storeDAO.addInstance(store2);
	}
	public static void storeList(DAO<Store> storeDAO) {
		int i =1;
		for(Store store: storeDAO.getAllInstances()) {
			System.out.println("["+i+"] "+ store);
			i++;
		}
		System.out.println("[x] Exit out");
	}
	public static Store jojaMiniMenu(DAO<Store> storeDAO) {
		for(Store store: storeDAO.getAllInstances()) {
			if(store.name.equals("Joja Minimart")) {
				System.out.println("---------------------");
				System.out.println("Please select a product to add to cart or X to return");
				for(int i=0; i<store.prods.size();i++) {
					System.out.print("["+i+"] " +store.prods.get(i)+"\n");
				}
				System.out.println("[c] Submit order");
				System.out.println("[x] Cancel and exit");
				return store;
			}
		}
		return null;
	}
	public static void jojaSupermart() {
		
	}
}