public class Main {
    public static void main(String[] args) {
        InitializeDB initializeDB = new InitializeDB("jdbc:mysql://localhost:3306/",
                "root",
                "molioo1298");

        initializeDB.initialize();
    }
}
