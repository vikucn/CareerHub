package main;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import dao.OrderProcessorRepositoryImpl;
import entity.Products;


public class ProductCreationTest {
    
    @Test
    public void testCreateProduct() {
        // Arrange: Create a product object to be added to the database
        Products product = new Products(0, "Laptop", 1200.00, "High-end gaming laptop", 10); // Using double for price
        OrderProcessorRepositoryImpl repository = new OrderProcessorRepositoryImpl();
        
        // Act: Add the product to the database
        boolean isProductCreated = repository.createProduct(product);
        
        // Assert: Check that the product creation returned true, indicating success
        assertTrue(isProductCreated);
    }
}
