package SQLDefinedClasses;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Session {
    private int iduser;
    private int idclient;
    private String username;
    private String name;
    private String phone;
    private String userType;
    private String clientAddress;
    private float clientCredit;
    private boolean isAdmin;

    public Session(Connection connection,String username, String password) throws Exception{
        String hashedPassword = PasswordHasher.hashPassword(password);
        try {
            // Make a query to database
            String st = "SELECT * FROM user WHERE username=? && password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(st);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,hashedPassword);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Checks if it's in the database or not
            if(!resultSet.next()){
                throw new Exception("Wrong credentials");
            } else {

                // Assigns fields in their corresponding information from database
                this.iduser = resultSet.getInt(1);
                this.username = resultSet.getString(2);
                this.name = resultSet.getString(4);
                this.phone = resultSet.getString(5);
                this.userType = resultSet.getString(6);

                if(this.userType.equals("CLIENT")){
                    this.isAdmin = false;

                    // Fetching client properties from database
                    PreparedStatement ps = connection.prepareStatement("SELECT * FROM client WHERE user=?");
                    ps.setInt(1,this.iduser);
                    ResultSet rs = ps.executeQuery();
                    rs.next();

                    this.idclient = rs.getInt(1);
                    this.clientAddress = rs.getString(2);
                    this.clientCredit = rs.getFloat(3);

                } else {
                    this.isAdmin = true;
                }
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public int getIduser() {
        return iduser;
    }

    public int getIdclient() {
        return idclient;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getUserType() {
        return userType;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public float getClientCredit() {
        return clientCredit;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public void setClientCredit(float clientCredit) {
        this.clientCredit = clientCredit;
    }
}
