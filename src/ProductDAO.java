import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public void addProduct(Product product) {
        String sql = "INSERT INTO products(name, cost) VALUES(?, ?) RETURNING id";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, product.getName());
            pstmt.setInt(2, product.getCost());

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                product.setId(rs.getInt("id")); // сохраняем id продукта из базы
            }

            System.out.println("Product added to DB: " + product);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product p = new Product(rs.getString("name"), rs.getInt("cost"));
                p.setId(rs.getInt("id"));
                products.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public void updatePrice(String name, int newCost) {
        String sql = "UPDATE products SET cost = ? WHERE name = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, newCost);
            pstmt.setString(2, name);
            int rows = pstmt.executeUpdate();
            System.out.println("Updated " + rows + " product(s) with new cost: " + newCost);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(String name) {
        String sql = "DELETE FROM products WHERE name = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            int rows = pstmt.executeUpdate();
            System.out.println("Deleted " + rows + " product(s) with name: " + name);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
