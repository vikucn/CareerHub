package main;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import dao.OrderProcessorRepositoryImpl;
import entity.Customers;
import entity.Products;

//import java.math.BigDecimal;

public class AddToCartTest {
    
    @Test
    public void testAddProductToCart() {
        // Arrange: Create a customer and a product to be added to the cart
        Customers customer = new Customers(1, "John Doe", "john@example.com", "password123");
        Products product = new Products(2, "Smartphone", 800.00, "Flagship smartphone", 20);
        
        OrderProcessorRepositoryImpl repository = new OrderProcessorRepositoryImpl();
        
        // Act: Add the product to the cart
        boolean isProductAddedToCart = repository.addToCart(customer, product, 2);
        
        // Assert: Check that adding the product to the cart was successful
        assertTrue(isProductAddedToCart);
    }
}
