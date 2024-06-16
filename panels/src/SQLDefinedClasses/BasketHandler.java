package SQLDefinedClasses;

import DataTypeClasses.Basket;
import DataTypeClasses.Product;

import java.sql.*;
import java.util.ArrayList;

public class BasketHandler {

    public static void addProductToBasket(Connection connection, Product product, Basket basket){
        // Add product to the basket
        try {
            String basketProductStatement = "INSERT INTO basket_product (basket, product) VALUES (?, ?);";
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(basketProductStatement);
            preparedStatement.setInt(1, basket.getIdBasket());
            preparedStatement.setInt(2, product.getIdProduct());
            preparedStatement.executeUpdate();

            // Set the total of the basket
            float total = basket.getTotal() + product.getPrice();
            String basketStatement = "UPDATE basket SET total_price = ? WHERE idbasket = ?;";
            preparedStatement = connection.prepareStatement(basketStatement);
            preparedStatement.setFloat(1, total);
            preparedStatement.setInt(2, basket.getIdBasket());
            preparedStatement.executeUpdate();

            // Fetch the updated basket
            basket = fetchBasketFromId(connection, basket.getIdBasket());

        } catch (java.sql.SQLException e){
            System.out.println("Something went wrong in adding product to the basket in the database");
            System.out.println(e.getMessage());
        }

    }

    public static void removeProductFromBasket(Connection connection, Product product, Basket basket){
        // Remove product from the basket
        try {
            String basketProductStatement = "DELETE FROM basket_product WHERE idbasket = ? AND idproduct = ?;";
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(basketProductStatement);
            preparedStatement.setInt(1, basket.getIdBasket());
            preparedStatement.setInt(2, product.getIdProduct());
            preparedStatement.executeUpdate();

            // Set the new total
            float total = basket.getTotal() - product.getPrice();
            String basketStatement = "UPDATE basket SET total_price = ? WHERE idbasket = ?;";
            preparedStatement = connection.prepareStatement(basketStatement);
            preparedStatement.setFloat(1, total);
            preparedStatement.setInt(2, basket.getIdBasket());
            preparedStatement.executeUpdate();

            // Fetch the updated basket
            basket = fetchBasketFromId(connection, basket.getIdBasket());

        } catch (java.sql.SQLException e){
            System.out.println("Something went wrong in removing product from the basket in the database");
            System.out.println(e.getMessage());
        }
    }

    public static void checkout(Connection connection, Basket basket) throws Exception {
        // Checkout the basket
        try {
            String basketStatement = "UPDATE basket SET is_proceeded = ? date_proceeded = ? WHERE idbasket = ?;";
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(basketStatement);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setDate(2, java.sql.Date.valueOf(java.time.LocalDate.now()));
            preparedStatement.setInt(3, basket.getIdBasket());
            preparedStatement.executeUpdate();

            // Create a new basket
            basket = createBasket(connection, basket.getClient());

        } catch (java.sql.SQLException e){
            System.out.println("Something went wrong in checking out the basket in the database");
            System.out.println(e.getMessage());
        }
    }

    public static Basket fetchBasketFromId(Connection connection, int idBasket) {
        // Fetch the basket
        try{
            String basketStatement = "SELECT * FROM basket WHERE idbasket = ?;";
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(basketStatement);
            preparedStatement.setInt(1, idBasket);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                if (resultSet.getDate("date_proceeded") == null)
                    return new Basket(resultSet.getInt("idbasket"), resultSet.getInt("client"), null, resultSet.getFloat("total_price"), resultSet.getBoolean("is_proceeded"));
                else
                    return new Basket(resultSet.getInt("idbasket"), resultSet.getInt("client"), resultSet.getDate("date_proceeded").toLocalDate(), resultSet.getFloat("total_price"), resultSet.getBoolean("is_proceeded"));
            }
        } catch (java.sql.SQLException e){
            System.out.println("Something went wrong in fetching basket from the database");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static ArrayList<Basket> fetchBasketFromClient(Connection connection, int client, boolean isProceeded) {
        // Fetch the basket with the client ID
        ArrayList<Basket> baskets = new ArrayList<>();
        try{
            String basketStatement = "SELECT * FROM basket WHERE client = ? AND is_proceeded = ?;";
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(basketStatement);
            preparedStatement.setInt(1, client);
            preparedStatement.setBoolean(2, isProceeded);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                if (resultSet.getDate("date_proceeded") == null)
                    baskets.add( new Basket(resultSet.getInt("idbasket"), resultSet.getInt("client"), null, resultSet.getFloat("total_price"), resultSet.getBoolean("is_proceeded")));
                else
                    baskets.add(new Basket(resultSet.getInt("idbasket"), resultSet.getInt("client"), resultSet.getDate("date_proceeded").toLocalDate(), resultSet.getFloat("total_price"), resultSet.getBoolean("is_proceeded")));
            }
            return baskets;
        } catch (java.sql.SQLException e){
            System.out.println("Something went wrong in fetching basket from the database");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static Basket createBasket(Connection connection, int client) throws Exception{
        // Create a basket
        try{
            String basketStatement = "INSERT INTO basket (client, date_proceeded, total_price, is_proceeded) VALUES (?, ?, ?, ?);";
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(basketStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, client);
            preparedStatement.setDate(2, null);
            preparedStatement.setFloat(3, 0f);
            preparedStatement.setBoolean(4, false);
            preparedStatement.executeUpdate();

            // Retrieve the last inserted basket ID
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int idBasket = -1;
            if(generatedKeys.next()){
                idBasket = generatedKeys.getInt(1);
            }

            if (idBasket == -1)
                throw new Exception("Something went wrong in getting basket id");

            return fetchBasketFromId(connection, idBasket);

        } catch (java.sql.SQLException e){
            System.out.println("Something went wrong in creating basket in the database");
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public static ArrayList<Product> fetchProductsFromBasket(Connection connection,Basket basket, Session session){
        try{
            String sqlStatement = "SELECT * FROM basket_product WHERE idbasket = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1,basket.getIdBasket());
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Product> products = new ArrayList<>();
            while (resultSet.next()){
                products.add(ProductHandler.fetchProduct(connection,resultSet.getInt("product"), session));
            }
            return products;
        } catch (SQLException e){
            System.out.println("Something went wrong in fetching products from the basket in the database");
            System.out.println(e.getMessage());
        }
        return null;
    }

}
