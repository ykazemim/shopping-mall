/**
 * This app's icon is downloaded from:
 * https://www.flaticon.com/free-icon/shopping-bag_743007?term=shopping+mall&page=1&position=26&origin=tag&related_id=743007
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static final String dbURL = "jdbc:mysql://localhost:3306/mall";
    private static JFrame frame;

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        String username, password;
        System.out.println("Please enter database username and password.");
        while (true) {
            try {
                System.out.print("Username: ");
                username = scanner.nextLine();
                System.out.print("Password: ");
                password = scanner.nextLine();

                // Create a connection to sql server
                new Initialize(dbURL, username, password);
                break;
            } catch (Exception e) {
                System.out.println("Failed to log in. Try again.");
                System.out.println("------------------------------------------");
                // TODO: Maybe log e.getMessage()
            }
        }
        scanner.close();

        frame = new JFrame("Shopping Mall");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 1));
        frame.setResizable(false);

        // Setting the app's icon
        try {
            BufferedImage img = ImageIO.read(Objects.requireNonNull(Main.class.getResource("app_icon.png")));
            frame.setIconImage(img);
        } catch (IllegalArgumentException | IOException e) {
            System.out.println("Failed to set the app's icon.");
        }


        SignInPanel signInPanel = new SignInPanel();
        frame.add(signInPanel);


        refreshFrame();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    protected static void refreshFrame() {
        frame.pack();
        frame.pack();
        frame.setSize(new Dimension((int) (frame.getSize().width * 1.25), (int) (frame.getSize().height * 1.25)));
        frame.setLocationRelativeTo(null);
        frame.revalidate();
        frame.repaint();
    }

    protected static void changePanel(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.add(panel);
        refreshFrame();
    }
}
