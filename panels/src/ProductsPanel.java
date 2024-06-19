import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProductsPanel extends JPanel {

    public ProductsPanel() {
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.8;
        gbc.weighty = 0.2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(createUpperLeftPanel(), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.weighty = 0.2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(createUpperRightPanel(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.8;
        gbc.weighty = 0.8;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(createScrollPanel(), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        gbc.weighty = 0.8;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(createOptionsPanel(), gbc);

    }

    private JPanel createUpperLeftPanel() {
        JPanel upperLeftPanel = new JPanel();
        upperLeftPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);


        JLabel shopNameLabel = new JLabel("Shopping Mall");
        JButton addButton = new JButton("Add");
        JButton clientButton = new JButton("Client");
        JButton salesButton = new JButton("Sales");

        shopNameLabel.setOpaque(true);
        shopNameLabel.setBackground(Color.ORANGE);
        shopNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        shopNameLabel.setVerticalAlignment(SwingConstants.CENTER);
        shopNameLabel.setFont(new Font(shopNameLabel.getFont().getName(), Font.BOLD, shopNameLabel.getFont().getSize() + 5));

        // This panel contains addButton, clientButton and salesButton
        JPanel adminOptionsPanel = new JPanel(new GridLayout(3, 1));
        adminOptionsPanel.add(addButton);
        adminOptionsPanel.add(clientButton);
        adminOptionsPanel.add(salesButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        upperLeftPanel.add(adminOptionsPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.BOTH;
        upperLeftPanel.add(shopNameLabel, gbc);


        return upperLeftPanel;
    }

    private JPanel createUpperRightPanel() {
        JPanel upperRightPanel = new JPanel();
        upperRightPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);


        JLabel userTypeLabel = new JLabel("User type: " + (Initialize.session.isAdmin() ? "admin" : "client"));
        JButton balanceButton = new JButton("Balance");
        JButton logOutButton = new JButton("Log out");


        userTypeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userTypeLabel.setVerticalAlignment(SwingConstants.CENTER);
        userTypeLabel.setFont(new Font(userTypeLabel.getFont().getName(), Font.BOLD, userTypeLabel.getFont().getSize() + 2));

        // This panel contains addButton, clientButton and salesButton
        JPanel adminOptionsPanel = new JPanel(new GridLayout(2, 1));
        adminOptionsPanel.add(balanceButton);
        adminOptionsPanel.add(logOutButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        upperRightPanel.add(userTypeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.BOTH;
        upperRightPanel.add(adminOptionsPanel, gbc);


        return upperRightPanel;
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

        ArrayList<Product> products = ProductHandler.fetchProducts(Initialize.connection, Initialize.session, ProductHandler.SORT_BY_RATING, ProductHandler.ASCENDING_ORDER);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        for (Product product : products) {
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
        JPanel optionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weighty = 0;

        String[] sortOptions = {"Default (Time Added)", "Rating", "Price"};

        JLabel sortLabel = new JLabel("Sort by: ");
        JTextField searchTextField = new JTextField();
        JButton searchButton = new JButton("Search");
        JButton sortButton = new JButton("Sort");
        JButton basketButton = new JButton("Basket");
        JButton profileButton = new JButton("Profile");
        JComboBox<String> sortComboBox = new JComboBox<>(sortOptions);

        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) sortComboBox.getSelectedItem();
                System.out.println(selectedOption);
            }
        });

        // This panel contains searchTextField and searchButton
        JPanel searchPanel = new JPanel(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        searchPanel.add(searchTextField, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.NONE;
        searchPanel.add(searchButton, gbc);


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        optionPanel.add(searchPanel, gbc);


        gbc.gridy++;
        optionPanel.add(sortLabel, gbc);

        gbc.gridy++;
        optionPanel.add(sortComboBox, gbc);

        gbc.gridy++;
        optionPanel.add(sortButton, gbc);

        // This panel contains basketButton and profileButton
        JPanel basketAndProfilePanel = new JPanel(new GridBagLayout());

        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        basketAndProfilePanel.add(basketButton, gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        basketAndProfilePanel.add(profileButton, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        optionPanel.add(basketAndProfilePanel, gbc);

        return optionPanel;
    }
}