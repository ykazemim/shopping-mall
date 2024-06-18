import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ProductsPanel extends JPanel {

    public ProductsPanel() {
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.8;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(createScrollPanel(), gbc);


    }

    private JPanel createScrollPanel() {
        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        ArrayList<Product> products = ProductHandler.fetchProducts(Initialize.connection, Initialize.session);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        for (Product product : products) {
            listPanel.add(product.createPanel());
            listPanel.add(product.createPanel());
            listPanel.add(product.createPanel());
            listPanel.add(product.createPanel());
            listPanel.add(product.createPanel());
            listPanel.add(product.createPanel());
        }
        JScrollPane scroll = new JScrollPane(listPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Setting a maximum height for scroll pane
        scroll.setPreferredSize(new Dimension(scroll.getPreferredSize().width, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2));

        scrollPanel.add(scroll, gbc);

        return scrollPanel;
    }

    private JPanel createOptionsPanel() {
        JPanel optionPanel = new JPanel();
        return optionPanel;
    }
}
