import java.sql.*;

public class User {
    private Session newSession = null;

    public User(Connection connection, String name, String username, String password, String phone, String address) throws Exception {
        // Since only clients allowed to sign up in this app, this constructor only create client instances

        String hashedPassword = PasswordHasher.hashPassword(password);
        try{
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
            preparedStatement = connection.prepareStatement(clientStatement);

            preparedStatement.setString(1,address);
            preparedStatement.setFloat(2,0f);
            preparedStatement.setInt(3, idUser);

            preparedStatement.executeUpdate();

            // Set the session
            newSession = new Session(connection, username, password);

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public Session getSession() {
        return newSession;
    }
}
