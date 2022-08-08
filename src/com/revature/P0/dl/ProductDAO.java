package com.revature.P0.dl;

import java.util.ArrayList;

import com.revature.P0.models.Product;

public class ProductDAO {
	public class StoreDAO implements DAO<Product>{
		@Override
		public void addInstance(Product newInstance) {
			TempStorage.products.add(newInstance);
		}
		@Override
		public ArrayList<Product> getAllInstances(){
			return TempStorage.products;
		}
		@Override
		public Product getByName(String Name) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public void updateInstance(Product updatedInstance) {
			// TODO Auto-generated method stub
			
		}
	}
}