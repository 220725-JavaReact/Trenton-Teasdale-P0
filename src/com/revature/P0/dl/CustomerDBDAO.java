package com.revature.P0.dl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.revature.P0.models.Customer;
import com.revature.P0.models.Order;
import com.revature.P0.util.ConnectionFactory;

public class CustomerDBDAO implements DAO<Customer>{
	 
	DAO<Order> orderDAO = new OrderDBDAO();

	@Override
	public void addInstance(Customer newInstance) {
		// TODO Auto-generated method stub
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String query = "Insert into customers (email, customer_name, address,customer_number) values (?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, newInstance.getEmail());
			pstmt.setString(2, newInstance.name);
			pstmt.setString(3, newInstance.getAddress());
			pstmt.setInt(4, newInstance.getNumber());
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Customer> getAllInstances() {
		// TODO Auto-generated method stub
		ArrayList<Customer> customers = new ArrayList<>();
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String query = "Select * from customers";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				customers.add(new Customer(rs.getString("customer_name"),rs.getString("address"),rs.getString("email"),rs.getInt("customer_number")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customers;
	}

	@Override
	public Customer getByName(String name) {
		// TODO Auto-generated method stub
//		ArrayList<Order> orders = new ArrayList<>();
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String query = "select * from customers where email = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			Customer customer = new Customer();
			while(rs.next()) {
				customer.name= rs.getString("customer_name");
				customer.setAddress(rs.getString("address"));
				customer.setNumber(rs.getInt("customer_number"));
				customer.setEmail(rs.getString("email"));
			}
			customer.orders.addAll(((OrderDBDAO) orderDAO).getAllByName(customer.name));
//			System.out.println(((OrderDBDAO) orderDAO).getAllByName("Trenton"));
			return customer;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateInstance(Customer updatedInstance) {
		// TODO Auto-generated method stub
		
	}

}
