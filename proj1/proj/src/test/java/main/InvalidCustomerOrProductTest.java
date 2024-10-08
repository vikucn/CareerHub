package main;
//import static org.junit.Assert.assertThrows;
import dao.OrderProcessorRepositoryImpl;
//import entity.Customers;
//import entity.Products;
import exception.CustomerNotFoundException;
import exception.ProductNotFoundException;
//import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

//import dao.OrderProcessorRepositoryImpl;
//import exception.CustomerNotFoundException;
//import exception.ProductNotFoundException;
//import org.junit.jupiter.api.Test;

public class InvalidCustomerOrProductTest {

    @Test
    public void testCustomerNotFoundException() {
        // Arrange: Use an invalid customer ID
        OrderProcessorRepositoryImpl repository = new OrderProcessorRepositoryImpl();
        int invalidCustomerId = 9999; // Assume this ID does not exist in the database
        
        // Act & Assert: Ensure CustomerNotFoundException is thrown
        assertThrows(CustomerNotFoundException.class, () -> {
            repository.getCustomerById(invalidCustomerId);
        });
    }

    @Test
    public void testProductNotFoundException() {
        // Arrange: Use an invalid product ID
        OrderProcessorRepositoryImpl repository = new OrderProcessorRepositoryImpl();
        int invalidProductId = 9999; // Assume this ID does not exist in the database
        
        // Act & Assert: Ensure ProductNotFoundException is thrown
        assertThrows(ProductNotFoundException.class, () -> {
            repository.getProductById(invalidProductId);
        });
    }
}
