package POJO_EcomApp;

import java.util.List;

public class OrderProduct_Request {
    private List<OrdersProductDetails_Request> orders;

    public List<OrdersProductDetails_Request> getOrders() {
        return orders;
    }

    public void setOrders(List<OrdersProductDetails_Request> orders) {
        this.orders = orders;
    }
}
