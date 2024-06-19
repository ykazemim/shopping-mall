import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Initialize {
    protected static Connection connection;
    protected static Session session;

    public Initialize(String url, String username, String password){
        try{
            connection = DriverManager.getConnection(url,username,password);
            System.out.println("Connected to DB");
        } catch (SQLException se){
            System.out.println("Something went wrong in connecting to sql db.");
            System.out.println(se.getMessage());
        }

    }

    public static void setSession(Session passedSession){
        session = passedSession;
    }

    public static void removeSession(){
        // For logout
        session = null;
    }

}
