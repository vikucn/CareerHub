package dao;

import entity.Customers;
import entity.Products;

import exception.*;

import java.util.List;
import java.util.Map;


public interface OrderProcessorRepository {
	// Customer Management
    boolean createCustomer(Customers customer);
    Customers getCustomerById(int customerId) throws CustomerNotFoundException;
    boolean deleteCustomer(int customerId) throws CustomerNotFoundException;

    // Product Management
    boolean createProduct(Products product);
    Products getProductById(int productId) throws ProductNotFoundException;    
    boolean deleteProduct(int productId) throws  ProductNotFoundException;

    // Cart Management
    boolean addToCart(Customers customer, Products product, int quantity);
    boolean removeFromCart(Customers customer, Products product);
    List<Products> getAllFromCart(Customers customer) throws Exception; 

    // Order Management
    boolean placeOrder(Customers customer, List<Map<Products, Integer>> products, String shippingAddress);
    List<Map<Products, Integer>> getOrdersByCustomer(int customerId) throws Exception;  
}

