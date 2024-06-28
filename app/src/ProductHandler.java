import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ProductHandler {
    public static final int SORT_BY_PRICE = 2;
    public static final int SORT_BY_RATING = 1;
    public static final int DEFAULT_ORDER = 0;
    public static final int ASCENDING_ORDER = 1;
    public static final int DESCENDING_ORDER = 0;

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
            String productStatement = "UPDATE product SET title = ?, price = ?, image = ?, stock = ?, available_for_client = ? WHERE idproduct = ?;";
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
            String productStatement = "DELETE FROM product WHERE idproduct = ?;";
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
            String productStatement = "SELECT * FROM product WHERE idproduct = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(productStatement);
            preparedStatement.setInt(1, id);
            ResultSet productResultSet = preparedStatement.executeQuery();
            productResultSet.next();

            int userRating = 0;
            if (!session.isAdmin()) {
                // Fetch client rating if exists
                PreparedStatement prepareStatementRating = connection.prepareStatement("SELECT * FROM rating WHERE client = ? AND product = ?;");
                prepareStatementRating.setInt(1, session.getIdclient());
                prepareStatementRating.setInt(2, productResultSet.getInt("idproduct"));
                ResultSet ratingResultSet = prepareStatementRating.executeQuery();
                if (ratingResultSet.next()) {
                    userRating = ratingResultSet.getInt("rating");
                }
            }

            // Fetch the stock of the product in the user basket if exists
            int userStock = 0;
            if (!session.isAdmin()) {
                PreparedStatement prepareStatementBasket = connection.prepareStatement("SELECT * FROM product_basket WHERE idproduct = ? AND idbasket = ?;");
                prepareStatementBasket.setInt(1, productResultSet.getInt("idproduct"));
                prepareStatementBasket.setInt(2, session.getClientBasket().getIdBasket());
                ResultSet basketResultSet = prepareStatementBasket.executeQuery();
                if (basketResultSet.next()) {
                    userStock = basketResultSet.getInt("stock");
                }
            }

            return new Product(productResultSet.getInt("idproduct"),
                    productResultSet.getString("title"),
                    productResultSet.getFloat("price"),
                    productResultSet.getString("image"),
                    productResultSet.getInt("stock"),
                    productResultSet.getInt("rating_count"),
                    productResultSet.getFloat("average_rating"),
                    productResultSet.getBoolean("available_for_client"),
                    userRating,
                    userStock);

        } catch (SQLException e){
            System.out.println("Something went wrong in fetching product from the database");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static ArrayList<Product> fetchProducts(Connection connection, Session session, int sortType, int orderType) {
        // Fetch products from the database based on user session
        ArrayList<Product> products = new ArrayList<>();

        // Creating the product statement based on the sort type and order type

        try {
            String productStatement;

            switch (sortType) {
                case SORT_BY_RATING:
                    if (session.isAdmin())
                        if (orderType == ASCENDING_ORDER)
                            productStatement = "SELECT * FROM product ORDER BY average_rating ASC;";
                        else
                            productStatement = "SELECT * FROM product ORDER BY average_rating DESC;";
                    else
                        if (orderType == ASCENDING_ORDER)
                            productStatement = "SELECT * FROM product WHERE available_for_client = ? ORDER BY average_rating ASC;";
                        else
                            productStatement = "SELECT * FROM product WHERE available_for_client = ? ORDER BY average_rating DESC;";
                    break;
                case SORT_BY_PRICE:
                    if (session.isAdmin())
                        if (orderType == ASCENDING_ORDER)
                            productStatement = "SELECT * FROM product ORDER BY price ASC;";
                        else
                            productStatement = "SELECT * FROM product ORDER BY price DESC;";

                    else
                        if (orderType == ASCENDING_ORDER)
                            productStatement = "SELECT * FROM product WHERE available_for_client = ? ORDER BY price ASC;";
                        else
                            productStatement = "SELECT * FROM product WHERE available_for_client = ? ORDER BY price DESC;";
                    break;

                default:
                    if (session.isAdmin())
                        productStatement = "SELECT * FROM product ;";
                    else
                        productStatement = "SELECT * FROM product WHERE available_for_client = ?;";
                    break;
            }

            PreparedStatement preparedStatementProduct = connection.prepareStatement(productStatement);
            if (!session.isAdmin())
                preparedStatementProduct.setBoolean(1, true);

            ResultSet productResultSet = preparedStatementProduct.executeQuery();


            while (productResultSet.next()) {

                int userRating = 0;
                if (!session.isAdmin()) {
                    // Fetch client rating if exists
                    PreparedStatement prepareStatementRating = connection.prepareStatement("SELECT * FROM rating WHERE client = ? AND product = ?;");
                    prepareStatementRating.setInt(1, session.getIdclient());
                    prepareStatementRating.setInt(2, productResultSet.getInt("idproduct"));
                    ResultSet ratingResultSet = prepareStatementRating.executeQuery();
                    if (ratingResultSet.next()) {
                        userRating = ratingResultSet.getInt("rating");
                    }
                }

                // Fetch the stock of the product in the user basket if exists
                int userStock = 0;
                if (!session.isAdmin()) {
                    PreparedStatement prepareStatementBasket = connection.prepareStatement("SELECT * FROM product_basket WHERE idproduct = ? AND idbasket = ?;");
                    prepareStatementBasket.setInt(1, productResultSet.getInt("idproduct"));
                    prepareStatementBasket.setInt(2, session.getClientBasket().getIdBasket());
                    ResultSet basketResultSet = prepareStatementBasket.executeQuery();
                    if (basketResultSet.next()) {
                        userStock = basketResultSet.getInt("stock");
                    }
                }

                products.add(new Product(
                        productResultSet.getInt("idproduct"),
                        productResultSet.getString("title"),
                        productResultSet.getFloat("price"),
                        productResultSet.getString("image"),
                        productResultSet.getInt("stock"),
                        productResultSet.getInt("rating_count"),
                        productResultSet.getFloat("average_rating"),
                        productResultSet.getBoolean("available_for_client"),
                        userRating,
                        userStock));
            }

            if (sortType == DEFAULT_ORDER)
                if (orderType == DESCENDING_ORDER)
                    Collections.reverse(products);

            return products;
        } catch (SQLException e){
            System.out.println("Something went wrong in fetching products from the database");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return products;
    }

    public static void calculateRating(Connection connection, Session session, Product product, int userRating) {
        // Calculate rating for a product
        try {
            // First insert the rating row to the rating table
            String insertRatingStatement = "INSERT INTO rating (client, product, rating) values (?, ?, ?);";
            PreparedStatement insertRatingPreparedStatement = connection.prepareStatement(insertRatingStatement);
            insertRatingPreparedStatement.setInt(1, session.getIdclient());
            insertRatingPreparedStatement.setInt(2, product.getIdProduct());
            insertRatingPreparedStatement.setInt(3, userRating);
            insertRatingPreparedStatement.executeUpdate();

            // Fetch the product rating count and average rating
            String fetchRatingStatement = "SELECT * FROM product WHERE idproduct = ?;";
            PreparedStatement fetchRatingPreparedStatement = connection.prepareStatement(fetchRatingStatement);
            fetchRatingPreparedStatement.setInt(1, product.getIdProduct());
            ResultSet fetchRatingResultSet = fetchRatingPreparedStatement.executeQuery();
            fetchRatingResultSet.next();

            // Calculating the new rating
            int ratingCount = fetchRatingResultSet.getInt("rating_count");
            float averageRating = fetchRatingResultSet.getFloat("average_rating");

            float newAverageRating = (averageRating * ratingCount + userRating) / (ratingCount + 1);

            // Update table accordingly
            String updateRatingStatement = "UPDATE product SET rating_count = ?, average_rating = ? WHERE idproduct = ?;";
            PreparedStatement updateRatingPreparedStatement = connection.prepareStatement(updateRatingStatement);
            updateRatingPreparedStatement.setInt(1, ratingCount + 1);
            updateRatingPreparedStatement.setFloat(2, newAverageRating);
            updateRatingPreparedStatement.setInt(3, product.getIdProduct());
            updateRatingPreparedStatement.executeUpdate();

            // Assign updated product to the product
            product = ProductHandler.fetchProduct(connection, product.getIdProduct(), session);

        } catch (SQLException e){
            System.out.println("Something went wrong in calculating rating for the product");
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Product> searchInProducts(Connection connection, Session session, String search) throws Exception {
        // Search for products in the database
        ArrayList<Product> products = new ArrayList<>();
        try {
            String productStatement;

            if (!session.isAdmin()) {
                // Fetch only available for client products if user is not admin
                productStatement = "SELECT * FROM product WHERE available_for_client = ? AND title LIKE ?;";

            } else
                productStatement = "SELECT * FROM product WHERE title LIKE ?;";

            PreparedStatement preparedStatementProduct = connection.prepareStatement(productStatement);
            if (!session.isAdmin()) {
                preparedStatementProduct.setBoolean(1, true);
                preparedStatementProduct.setString(2, "%" + search + "%");
            } else
                preparedStatementProduct.setString(1, "%" + search + "%");

            ResultSet productResultSet = preparedStatementProduct.executeQuery();

            while (productResultSet.next()) {
                // Fetch client rating if exists
                int userRating = 0;
                if (!session.isAdmin()) {
                    PreparedStatement prepareStatementRating = connection.prepareStatement("SELECT * FROM rating WHERE client = ? AND product = ?;");
                    prepareStatementRating.setInt(1, session.getIdclient());
                    prepareStatementRating.setInt(2, productResultSet.getInt("idproduct"));
                    ResultSet ratingResultSet = prepareStatementRating.executeQuery();
                    if (ratingResultSet.next()) {
                        userRating = ratingResultSet.getInt("rating");
                    }
                }

                // Fetch the stock of the product in the user basket if exists
                int userStock = 0;
                if (!session.isAdmin()) {
                    PreparedStatement prepareStatementBasket = connection.prepareStatement("SELECT * FROM product_basket WHERE idproduct = ? AND idbasket = ?;");
                    prepareStatementBasket.setInt(1, productResultSet.getInt("idproduct"));
                    prepareStatementBasket.setInt(2, session.getClientBasket().getIdBasket());
                    ResultSet basketResultSet = prepareStatementBasket.executeQuery();
                    if (basketResultSet.next()) {
                        userStock = basketResultSet.getInt("stock");
                    }
                }

                products.add(new Product(
                        productResultSet.getInt("idproduct"),
                        productResultSet.getString("title"),
                        productResultSet.getFloat("price"),
                        productResultSet.getString("image"),
                        productResultSet.getInt("stock"),
                        productResultSet.getInt("rating_count"),
                        productResultSet.getFloat("average_rating"),
                        productResultSet.getBoolean("available_for_client"),
                        userRating,
                        userStock));


            } if (products.isEmpty()) {
                throw new Exception("*No product found with the given search");
            }

        } catch (SQLException e) {
            System.out.println("Something went wrong in searching for products in the database");
            System.out.println(e.getMessage());
        }
        return products;
    }

    public static void modifyProduct(Connection connection, Product product){
        // Modify product in the database
        try {
            String productStatement = "UPDATE product SET title = ?, price = ?, image = ?, stock = ?, available_for_client = ? WHERE idproduct = ?;";
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(productStatement);
            preparedStatement.setString(1, product.getTitle());
            preparedStatement.setFloat(2, product.getPrice());
            preparedStatement.setString(3, product.getPathToImage());
            preparedStatement.setInt(4, product.getStock());
            preparedStatement.setBoolean(5, product.isAvailableForClient());
            preparedStatement.setInt(6, product.getIdProduct());
            preparedStatement.executeUpdate();

        } catch (SQLException e){
            System.out.println("Something went wrong in updating product in the database");
            System.out.println(e.getMessage());
        }
    }

}
