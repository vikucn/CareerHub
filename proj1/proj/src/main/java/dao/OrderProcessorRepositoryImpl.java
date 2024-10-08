package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
//import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.DBConnection;
import entity.Customers;
import entity.Products;
//import org.hexaware.entity.Order;
//import org.hexaware.entity.OrderItems;
import exception.CustomerNotFoundException;
import exception.ProductNotFoundException;

public class OrderProcessorRepositoryImpl implements OrderProcessorRepository {

    public Connection con = DBConnection.getConnection();

    // --- Customer Management ---
    @Override
    public boolean createCustomer(Customers customer) {
        String query = "INSERT INTO customers (name, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPassword());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Customers getCustomerById(int customerId) throws CustomerNotFoundException {
        String query = "SELECT * FROM customers WHERE customer_id = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Customers(
                    rs.getInt("customer_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password")
                );
            } else {
                throw new CustomerNotFoundException("Customer with ID " + customerId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving customer.");
        }
    }

    @Override
    public boolean deleteCustomer(int customerId) throws CustomerNotFoundException {
        String query = "DELETE FROM customers WHERE customer_id = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, customerId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                throw new CustomerNotFoundException("Customer with ID " + customerId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
/*
    @Override
    public Customers getCustomerById(int customerId) throws CustomerNotFoundException {
        String query = "SELECT * FROM customers WHERE customer_id = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Customers(
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                    );
                } else {
                    throw new CustomerNotFoundException("Customer with ID " + customerId + " not found.");
                }
            }
        } catch (SQLException e) {
            // Replace printStackTrace with a logging statement
            System.err.println("SQL error while retrieving customer: " + e.getMessage());
            throw new RuntimeException("Error retrieving customer.", e);  // Add the original exception to the chain
        }
    }
    @Override
    public boolean deleteCustomer(int customerId) throws CustomerNotFoundException {
        String query = "DELETE FROM customers WHERE customer_id = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, customerId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                throw new CustomerNotFoundException("Customer with ID " + customerId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
*/
    
    
    
    
    
    
    
    // --- Product Management ---
    @Override
    public boolean createProduct(Products product) {
        String query = "INSERT INTO products (name, price, description, stock_quantity) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setString(3, product.getDescription());
            ps.setInt(4, product.getStockQuantity());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;  // Return true if the product was successfully added
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Products getProductById(int productId) throws ProductNotFoundException {
        String query = "SELECT * FROM products WHERE product_id = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Products(
                    rs.getInt("product_id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getString("description"),
                    rs.getInt("stock_quantity")
                );
            } else {
                throw new ProductNotFoundException("Product with ID " + productId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving product.");
        }
    }

    @Override
    public boolean deleteProduct(int productId) throws ProductNotFoundException {
        String query = "DELETE FROM products WHERE product_id = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, productId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                throw new ProductNotFoundException("Product with ID " + productId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // --- Cart Management ---
    @Override
    public boolean addToCart(Customers customer, Products product, int quantity) {
        String query = "INSERT INTO cart (customer_id, product_id, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, customer.getCustomerId());
            ps.setInt(2, product.getProductId());
            ps.setInt(3, quantity);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;  // Return true if the product was successfully added to the cart
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
    @Override
    public boolean addToCart(Customers customer, Products product, int quantity) throws ProductNotFoundException {
        String productQuery = "SELECT * FROM products WHERE product_id = ?";
        try (PreparedStatement ps = con.prepareStatement(productQuery)) {
            ps.setInt(1, product.getProductId());
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new ProductNotFoundException("Product with ID " + product.getProductId() + " not found.");
            }

            // If product exists, proceed to add to cart
            String addToCartQuery = "INSERT INTO cart (customer_id, product_id, quantity) VALUES (?, ?, ?)";
            try (PreparedStatement cartPs = con.prepareStatement(addToCartQuery)) {
                cartPs.setInt(1, customer.getCustomerId());
                cartPs.setInt(2, product.getProductId());
                cartPs.setInt(3, quantity);
                int rowsAffected = cartPs.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding product to cart.", e);
        }
    }
*/
    @Override
    public boolean removeFromCart(Customers customer, Products product) {
        String query = "DELETE FROM cart WHERE customer_id = ? AND product_id = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, customer.getCustomerId());
            ps.setInt(2, product.getProductId());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;  // Return true if the product was successfully removed from the cart
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Products> getAllFromCart(Customers customer) throws Exception {
        String query = "SELECT p.product_id, p.name, p.price, p.description, p.stock_quantity " +
                       "FROM products p " +
                       "JOIN cart c ON p.product_id = c.product_id " +
                       "WHERE c.customer_id = ?";
        
        List<Products> products = new ArrayList<>();  // Initialize the list
        
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, customer.getCustomerId());
            ResultSet rs = ps.executeQuery();

            // Loop through result set and add products to the list
            while (rs.next()) {
                Products product = new Products(
                    rs.getInt("product_id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getString("description"),
                    rs.getInt("stock_quantity")
                );
                products.add(product);
            }

            return products;  
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error retrieving cart items from the database.");
        }
    }


    // --- Order Management ---
    @Override
    public boolean placeOrder(Customers customer, List<Map<Products, Integer>> products, String shippingAddress) {
        String orderQuery = "INSERT INTO orders (customer_id, order_date, total_price, shipping_address) VALUES (?, ?, ?, ?)";
        String orderItemQuery = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)";
        String updateOrderPriceQuery = "UPDATE orders SET total_price = ? WHERE order_id = ?";

        double totalPrice = 0.0;

        try {
            // Insert into orders table
            PreparedStatement orderStmt = con.prepareStatement(orderQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            orderStmt.setInt(1, customer.getCustomerId());
            orderStmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));
            orderStmt.setDouble(3, totalPrice);  
            orderStmt.setString(4, shippingAddress);

            int rowsAffected = orderStmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("Creating order failed, no rows affected.");
                return false;
            }

            // Get the generated order ID
            ResultSet generatedKeys = orderStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int orderId = generatedKeys.getInt(1);

                // Insert order items and calculate the total price
                for (Map<Products, Integer> productMap : products) {
                    for (Map.Entry<Products, Integer> entry : productMap.entrySet()) {
                        Products product = entry.getKey();
                        int quantity = entry.getValue();
                        totalPrice += product.getPrice() * quantity;

                        PreparedStatement orderItemStmt = con.prepareStatement(orderItemQuery);
                        orderItemStmt.setInt(1, orderId);
                        orderItemStmt.setInt(2, product.getProductId());
                        orderItemStmt.setInt(3, quantity);
                        orderItemStmt.executeUpdate();
                    }
                }

                // Update the total price in the order
                PreparedStatement updateOrderPriceStmt = con.prepareStatement(updateOrderPriceQuery);
                updateOrderPriceStmt.setDouble(1, totalPrice);
                updateOrderPriceStmt.setInt(2, orderId);
                updateOrderPriceStmt.executeUpdate();

                return true;
            } else {
                System.out.println("Creating order failed, no order ID obtained.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
//    @Override
//    public boolean placeOrder(Customers customer, List<Map<Products, Integer>> products, String shippingAddress) {
//        String orderQuery = "INSERT INTO orders (customer_id, order_date, total_price, shipping_address) VALUES (?, ?, ?, ?)";
//        String orderItemQuery = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)";
//
//        double totalPrice = 0.0;
//
//        try {
//            con.setAutoCommit(false);  // Start transaction
//
//            // Insert into orders table
//            PreparedStatement orderStmt = con.prepareStatement(orderQuery, PreparedStatement.RETURN_GENERATED_KEYS);
//            orderStmt.setInt(1, customer.getCustomerId());
//            orderStmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));
//            orderStmt.setDouble(3, totalPrice);  // Total price will be calculated below
//            orderStmt.setString(4, shippingAddress);
//            
//            int rowsAffected = orderStmt.executeUpdate();
//            if (rowsAffected == 0) {
//                con.rollback();
//                throw new SQLException("Creating order failed, no rows affected.");
//            }
//
//            // Get the generated order ID
//            ResultSet generatedKeys = orderStmt.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                int orderId = generatedKeys.getInt(1);
//
//                // Insert order items into order_items table
//                for (Map<Products, Integer> productMap : products) {
//                    for (Map.Entry<Products, Integer> entry : productMap.entrySet()) {
//                        Products product = entry.getKey();
//                        int quantity = entry.getValue();
//                        totalPrice += product.getPrice() * quantity;
//
//                        PreparedStatement orderItemStmt = con.prepareStatement(orderItemQuery);
//                        orderItemStmt.setInt(1, orderId);
//                        orderItemStmt.setInt(2, product.getProductId());
//                        orderItemStmt.setInt(3, quantity);
//                        orderItemStmt.executeUpdate();
//                    }
//                }
//
//                // Update the total price in the order
//                PreparedStatement updateOrderPriceStmt = con.prepareStatement("UPDATE orders SET total_price = ? WHERE order_id = ?");
//                updateOrderPriceStmt.setDouble(1, totalPrice);
//                updateOrderPriceStmt.setInt(2, orderId);
//                updateOrderPriceStmt.executeUpdate();
//
//                // Commit the transaction after all updates are successful
//                con.commit();
//                return true;
//            } else {
//                con.rollback();  // Rollback if order ID generation failed
//                throw new SQLException("Creating order failed, no order ID obtained.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            try {
//                con.rollback();  // Rollback transaction on failure
//            } catch (SQLException rollbackEx) {
//                rollbackEx.printStackTrace();
//            }
//            return false;
//        } finally {
//            try {
//                con.setAutoCommit(true);  // Restore auto-commit mode
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    @Override
    public List<Map<Products, Integer>> getOrdersByCustomer(int customerId) throws Exception {
        String query = "SELECT o.order_id, oi.product_id, oi.quantity, p.name, p.price, p.description, p.stock_quantity " +
                       "FROM orders o " +
                       "JOIN order_items oi ON o.order_id = oi.order_id " +
                       "JOIN products p ON oi.product_id = p.product_id " +
                       "WHERE o.customer_id = ?";

        List<Map<Products, Integer>> customerOrders = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<Products, Integer> orderDetails = new HashMap<>();
                Products product = new Products(
                    rs.getInt("product_id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getString("description"),
                    rs.getInt("stock_quantity")
                );
                int quantity = rs.getInt("quantity");
                orderDetails.put(product, quantity);
                customerOrders.add(orderDetails);
            }

            if (customerOrders.isEmpty()) {
                throw new Exception("No orders found for customer ID " + customerId);
            }

            return customerOrders;  // Return list of orders (product and quantity)

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error retrieving customer orders from the database.");
        }
    }
}


