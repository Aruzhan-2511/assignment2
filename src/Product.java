import java.util.Objects;

public class Product {
    private String name;
    private int cost;

    private int id;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }


    public Product(String name) {
        this.name = name;
        this.cost = 500;
    }

    public Product(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Product: " + name + ", Cost: " + cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return cost == product.cost && name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cost);
    }
}
