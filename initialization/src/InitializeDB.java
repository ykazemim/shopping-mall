import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class InitializeDB {
    private Connection connection = null;
    public InitializeDB(String url, String username, String password) {
        try{
            this.connection = DriverManager.getConnection(url,username,password);
            System.out.println("Connected to sql server!");
        } catch (SQLException ex){
            System.out.println("Something went wrong in connecting to sql server:");
            System.out.println(ex.getMessage());
        }
    }
    public void initialize() {
        if(connection != null){
            System.out.println("Executing sql commands");
            try{
                System.out.println("Creating database 'mall'");
                connection.createStatement().execute("CREATE DATABASE mall");
                System.out.println("Creating table user");
                connection.createStatement().execute("""
                        CREATE TABLE `mall`.`user` (
                          `iduser` INT NOT NULL AUTO_INCREMENT,
                          `username` VARCHAR(45) NOT NULL,
                          `password` VARCHAR(45) NOT NULL,
                          `name` VARCHAR(45) NOT NULL,
                          `phone` VARCHAR(45) NULL,
                          `user_type` VARCHAR(45) NOT NULL,
                          PRIMARY KEY (`iduser`),
                          UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE);
                        """);

                System.out.println("Creating table product");
                connection.createStatement().execute("""
                        CREATE TABLE `mall`.`product` (
                          `idproduct` INT NOT NULL AUTO_INCREMENT,
                          `title` VARCHAR(45) NOT NULL,
                          `price` FLOAT NOT NULL,
                          `image` NVARCHAR(260) NULL DEFAULT NULL,
                          `stock` INT NULL,
                          `rating_count` INT NULL,
                          `average_rating` FLOAT NULL,
                          `available_for_client` BOOLEAN NULL,
                          PRIMARY KEY (`idproduct`));""");

                System.out.println("Creating table client");
                connection.createStatement().execute("""
                        CREATE TABLE `mall`.`client` (
                          `idclient` INT NOT NULL AUTO_INCREMENT,
                          `address` VARCHAR(45) NULL,
                          `credit` FLOAT ZEROFILL NULL,
                          `user` INT NOT NULL,
                          FOREIGN KEY (`user`) REFERENCES user(`iduser`) ON DELETE CASCADE,
                          PRIMARY KEY (`idclient`));
                        """);

                System.out.println("Creating table basket");
                connection.createStatement().execute("""
                        CREATE TABLE `mall`.`basket` (
                          `idbasket` INT NOT NULL AUTO_INCREMENT,
                          `client` INT NOT NULL,
                          `date_proceeded` DATETIME NULL,
                          `total_price` FLOAT NULL,
                          `is_proceeded` BOOLEAN NULL,
                          PRIMARY KEY (`idbasket`),
                          FOREIGN KEY (`client`) REFERENCES client(`idclient`) ON DELETE CASCADE,
                          UNIQUE INDEX `idbasket_UNIQUE` (`idbasket` ASC) VISIBLE);""");

                System.out.println("Creating table product_basket");
                connection.createStatement().execute("""
                        CREATE TABLE `mall`.`product_basket` (
                          `idbasket` INT NOT NULL,
                          `idproduct` INT NOT NULL,
                          `stock` INT NULL,
                          PRIMARY KEY (`idbasket`, `idproduct`),
                        FOREIGN KEY (`idbasket`) REFERENCES basket(`idbasket`) ON DELETE CASCADE,
                        FOREIGN KEY (`idproduct`) REFERENCES product(`idproduct`) ON DELETE CASCADE
                        );""");

                System.out.println("Creating table rating");
                connection.createStatement().execute("""
                        CREATE TABLE `mall`.`rating` (
                          `idrating` INT NOT NULL AUTO_INCREMENT,
                          `client` INT NOT NULL,
                          `product` INT NOT NULL,
                          `rating` INT NULL,
                          PRIMARY KEY (`idrating`),
                          FOREIGN KEY (`client`) REFERENCES client(`idclient`) ON DELETE CASCADE,
                          FOREIGN KEY (`product`) REFERENCES product(`idproduct`) ON DELETE CASCADE);
                       """);

                // Prompting the user to enter the admin credentials
                ArrayList<String> errors = new ArrayList<>();
                String adminUsername, adminPassword, adminName, adminPhone;
                do {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Enter the admin username: ");
                    adminUsername = scanner.nextLine();
                    System.out.println("Enter the admin password: ");
                    adminPassword = scanner.nextLine();
                    System.out.println("Enter the admin name: ");
                    adminName = scanner.nextLine();
                    System.out.println("Enter the admin phone: ");
                    adminPhone = scanner.nextLine();
                    errors = Validator.validateSingUpForm(adminName, adminUsername, adminPassword, adminPhone);
                    if (!errors.isEmpty()) {
                        System.out.println("Please fix the following errors:");
                        for (String error : errors) {
                            System.out.println(error);
                        }
                    }
                } while (!errors.isEmpty());

                this.createAdmin(adminUsername, adminPassword, adminName, adminPhone);

                System.out.println("All done!");

            } catch (SQLException ex){
                System.out.println("Something went wrong : ");
                System.out.println(ex.getMessage());
            }
        } else {
            System.out.println("Connection was not established.");
        }
    }

    public void createAdmin(String username, String password, String name, String phone){
        if(connection != null){
            try{
                String hashedPassword = PasswordHasher.hashPassword(password);
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO mall.user (username, password, name, phone, user_type) VALUES (?,?,?,?,'ADMIN')");
                preparedStatement.setString(1,username);
                preparedStatement.setString(2,hashedPassword);
                preparedStatement.setString(3,name);
                preparedStatement.setString(4,phone);
                preparedStatement.execute();
                System.out.println("Admin created successfully!");
            } catch (SQLException ex){
                System.out.println("Something went wrong in creating the admin:");
                System.out.println(ex.getMessage());
            }
        } else {
            System.out.println("Connection was not established.");
        }
    }

}
