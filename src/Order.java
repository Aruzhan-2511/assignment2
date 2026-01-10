import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Product> products;

    public Order() {
        products = new ArrayList<>();
    }

    // Добавление продукта
    public void addProduct(Product p) {
        products.add(p);
    }

    // **ВАЖНО!** getter для списка продуктов
    public List<Product> getProducts() {
        return products;
    }
}
