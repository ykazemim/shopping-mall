import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class InitializeDB {
    private Connection connection = null;
    public InitializeDB(String url, String username, String password) {
        try{
            this.connection = DriverManager.getConnection(url,username,password);
            System.out.println("Connected to sql server...");
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
                          `image` VARCHAR(45) NULL,
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
                          `credit` FLOAT NULL,
                          PRIMARY KEY (`idclient`));
                        """);

                System.out.println("Creating table basket");
                connection.createStatement().execute("""
                        CREATE TABLE `mall`.`basket` (
                          `idbasket` INT NOT NULL AUTO_INCREMENT,
                          `client` INT NOT NULL,
                          `date_proceded` DATETIME NULL,
                          `total_price` FLOAT NULL,
                          `is_proceded` BOOLEAN NULL,
                          PRIMARY KEY (`idbasket`),
                          FOREIGN KEY (`client`) REFERENCES client(`idclient`) ON DELETE CASCADE,
                          UNIQUE INDEX `idbasket_UNIQUE` (`idbasket` ASC) VISIBLE);""");

                System.out.println("Creating table product_basket");
                connection.createStatement().execute("""
                        CREATE TABLE `mall`.`product_basket` (
                          `idbasket` INT NOT NULL,
                          `idproduct` INT NOT NULL,
                          PRIMARY KEY (`idbasket`, `idproduct`),
                        FOREIGN KEY (`idbasket`) REFERENCES basket(`idbasket`) ON DELETE CASCADE,
                        FOREIGN KEY (`idproduct`) REFERENCES product(`idproduct`) ON DELETE CASCADE
                        );""");

                System.out.println("All done!");

            } catch (SQLException ex){
                System.out.println("Something went wrong : ");
                System.out.println(ex.getMessage());
            }
        } else {
            System.out.println("Connection was not established.");
        }
    }
}
