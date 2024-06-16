package SQLDefinedClasses;

import DataTypeClasses.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public static Product fetchProduct(Connection connection, int id, Session session) {
        // Fetch a specific product from the database
        try {
            String productStatement = "SELECT * FROM product WHERE id = ?;";
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(productStatement);
            preparedStatement.setInt(1, id);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();

            int userRating = 0;
            if (!session.isAdmin()) {
                // Fetch client rating if exists
                PreparedStatement prepareStatementRating = connection.prepareStatement("SELECT * FROM rating WHERE user = ? AND product = ?;");
                prepareStatementRating.setInt(1, session.getIduser());
                prepareStatementRating.setInt(2, resultSet.getInt("idproduct"));
                ResultSet ratingResultSet = prepareStatementRating.executeQuery();
                if (ratingResultSet.next()) {
                    userRating = ratingResultSet.getInt("rating");
                }
            }

            if (resultSet.next()) {
                return new Product(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getFloat("price"),
                        resultSet.getString("image"),
                        resultSet.getInt("stock"),
                        resultSet.getInt("rating_count"),
                        resultSet.getFloat("average_rating"),
                        resultSet.getBoolean("available_for_client"),
                        userRating);
            }
        } catch (SQLException e){
            System.out.println("Something went wrong in fetching product from the database");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static ArrayList<Product> fetchProducts(Connection connection, Session session) {
        // Fetch products from the database based on user session
        ArrayList<Product> products = new ArrayList<>();
        try {
            String productStatement;

            if (!session.isAdmin()) {
                // Fetch only available for client products if user is not admin
                productStatement = "SELECT * FROM product WHERE available_for_client = ?;";

            } else
                productStatement = "SELECT * FROM product;";

            PreparedStatement preparedStatementProduct = connection.prepareStatement(productStatement);
            if (!session.isAdmin())
                preparedStatementProduct.setBoolean(1, true);

            ResultSet productResultSet = preparedStatementProduct.executeQuery();

            int userRating = 0;
            if (!session.isAdmin()) {
                // Fetch client rating if exists
                PreparedStatement prepareStatementRating = connection.prepareStatement("SELECT * FROM rating WHERE user = ? AND product = ?;");
                prepareStatementRating.setInt(1, session.getIduser());
                prepareStatementRating.setInt(2, productResultSet.getInt("idproduct"));
                ResultSet ratingResultSet = prepareStatementRating.executeQuery();
                if (ratingResultSet.next()) {
                    userRating = ratingResultSet.getInt("rating");
                }
            }

            while (productResultSet.next()) {
                products.add(new Product(
                        productResultSet.getInt("id"),
                        productResultSet.getString("title"),
                        productResultSet.getFloat("price"),
                        productResultSet.getString("image"),
                        productResultSet.getInt("stock"),
                        productResultSet.getInt("rating_count"),
                        productResultSet.getFloat("average_rating"),
                        productResultSet.getBoolean("available_for_client"),
                        userRating));
            }

            return products;
        } catch (SQLException e){
            System.out.println("Something went wrong in fetching products from the database");
            System.out.println(e.getMessage());
        }
        return products;
    }

}
