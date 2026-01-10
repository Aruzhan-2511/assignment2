public class Main {
    public static void main(String[] args) {
        Product laptop = new Product("Laptop", 350000);
        Product mouse = new Product("Mouse", 5000);

        Order order = new Order();
        order.addProduct(laptop);
        order.addProduct(mouse);

        ProductDAO productDAO = new ProductDAO();
        OrderDAO orderDAO = new OrderDAO();

        productDAO.addProduct(laptop);
        productDAO.addProduct(mouse);

        int[] productIds = order.getProducts().stream().mapToInt(Product::getId).toArray();
        orderDAO.addOrder(productIds);

        System.out.println("\nAll products in DB:");
        for (Product p : productDAO.getAllProducts()) {
            System.out.println(p);
        }

        System.out.println("\nAll orders in DB:");
        orderDAO.getAllOrders();

        productDAO.updatePrice("Laptop", 360000);
        productDAO.deleteProduct("Mouse");
    }
}
