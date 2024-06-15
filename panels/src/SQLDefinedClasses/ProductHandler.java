package SQLDefinedClasses;

import DataTypeClasses.Product;

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

    public static void updateProduct(Connection connection, int id, String title, float price, boolean availableForClient, String pathToImage, int stock) {
        // Update product in the database
        try {
            String productStatement = "UPDATE product SET title = ?, price = ?, image = ?, stock = ?, available_for_client = ? WHERE id = ?;";
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(productStatement);
            preparedStatement.setString(1, title);
            preparedStatement.setFloat(2, price);
            preparedStatement.setString(3, pathToImage);
            preparedStatement.setInt(4, stock);
            preparedStatement.setBoolean(5, availableForClient);
            preparedStatement.setInt(6, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Something went wrong in updating product in the database");
            System.out.println(e.getMessage());
        }
    }

    public static void deleteProduct(Connection connection, int id) {
        // Delete product from the database
        try {
            String productStatement = "DELETE FROM product WHERE id = ?;";
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(productStatement);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Something went wrong in deleting product from the database");
            System.out.println(e.getMessage());
        }
    }

    public static Product fetchProduct(Connection connection, int id) {
        // Fetch product from the database
        try {
            String productStatement = "SELECT * FROM product WHERE id = ?;";
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(productStatement);
            preparedStatement.setInt(1, id);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Product(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getFloat("price"), resultSet.getString("image"), resultSet.getInt("stock"), resultSet.getInt("rating_count"), resultSet.getFloat("average_rating"), resultSet.getBoolean("available_for_client"));
            }
        } catch (SQLException e){
            System.out.println("Something went wrong in fetching product from the database");
            System.out.println(e.getMessage());
        }
        return null;
    }

}
