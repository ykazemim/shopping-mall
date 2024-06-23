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

        frame = new JFrame("Shopping Mall");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1,1));
//        frame.setResizable(false);


//        SignInPanel signInPanel = new SignInPanel();
//        frame.add(signInPanel);

//        AddProductPanel addProductPanel = new aAddProductPanel();
//        frame.add(addProductPanel);

        Initialize.setSession(new Session(Initialize.connection,"amir_125","Moli@1299"));
        AdminProductsPanel adminProductsPanel = new AdminProductsPanel();
        frame.add(adminProductsPanel);

//        Initialize.setSession(new Session(Initialize.connection,"molio_98","Moli@1298"));
//        ClientProductsPanel clientProductsPanel = new ClientProductsPanel();
//        frame.add(clientProductsPanel);


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
