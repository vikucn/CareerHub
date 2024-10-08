package entity;

public class OrderItems {
	private int orderItemId;    // Corresponds to 'order_item_id' in the database
    private int orderId;        // Corresponds to 'order_id' in the database
    private int productId;      // Corresponds to 'product_id' in the database
    private int quantity;       // Corresponds to 'quantity' in the database

    // Default Constructor
    public OrderItems() {
    }

    // Parameterized Constructor
    public OrderItems(int orderItemId, int orderId, int productId, int quantity) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
 // toString() Method
    @Override
    public String toString() {
        return "OrderItem{" +
               "orderItemId=" + orderItemId +
               ", orderId=" + orderId +
               ", productId=" + productId +
               ", quantity=" + quantity +
               '}';
    }
    
}
