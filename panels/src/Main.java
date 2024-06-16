import DataTypeClasses.Product;

import javax.swing.*;
import java.awt.*;

public class Main {
    private static final String username = "root";
    private static final String password = "molioo1298";
    private static final String dbURL = "jdbc:mysql://localhost:3306/mall";
    private static JFrame frame;

    public static void main(String[] args) {

        // Create a connection to sql server
        new Initialize(dbURL, username, password);

        frame = new JFrame("Shopping Mall");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 1));
//        frame.setResizable(false);


//        SignInPanel signInPanel = new SignInPanel();
//        frame.add(signInPanel);

//        AddProductPanel addProductPanel = new AddProductPanel();
//        frame.add(addProductPanel);
//
        Product product = new Product(12597423, "Sport car", 250.783f, "D:\\Music\\.thumbnails\\152453.jpg", 13, 729, 4.3f, true);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel,BoxLayout.Y_AXIS));
        listPanel.add(product.createPanel());
        listPanel.add(product.createPanel());
        listPanel.add(product.createPanel());
        listPanel.add(product.createPanel());
        listPanel.add(product.createPanel());
        listPanel.add(product.createPanel());
        listPanel.add(product.createPanel());
        JScrollPane scroll = new JScrollPane(listPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


//        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();

        frame.add(scroll);


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
