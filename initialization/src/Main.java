import java.util.Scanner;

public class Main {
    // Change url and credentials accordingly
    private final static String url = "jdbc:mysql://localhost:3306/";
    private static String username;
    private static String password;

    public static void main(String[] args) {

        // Checking args
        if (!processArgs(args)) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your username: ");
            username = scanner.nextLine();
            System.out.println("Enter your password: ");
            password = scanner.nextLine();
        }
        InitializeDB initializeDB = new InitializeDB(url, username, password);
        
        initializeDB.initialize();
    }

    private static boolean processArgs(String[] args) {
        if(args.length==2){
            username=args[0];
            password=args[1];
            return true;
        }
        return false;
    }
}
