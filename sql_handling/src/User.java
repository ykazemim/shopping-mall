import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class User {
    private Session newSession = null;

    public User(Connection connection, String name, String username, String password, String phone, String address) throws Exception {
        // Since only clients allowed to sign up in this app, this constructor only create client instances
        String hashedPassword = PasswordHasher.hashPassword(password);
        try{
            String st = """
                    -- Insert into User table
                    INSERT INTO User (username, password, name, phone, user_type) values (?, ?, ?, ?, ?);
                    -- Retrieve the last inserted user ID
                    SET @last_user_id = LAST_INSERT_ID();
                    -- Insert into Client table using the retrieved user ID
                    INSERT INTO Client (address, credit, user) values (?, ?, @last_user_id);""";

            PreparedStatement preparedStatement = connection.prepareStatement(st);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,hashedPassword);
            preparedStatement.setString(3,name);
            preparedStatement.setString(4,phone);
            preparedStatement.setString(5,"CLIENT");
            preparedStatement.setString(6,address);
            preparedStatement.setFloat(7,0f);

            preparedStatement.execute();

            newSession = new Session(connection, username, password);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public Session getSession() {
        return newSession;
    }
}
