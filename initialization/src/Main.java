public class Main {
    // Change url and credentials accordingly
    private final static String url = "jdbc:mysql://localhost:3306/";
    private final static String username = "root";
    private final static String password = "molioo1298";

    public static void main(String[] args) {

        InitializeDB initializeDB = new InitializeDB(url,username,password);

        initializeDB.initialize();
    }
}
