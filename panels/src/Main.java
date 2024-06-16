import javax.swing.*;
import java.awt.*;

public class Main {
    private static final String username = "root";
    private static final String password = "67812792";
    private static final String dbURL = "jdbc:mysql://localhost:3306/mall";
    private static JFrame frame;

    public static void main(String[] args) throws Exception{

        // Create a connection to sql server
        new Initialize(dbURL, username, password);
        Initialize.setSession(new Session(Initialize.connection,"molio_98","Moli@1298"));

        frame = new JFrame("Shopping Mall");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 1));
//        frame.setResizable(false);


//        SignInPanel signInPanel = new SignInPanel();
//        frame.add(signInPanel);

//        AddProductPanel addProductPanel = new AddProductPanel();
//        frame.add(addProductPanel);

        ProductScrollPanel productScrollPanel = new ProductScrollPanel();
        frame.add(productScrollPanel);


        refreshFrame();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    protected static void refreshFrame() {
        frame.pack();
        frame.setSize(new Dimension((int) (frame.getSize().width * 1.25), (int) (frame.getSize().height * 1.25)));
        frame.revalidate();
        frame.repaint();
    }

    protected static void changePanel(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.add(panel);
        refreshFrame();
    }
}
