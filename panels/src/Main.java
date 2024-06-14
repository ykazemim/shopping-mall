import javax.swing.*;
import java.awt.*;

public class Main {
    private static JFrame frame;

    public static void main(String[] args) {
        frame = new JFrame("Shopping Mall");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 1));
        frame.setResizable(false);


        SignInPanel signInPanel = new SignInPanel();
        frame.add(signInPanel);

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
