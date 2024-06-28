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

public class Main {
    private static final String username = "root";
    private static final String password = "67812792";
    private static final String dbURL = "jdbc:mysql://localhost:3306/mall";
    private static JFrame frame;

    public static void main(String[] args) throws Exception {

        // Create a connection to sql server
        new Initialize(dbURL, username, password);

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
