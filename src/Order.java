import java.util.*;

public class Order {
    private List<Product> products;

    public Order() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public int getTotalCost() {
        int total = 0;
        for (Product p : products) {
            total += p.getCost();
        }
        return total;
    }

    public Product findProductByName(String name) {
        for (Product p : products) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    public List<Product> filterByCost(int maxCost) {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getCost() <= maxCost) {
                result.add(p);
            }
        }
        return result;
    }

    public void sortByCost() {
        products.sort(Comparator.comparingInt(Product::getCost));
    }

    @Override
    public String toString() {
        return "Order products: " + products + ", Total cost: " + getTotalCost();
    }
}
