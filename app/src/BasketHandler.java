import java.sql.*;
import java.util.ArrayList;

public class BasketHandler {

    public static void addProductToBasket(Connection connection, Product product, Basket basket){
        // Add or Increment the product in the basket

        // Check if the product is already in the basket
        try {
            String fetchStatement = "SELECT * FROM product_basket WHERE idbasket = ? AND idproduct = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(fetchStatement);
            preparedStatement.setInt(1, basket.getIdBasket());
            preparedStatement.setInt(2, product.getIdProduct());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                // If the product is already in the basket, increment the stock
                String incrementStatement = "UPDATE product_basket SET stock = stock + 1 WHERE idbasket = ? AND idproduct = ?;";
                preparedStatement = connection.prepareStatement(incrementStatement);
                preparedStatement.setInt(1, basket.getIdBasket());
                preparedStatement.setInt(2, product.getIdProduct());
                preparedStatement.executeUpdate();
            } else {
                // If the product is not in the basket, add the product to the basket
                String addStatement = "INSERT INTO product_basket (idbasket, idproduct, stock) VALUES (?, ?, ?);";
                preparedStatement = connection.prepareStatement(addStatement);
                preparedStatement.setInt(1, basket.getIdBasket());
                preparedStatement.setInt(2, product.getIdProduct());
                preparedStatement.setInt(3, 1);
                preparedStatement.executeUpdate();
            }

            // Set the total price of the basket
            float total = basket.getTotal() + product.getPrice();
            String basketStatement = "UPDATE basket SET total_price = ? WHERE idbasket = ?;";
            preparedStatement = connection.prepareStatement(basketStatement);
            preparedStatement.setFloat(1, total);
            preparedStatement.setInt(2, basket.getIdBasket());
            preparedStatement.executeUpdate();

            // Fetch the updated basket
            Initialize.session.setClientBasket(fetchBasketFromId(connection, basket.getIdBasket()));

        } catch (SQLException e) {
            System.out.println("Something went wrong in fetching product from the basket in the database");
            System.out.println(e.getMessage());
        }

    }

    public static void removeProductFromBasket(Connection connection, Product product, Basket basket){
        // Remove or decrement product stock from the basket

        // Check if the product is already in the basket
        try {
            String fetchStatement = "SELECT * FROM product_basket WHERE idbasket = ? AND idproduct = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(fetchStatement);
            preparedStatement.setInt(1, basket.getIdBasket());
            preparedStatement.setInt(2, product.getIdProduct());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                // If the product is already in the basket, decrement the stock
                if (resultSet.getInt("stock") > 1){
                    String decrementStatement = "UPDATE product_basket SET stock = stock - 1 WHERE idbasket = ? AND idproduct = ?;";
                    preparedStatement = connection.prepareStatement(decrementStatement);
                    preparedStatement.setInt(1, basket.getIdBasket());
                    preparedStatement.setInt(2, product.getIdProduct());
                    preparedStatement.executeUpdate();
                } else {
                    String deleteStatement = "DELETE FROM product_basket WHERE idbasket = ? AND idproduct = ?;";
                    preparedStatement = connection.prepareStatement(deleteStatement);
                    preparedStatement.setInt(1, basket.getIdBasket());
                    preparedStatement.setInt(2, product.getIdProduct());
                    preparedStatement.executeUpdate();
                }

                // Set the new total
                float total = basket.getTotal() - product.getPrice();
                String basketStatement = "UPDATE basket SET total_price = ? WHERE idbasket = ?;";
                preparedStatement = connection.prepareStatement(basketStatement);
                preparedStatement.setFloat(1, total);
                preparedStatement.setInt(2, basket.getIdBasket());
                preparedStatement.executeUpdate();

                // Fetch the updated basket
                Initialize.session.setClientBasket(fetchBasketFromId(connection, basket.getIdBasket()));
            }

        } catch (SQLException e) {
            System.out.println("Something went wrong in fetching product from the basket in the database");
            System.out.println(e.getMessage());
        }
    }

    public static void checkout(Connection connection, Basket basket) throws Exception {
        // Checkout the basket

        // Check if client has enough credit
        if (Initialize.session.getClientCredit() < basket.getTotal())
            throw new Exception("Not enough credit to proceed the basket");

        // Check basket if is null
        if (basket.getTotal() == 0f)
            throw new Exception("Basket is empty");

        try {
            String basketStatement = "UPDATE basket SET is_proceeded = ? , date_proceeded = ? WHERE idbasket = ?;";
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(basketStatement);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(3, basket.getIdBasket());
            preparedStatement.executeUpdate();

            // Deduct credit from current client
            String clientStatement = "UPDATE client SET credit = credit - ? WHERE idclient = ?;";
            preparedStatement = connection.prepareStatement(clientStatement);
            preparedStatement.setFloat(1, basket.getTotal());
            preparedStatement.setInt(2, basket.getClient());
            preparedStatement.executeUpdate();

            // Update the client's credit on the runtime
            Initialize.session.setClientCredit(Initialize.session.getClientCredit() - basket.getTotal());

            // Deduct stock quantities from products in basket
            ArrayList<Product> products = BasketHandler.fetchProductsFromBasket(connection,basket, Initialize.session);
            for (Product product : products){
                product.setStock(product.getStock() - product.getStockInBasket());
                if (product.getStock() == 0)
                    product.setAvailableForClient(false);
                ProductHandler.modifyProduct(connection,product);
            }

            // Create a new basket
            basket = createBasket(connection, basket.getClient());
            Initialize.session.setClientBasket(basket);

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
                    return new Basket(resultSet.getInt("idbasket"), resultSet.getInt("client"), resultSet.getTimestamp("date_proceeded"), resultSet.getFloat("total_price"), resultSet.getBoolean("is_proceeded"));
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
                    baskets.add(new Basket(resultSet.getInt("idbasket"), resultSet.getInt("client"), resultSet.getTimestamp("date_proceeded"), resultSet.getFloat("total_price"), resultSet.getBoolean("is_proceeded")));
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
            String sqlStatement = "SELECT * FROM product_basket WHERE idbasket = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1,basket.getIdBasket());
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Product> products = new ArrayList<>();
            while (resultSet.next()){
                products.add(ProductHandler.fetchProduct(connection,resultSet.getInt("idproduct"), session));
            }
            return products;
        } catch (SQLException e){
            System.out.println("Something went wrong in fetching products from the basket in the database");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static ArrayList<Basket> fetchProceededBaskets(Connection connection) throws Exception {
        try {
            String sqlStatement = "SELECT * FROM basket WHERE is_proceeded = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setBoolean(1,true);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                throw new Exception("No proceeded baskets in the database");
            ArrayList<Basket> baskets = new ArrayList<>();
            do {
                baskets.add(new Basket(
                        resultSet.getInt("idbasket"),
                        resultSet.getInt("client"),
                        resultSet.getTimestamp("date_proceeded"),
                        resultSet.getFloat("total_price"),
                        resultSet.getBoolean("is_proceeded")
                ));
            } while (resultSet.next());

            return baskets;

        } catch (SQLException e){
            System.out.println("Something went wrong in fetching baskets from the basket table in the database");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static ArrayList<Product> retrieveProductsFromClientBasket(Connection connection, Basket basket) throws Exception {
        try {
            String sqlStatement = "SELECT product.*, product_basket.stock FROM product, product_basket WHERE product.idproduct = product_basket.idproduct AND product_basket.idbasket = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1,basket.getIdBasket());
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Product> products = new ArrayList<>();
            while (resultSet.next()){
                // Fetch client rating for each product if available
                String ratingStatement = "SELECT * FROM rating WHERE product = ? AND client = ?;";
                PreparedStatement preparedStatement1 = connection.prepareStatement(ratingStatement);
                preparedStatement1.setInt(1,resultSet.getInt("idproduct"));
                preparedStatement1.setInt(2,basket.getClient());
                ResultSet ratingResultSet = preparedStatement1.executeQuery();
                int userRating = -1;
                if (ratingResultSet.next())
                    userRating = ratingResultSet.getInt("rating");

                products.add(new Product(
                        resultSet.getInt("idproduct"),
                        resultSet.getString("title"),
                        resultSet.getFloat("price"),
                        resultSet.getString("image"),
                        resultSet.getInt("product.stock"),
                        resultSet.getInt("rating_count"),
                        resultSet.getFloat("average_rating"),
                        resultSet.getBoolean("available_for_client"),
                        userRating,
                        resultSet.getInt("product_basket.stock")

                ));
            }
            if (products.isEmpty())
                throw new Exception("Something unusual happened in retrieving products from the client basket");
            return products;

        } catch (SQLException e){
            System.out.println("Something went wrong in retrieving products from the client basket in the database");
            System.out.println(e.getMessage());
        }

        return null;
    }

}
