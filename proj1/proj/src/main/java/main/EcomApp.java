package main;

import dao.*;
import entity.*;
import exception.*;

import java.util.*;

public class EcomApp {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        OrderProcessorRepository orderProcessor = new OrderProcessorRepositoryImpl(); // Service Layer

        int choice;
        do {
            System.out.println("\nChoose an operation:");
            System.out.println("1. Register Customer");
            System.out.println("2. Create Product");
            System.out.println("3. Delete Product");
            System.out.println("4. Add to Cart");
            System.out.println("5. View Cart");
            System.out.println("6. Place Order");
            System.out.println("7. View Customer Orders");
            System.out.println("8. Exit");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    registerCustomer(orderProcessor, scanner);
                    break;
                case 2:
                    createProduct(orderProcessor, scanner);
                    break;
                case 3:
                    deleteProduct(orderProcessor, scanner);
                    break;
                case 4:
                    addToCart(orderProcessor, scanner);
                    break;
                case 5:
                    viewCart(orderProcessor, scanner);
                    break;
                case 6:
                    placeOrder(orderProcessor, scanner);
                    break;
                case 7:
                    viewCustomerOrders(orderProcessor, scanner);
                    break;
                case 8:
                    System.out.println("Thank you for using the eCommerce website! Bye!");
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        } while (choice != 8); 

        scanner.close();
    }

    // Register Customer
    private static void registerCustomer(OrderProcessorRepository orderProcessor, Scanner scanner) {
        System.out.println("Register a new customer:");
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Customers customer = new Customers(0, name, email, password); 
        if (orderProcessor.createCustomer(customer)) {
            System.out.println("Customer registered successfully.");
        } else {
            System.out.println("Error registering customer.");
        }
    }

    // Create Product
    private static void createProduct(OrderProcessorRepository orderProcessor, Scanner scanner) {
        System.out.println("Create a new product:");
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter stock quantity: ");
        int stockQuantity = scanner.nextInt();

        Products product = new Products(0, name, price, description, stockQuantity);
        if (orderProcessor.createProduct(product)) {
            System.out.println("Product created successfully.");
        } else {
            System.out.println("Error creating product.");
        }
    }

    // Delete Product
    private static void deleteProduct(OrderProcessorRepository orderProcessor, Scanner scanner) throws Exception {
        System.out.println("Delete a product:");
        System.out.print("Enter product ID to delete: ");
        int productId = scanner.nextInt();
        try {
            if (orderProcessor.deleteProduct(productId)) {
                System.out.println("Product deleted successfully.");
            } else {
                System.out.println("Product not found.");
            }
        } catch (ProductNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // Add Product to Cart
    private static void addToCart(OrderProcessorRepository orderProcessor, Scanner scanner) {
        System.out.println("Add product to cart:");
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        System.out.print("Enter product ID: ");
        int productId = scanner.nextInt();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();

        try {
            Customers customer = orderProcessor.getCustomerById(customerId); // Retrieve customer
            Products product = orderProcessor.getProductById(productId); // Retrieve product
            if (orderProcessor.addToCart(customer, product, quantity)) {
                System.out.println("Product added to cart successfully.");
            } else {
                System.out.println("Error adding product to cart.");
            }
        } catch (CustomerNotFoundException | ProductNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // View Cart
    private static void viewCart(OrderProcessorRepository orderProcessor, Scanner scanner) throws Exception {
        System.out.println("View cart:");
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();

        try {
            Customers customer = orderProcessor.getCustomerById(customerId); // Retrieve customer
            List<Products> products = null;

            try {
                products = orderProcessor.getAllFromCart(customer); // Get products from cart
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("Error fetching products from cart."); // More descriptive error
            }

            // Handle case where products list is null or empty
            if (products == null || products.isEmpty()) {
                System.out.println("Cart is empty.");
            } else {
                System.out.println("Cart contents:");
                for (Products product : products) {
                    System.out.println(product); // Assuming Products has a meaningful toString() method
                }
            }

        } catch (CustomerNotFoundException e) {
            System.out.println(e.getMessage()); // Handle case when customer is not found
        }
    }


    // Place Order
    private static void placeOrder(OrderProcessorRepository orderProcessor, Scanner scanner) throws Exception {
        System.out.println("Place an order:");
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter shipping address: ");
        String shippingAddress = scanner.nextLine();

        try {
            Customers customer = orderProcessor.getCustomerById(customerId); // Retrieve customer
            List<Products> cartItems = null;
			try {
				cartItems = orderProcessor.getAllFromCart(customer);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Retrieve cart items

            if (!cartItems.isEmpty()) {
                List<Map<Products, Integer>> productsToOrder = new ArrayList<>();
                for (Products product : cartItems) {
                    Map<Products, Integer> productMap = new HashMap<>();
                    productMap.put(product, product.getStockQuantity()); // Assuming cart stored quantity
                    productsToOrder.add(productMap);
                }
                if (orderProcessor.placeOrder(customer, productsToOrder, shippingAddress)) {
                    System.out.println("Order placed successfully.");
                } else {
                    System.out.println("Error placing order.");
                }
            } else {
                System.out.println("Cart is empty. Cannot place an order.");
            }
        } catch (CustomerNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // View Customer Orders
    private static void viewCustomerOrders(OrderProcessorRepository orderProcessor, Scanner scanner) throws Exception {
        System.out.println("View customer orders:");
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();

        List<Map<Products, Integer>> orders = null;
		try {
			orders = orderProcessor.getOrdersByCustomer(customerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!orders.isEmpty()) {
		    System.out.println("Customer's orders:");
		    for (Map<Products, Integer> order : orders) {
		        for (Map.Entry<Products, Integer> entry : order.entrySet()) {
		            System.out.println("Product: " + entry.getKey() + ", Quantity: " + entry.getValue());
		        }
		    }
		} else {
		    System.out.println("No orders found for this customer.");
		}
    }
}
