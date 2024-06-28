import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ClientProductsPanel extends JPanel {
    private JPanel scrollPanel;

    public ClientProductsPanel() {
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
        this.scrollPanel = createScrollPanel(ProductHandler.DEFAULT_ORDER, ProductHandler.DESCENDING_ORDER);
        this.add(this.scrollPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        gbc.weighty = 0.8;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(createOptionsPanel(), gbc);

    }

    public JPanel getScrollPanel() {
        return scrollPanel;
    }

    public void setScrollPanel(JPanel scrollPanel) {
        this.scrollPanel = scrollPanel;
    }

    private JPanel createUpperLeftPanel() {
        JPanel upperLeftPanel = new JPanel();
        upperLeftPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);


        JLabel shopNameLabel = new JLabel("Shopping Mall");

        shopNameLabel.setOpaque(true);
        shopNameLabel.setBackground(Color.ORANGE);
        shopNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        shopNameLabel.setVerticalAlignment(SwingConstants.CENTER);
        shopNameLabel.setFont(new Font(shopNameLabel.getFont().getName(), Font.BOLD, shopNameLabel.getFont().getSize() + 5));


        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
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

        // Get the client's name and username from the session
        String clientName = Initialize.session.getName();
        String clientUsername = Initialize.session.getUsername();

        // Create a JLabel to display the welcome message and the client's username
        JLabel welcomeLabel = new JLabel("Welcome, " + clientName + "!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setVerticalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font(welcomeLabel.getFont().getName(), Font.BOLD, welcomeLabel.getFont().getSize() + 2));

        JLabel usernameLabel = new JLabel( "Your username is: " + clientUsername);
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        usernameLabel.setVerticalAlignment(SwingConstants.CENTER);
        usernameLabel.setFont(new Font(usernameLabel.getFont().getName(), Font.BOLD, usernameLabel.getFont().getSize() + 2));


        // Add the welcomeLabel and usernameLabel to the upperRightPanel
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        upperRightPanel.add(welcomeLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        upperRightPanel.add(usernameLabel, gbc);



        JLabel userTypeLabel = new JLabel("User type: " + (Initialize.session.isAdmin() ? "admin" : "client"));
        JLabel balanceLabel = new JLabel("Balance: " + Main.decimalFormat.format(Initialize.session.getClientCredit()));
        JButton logOutButton = new JButton("Log out");

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Initialize.removeSession();
                Main.changePanel(new SignInPanel());
            }
        });

        userTypeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userTypeLabel.setVerticalAlignment(SwingConstants.CENTER);
        userTypeLabel.setFont(new Font(userTypeLabel.getFont().getName(), Font.BOLD, userTypeLabel.getFont().getSize() + 2));

        // This panel contains addButton, clientButton and salesButton
        JPanel adminOptionsPanel = new JPanel(new GridLayout(2, 1));
        adminOptionsPanel.add(balanceLabel);
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

    private JPanel createScrollPanel(String searchQuery) {
        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        JScrollPane scroll = new JScrollPane(new JLabel(""), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        try {
            ArrayList<Product> products;
            if (!searchQuery.isBlank())
                products = ProductHandler.searchInProducts(Initialize.connection, Initialize.session, searchQuery);
            else
                products = ProductHandler.fetchProducts(Initialize.connection, Initialize.session, ProductHandler.DEFAULT_ORDER, ProductHandler.DESCENDING_ORDER);

            JPanel listPanel = new JPanel();
            listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

            for (Product product : products) {
                listPanel.add(product.createPanel(Product.CLIENT_MAIN_PANEL));
                scroll = new JScrollPane(listPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            }

        } catch (Exception e) {
            scroll = new JScrollPane(new JLabel(e.getMessage()), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        }


        // Setting a maximum height for scroll pane
        scroll.setPreferredSize(new Dimension(scroll.getPreferredSize().width, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2));

        scrollPanel.add(scroll, gbc);

        return scrollPanel;
    }

    private JPanel createScrollPanel(int sortType, int orderType) {
        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        ArrayList<Product> products = ProductHandler.fetchProducts(Initialize.connection, Initialize.session, sortType, orderType);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        for (Product product : products)
            listPanel.add(product.createPanel(Product.CLIENT_MAIN_PANEL));

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

        String[] sortOptions = {"Default (Time Added)", "Rating ascending", "Rating descending", "Price ascending", "Price descending"};

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
                int sortType = -1;
                int orderType = -1;
                switch (selectedOption) {
                    case "Default (Time Added)":
                        sortType = ProductHandler.DEFAULT_ORDER;
                        orderType = ProductHandler.DESCENDING_ORDER;
                        break;
                    case "Rating ascending":
                        sortType = ProductHandler.SORT_BY_RATING;
                        orderType = ProductHandler.ASCENDING_ORDER;
                        break;
                    case "Rating descending":
                        sortType = ProductHandler.SORT_BY_RATING;
                        orderType = ProductHandler.DESCENDING_ORDER;
                        break;
                    case "Price ascending":
                        sortType = ProductHandler.SORT_BY_PRICE;
                        orderType = ProductHandler.ASCENDING_ORDER;
                        break;
                    case "Price descending":
                        sortType = ProductHandler.SORT_BY_PRICE;
                        orderType = ProductHandler.DESCENDING_ORDER;
                        break;
                    default:
                        System.out.println("Something went wrong!");
                        break;
                }
                ClientProductsPanel.this.updateScrollPanel(ClientProductsPanel.this.createScrollPanel(sortType, orderType));
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

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientProductsPanel.this.updateScrollPanel(ClientProductsPanel.this.createScrollPanel(searchTextField.getText()));
            }
        });

        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.changePanel(new ClientProfilePanel());
            }
        });

        basketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.changePanel(new BasketPanel());
            }
        });

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

    private void updateScrollPanel(JPanel scrollPanel) {
        ClientProductsPanel.this.remove(ClientProductsPanel.this.scrollPanel);
        ClientProductsPanel.this.scrollPanel = scrollPanel;

        // Add the new scroll panel to the ClientProductsPanel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.8;
        gbc.weighty = 0.8;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        ClientProductsPanel.this.add(ClientProductsPanel.this.scrollPanel, gbc);

        ClientProductsPanel.this.revalidate();
        ClientProductsPanel.this.repaint();
        Main.refreshFrame();
    }

}
