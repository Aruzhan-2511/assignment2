import java.sql.*;
import java.util.Arrays;

public class OrderDAO {

    public void addOrder(int[] productIds) {
        String sql = "INSERT INTO orders(product_ids) VALUES(?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {


            Array sqlArray = conn.createArrayOf("INTEGER", Arrays.stream(productIds).boxed().toArray(Integer[]::new));
            pstmt.setArray(1, sqlArray);
            pstmt.executeUpdate();

            System.out.println("Order added to DB: " + Arrays.toString(productIds));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void getAllOrders() {
        String sql = "SELECT o.id AS order_id, p.name, p.cost " +
                "FROM orders o, products p " +
                "WHERE p.id = ANY(o.product_ids) " +
                "ORDER BY o.id";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            int currentOrder = -1;
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                String pname = rs.getString("name");
                int pcost = rs.getInt("cost");

                if (orderId != currentOrder) {
                    currentOrder = orderId;
                    System.out.println("\nOrder ID: " + orderId);
                }
                System.out.println("- " + pname + ", Cost: " + pcost);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



