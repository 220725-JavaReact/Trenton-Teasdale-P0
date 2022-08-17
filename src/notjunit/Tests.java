package notjunit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.mockito.Mockito;

import com.revature.P0.dl.DAO;
import com.revature.P0.dl.OrderDBDAO;
import com.revature.P0.models.Customer;
import com.revature.P0.models.Order;
import com.revature.P0.models.Product;

import exceptions.InvalidEmailException;
import exceptions.InvalidNumberException;

public class Tests {
	
	@Test
	public void set_Product_Price() {
		Product product = new Product();
		product.setPrice(2.50);
		Assert.equals(2.50, product.getPrice());
	}
	@Test
	public void set_Product_Price_Fail() {
		Product product = new Product();
		Assert.willThrow(InvalidNumberException.class, () -> product.setPrice(-1));
	}
	@Test void set_product_name() {
		Product prod = new Product();
		prod.name = "Rocks";
		Assert.equals(prod.name, "Rocks");
	}
	@Test 
	public void set_quantity() {
		Product product = new Product();
		product.setQuantity(2);
		Assert.equals(2, product.getQuantity());
	}
	@Test
	public void set_Quantity_Fail() {
		Product product = new Product();
		Assert.willThrow(InvalidNumberException.class, () -> product.setPrice(-1));
	}
	@Test
	public void customer_name() {
		Customer cust = new Customer();
		cust.name = "Rocks";
		Assert.equals(cust.name, "Rocks");
	}
	@Test
	public void new_Customer_valid_Email() {
		Customer cust = new Customer();
		cust.setEmail("Trenton@gmail.com");
		Assert.equals(cust.getEmail(), "Trenton@gmail.com");
	}
	@Test
	public void new_Customer_invalid_email() {
		Customer cust = new Customer();
		Assert.willThrow(InvalidEmailException.class, () -> cust.setEmail("trenton"));
	}
	private DAO<Order> orderDAO;
	@Before
	public void setup() {
		orderDAO = Mockito.mock(OrderDBDAO.class);
	}
	@Test
	public void calculate_Order_Total() {
		Order order = new Order();
		order.customerName = "Trenton";
		ArrayList<Product> prods = new ArrayList<>();
		Product prod1 = new Product();
		prod1.setPrice(5.5);
		Product prod2 = new Product();
		prod2.setPrice(5.5);
		prods.add(prod1);
		prods.add(prod2);
		order.items = prods;
		
		when(orderDAO.getByName(anyString())).thenReturn(order);
		Order resultOrder = orderDAO.getByName("Trenton");
		assertEquals(resultOrder.totalCost, 11);
	}
}
