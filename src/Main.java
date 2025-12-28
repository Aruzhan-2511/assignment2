public class Main {
    public static void main(String[] args) {

        Product p1 = new Product("Laptop", 350000);
        Product p2 = new Product("Mouse", 5000);
        Product p3 = new Product("Keyboard", 12000);
        Product p4 = new Product("USB Cable");

        Order order1 = new Order();
        order1.addProduct(p1);
        order1.addProduct(p2);
        order1.addProduct(p3);
        order1.addProduct(p4);
        Shopper shopper = new Shopper("Aruzhan");
        shopper.addOrder(order1);

        System.out.println(shopper);
        System.out.println(order1);

        Product found = order1.findProductByName("mouse");
        System.out.println("Found product: " + found);
        System.out.println("Products cheaper than 10000: ");
        for (Product p : order1.filterByCost(10000)) {
            System.out.println(p);
        }
        order1.sortByCost();
        System.out.println("Products after sorting by cost: ");
        System.out.println(order1);
    }
}
