package SQLDefinedClasses;
import DataTypeClasses.Basket;
import DataTypeClasses.Client;

import java.sql.*;
import java.util.ArrayList;

public class User {
    private Session newSession = null;

    public User(Connection connection, String name, String username, String password, String phone, String address) throws Exception {
        // Since only clients allowed to sign up in this app, this constructor only create client instances

        String hashedPassword = PasswordHasher.hashPassword(password);
        try{
            // Check if the username is already taken
            String checkUsernameStatement = "SELECT * FROM User WHERE username = ?;";
            PreparedStatement checkUsernamePreparedStatement = connection.prepareStatement(checkUsernameStatement);
            checkUsernamePreparedStatement.setString(1,username);
            ResultSet resultSet = checkUsernamePreparedStatement.executeQuery();
            if(resultSet.next())
                throw new Exception("Username is already taken");

            // Insert into User table
            String userStatement = "INSERT INTO User (username, password, name, phone, user_type) values (?, ?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(userStatement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,hashedPassword);
            preparedStatement.setString(3,name);
            preparedStatement.setString(4,phone);
            preparedStatement.setString(5,"CLIENT");
            preparedStatement.executeUpdate();

            // Retrieve the last inserted user ID
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            int idUser = -1;
            if(generatedKeys.next()){
                idUser = generatedKeys.getInt(1);
            }

            if(idUser == -1)
                throw new Exception("Something went wrong in getting user id");

            // Insert into Client table using the retrieved user ID
            String clientStatement = "INSERT INTO Client (address, credit, user) values (?, ?, ?);";
            preparedStatement = connection.prepareStatement(clientStatement, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1,address);
            preparedStatement.setFloat(2,0f);
            preparedStatement.setInt(3, idUser);

            preparedStatement.executeUpdate();

            // Retrieve the last inserted client ID
            generatedKeys = preparedStatement.getGeneratedKeys();
            idUser = -1;
            if(generatedKeys.next()){
                idUser = generatedKeys.getInt(1);
            }

            if(idUser == -1)
                throw new Exception("Something went wrong in getting client id");

            // Set the session
            newSession = new Session(connection, username, password);

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Client> fetchAllClients(Connection connection) {
        ArrayList<Client> clients = new ArrayList<>();
        try{
            // Fetch all clients from the database
            String clientStatement = "SELECT * FROM Client;";
            PreparedStatement preparedStatement = connection.prepareStatement(clientStatement);
            ResultSet clientResult = preparedStatement.executeQuery();

            while (clientResult.next()){
                String userStatement = "SELECT * FROM User WHERE iduser = ?;";
                PreparedStatement userPreparedStatement = connection.prepareStatement(userStatement);
                userPreparedStatement.setInt(1,clientResult.getInt("user"));
                ResultSet userResultSet = userPreparedStatement.executeQuery();
                userResultSet.next();
                clients.add(new Client(
                        clientResult.getInt("idclient"),
                        clientResult.getInt("user"),
                        clientResult.getString("address"),
                        clientResult.getFloat("credit"),
                        userResultSet.getString("name"),
                        userResultSet.getString("phone"),
                        userResultSet.getString("username")

                ));
            }
            return clients;
        } catch (SQLException e){
            System.out.println("Something went wrong in fetching clients from the database");
            System.out.println(e.getMessage());
        }
        return clients;
    }

    public Session getSession() {
        return newSession;
    }
}
