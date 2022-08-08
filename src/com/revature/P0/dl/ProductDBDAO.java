package com.revature.P0.dl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.revature.P0.models.Product;
import com.revature.P0.util.ConnectionFactory;

public class ProductDBDAO implements DAO<Product>{
	@Override
	public void addInstance(Product newInstance) {
		// TODO Auto-generated method stub
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String query = "Insert into products (productId, product_name, price,quantity) values (default,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, newInstance.name);
			pstmt.setDouble(2,newInstance.getPrice());
			pstmt.setInt(3, newInstance.getQuantity());
			pstmt.execute();
			String pId = "Select productId from products where product_name =?";
			PreparedStatement pstmt_pId = conn.prepareStatement(pId);
			pstmt_pId.setString(1, newInstance.name);
			ResultSet rs = pstmt_pId.executeQuery();
			int productId = 0;
			while(rs.next()) {
				productId = rs.getInt("productId");
			}
			String query2 = "Insert into store_products (storeId,productId) values (?,?)";
			PreparedStatement pstmt2 = conn.prepareStatement(query2);
			pstmt2.setInt(1, newInstance.getStoreId());
			pstmt2.setInt(2, productId);
			pstmt2.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Product> getAllInstances() {
		// TODO Auto-generated method stub
		ArrayList<Product> products = new ArrayList<>();
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String query = "Select * from products";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				products.add(new Product(rs.getString("product_name"),rs.getDouble("price"),rs.getInt("quantity"),rs.getInt("productId")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return products;
	}

	@Override
	public Product getByName(String name) {
		// TODO Auto-generated method stub
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String query = "Select * from products where product_name = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				return new Product(rs.getString("product_name"),rs.getDouble("price"),rs.getInt("quantity"),rs.getInt("productId"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<Product> getAllByOrderId(int order_id){
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			ArrayList<Product> products = new ArrayList<Product>();
			String query = "select * from line_items li\n"
					+ "inner join products p on p.productId = li.product_id\n"
					+ "inner join orders o on o.order_id = li.order_id where o.order_id =?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, order_id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				boolean duplicate = false;
				Product product = new Product();
				product.name = rs.getString("product_name");
				product.setPrice(rs.getDouble("price"));
				product.setQuantity(rs.getInt("quantity"));
				product.setStoreId(rs.getInt("storeId"));
				product.setProductId(rs.getInt("productId"));
				for(Product prod: products) {
					if(prod.name.equals(product.name)) {
						duplicate = true;
						prod.setQuantity(prod.getQuantity()+1);
						prod.setPrice(product.getPrice()*prod.getQuantity());
					}
				}
				if(duplicate == false) {
					products.add(product);
				} else {
					continue;
				}
				
			}
			return products;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public ArrayList<Product> getAllByStoreId(int store_id){
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			ArrayList<Product> products = new ArrayList<Product>();
			String query = "select * from store_products sp \n"
					+ "inner join products p on p.productId = sp.productId\n"
					+ "inner join stores s on s.id = sp.storeId where s.id=?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, store_id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				boolean duplicate = false;
				Product product = new Product();
				product.name = rs.getString("product_name");
				product.setPrice(rs.getDouble("price"));
				product.setQuantity(rs.getInt("quantity"));
				product.setStoreId(rs.getInt("storeId"));
				product.setProductId(rs.getInt("productId"));
				for(Product prod: products) {
					if(prod.name.equals(product.name)) {
						duplicate = true;
						prod.setQuantity(prod.getQuantity()+1);
						prod.setPrice(product.getPrice()*prod.getQuantity());
					}
				}
				if(duplicate == false) {
					products.add(product);
				} else {
					continue;
				}
				
			}
			return products;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public void updateInstance(Product updatedInstance) {
		// TODO Auto-generated method stub
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String query = "update products set quantity = ?, price = ? where productId = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, updatedInstance.getQuantity());
			pstmt.setDouble(2, updatedInstance.getPrice());
			pstmt.setInt(3, updatedInstance.getProductId());
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void removeInstance(Product product) {
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String query = "delete from products where productId = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, product.getProductId());
			pstmt.execute();
			String query2 = "delete from store_products where productId = ?";
			PreparedStatement pstmt2 = conn.prepareStatement(query2);
			pstmt2.setInt(1, product.getProductId());
			pstmt2.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
