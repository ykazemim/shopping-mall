package SQLDefinedClasses;

import java.sql.Connection;
import java.sql.SQLException;

public class ProductHandler {

    public static void addProduct(Connection connection, String title, float price, boolean availableForClient, String pathToImage, int stock) {
        // Add product to the database
        try {
            String productStatement = "INSERT INTO product (title, price, image, stock, rating_count, average_rating, available_for_client) values (?, ?, ?, ?, ?, ?, ?);";
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(productStatement);
            preparedStatement.setString(1, title);
            preparedStatement.setFloat(2, price);
            preparedStatement.setString(3, pathToImage);
            preparedStatement.setInt(4, stock);
            preparedStatement.setInt(5, 0);
            preparedStatement.setFloat(6, 0f);
            preparedStatement.setBoolean(7, availableForClient);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Something went wrong in adding product to the database");
            System.out.println(e.getMessage());
        }

    }
}
