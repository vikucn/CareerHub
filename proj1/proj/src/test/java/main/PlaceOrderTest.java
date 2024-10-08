package main;
//import static org.junit.Assert.assertTrue;
import dao.OrderProcessorRepositoryImpl;
import entity.Customers;
import entity.Products;
//import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

//import java.math.BigDecimal;
import java.util.*;

public class PlaceOrderTest {
    
    @Test
    public void testPlaceOrder() {
        // Arrange: Create a customer and a list of products with their quantities for the order
        Customers customer = new Customers(1, "John Doe", "john@example.com", "password123");
        Products product1 = new Products(1, "Laptop", 1200.00, "High-end gaming laptop", 10);
        Products product2 = new Products(2, "Smartphone", 800.00, "Flagship smartphone", 5);

        List<Map<Products, Integer>> productsInOrder = new ArrayList<>();
        Map<Products, Integer> productMap1 = new HashMap<>();
        productMap1.put(product1, 1);
        Map<Products, Integer> productMap2 = new HashMap<>();
        productMap2.put(product2, 2);
        productsInOrder.add(productMap1);
        productsInOrder.add(productMap2);

        OrderProcessorRepositoryImpl repository = new OrderProcessorRepositoryImpl();
        
        // Act: Place the order with the customer's selected products
        boolean isOrderPlaced = repository.placeOrder(customer, productsInOrder, "123 Main St, Springfield");
        
        // Assert: Check if the order was placed successfully
        assertTrue(isOrderPlaced);
    }
}
