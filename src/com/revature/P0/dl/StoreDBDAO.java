package com.revature.P0.dl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.revature.P0.models.Product;
import com.revature.P0.models.Store;
import com.revature.P0.util.ConnectionFactory;

public class StoreDBDAO implements DAO<Store>{

	@Override
	public void addInstance(Store newInstance) {
		// TODO Auto-generated method stub
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String query = "Insert into stores (id, store_name, address) values (?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, newInstance.getId());
			pstmt.setString(2, newInstance.name);
			pstmt.setInt(3, newInstance.getAddress());
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Store> getAllInstances() {
		// TODO Auto-generated method stub
		ArrayList<Store> stores = new ArrayList<>();
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String query = "Select * from stores";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				stores.add(new Store(rs.getInt("Id"),rs.getString("store_name"),rs.getInt("address")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stores;
	}

	@Override
	public Store getByName(String name) {
		// TODO Auto-generated method stub
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String query = "select * from store_products sp \n"
					+ "inner join products p on p.productId = sp.productId\n"
					+ "inner join stores s on s.id = sp.storeId where s.store_name = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			Store store = new Store();
			while(rs.next()) {
					store.name= rs.getString("store_name");
					store.address = rs.getInt("address");
					store.setId(rs.getInt("Id"));
				store.addProduct(new Product(rs.getString("product_name"),rs.getDouble("price"),rs.getInt("quantity"),rs.getInt("Id"),rs.getInt("productId"))); 
			}
			return store;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateInstance(Store updatedInstance) {
		// TODO Auto-generated method stub
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String query = "update stores set name = ? set address = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, updatedInstance.name);
			pstmt.setInt(2, updatedInstance.getAddress());
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
