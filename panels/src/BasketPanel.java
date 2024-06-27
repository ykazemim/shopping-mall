import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BasketPanel extends JPanel implements ActionListener {
    JLabel introLabel;
    JLabel balanceLabel;
    JLabel totalPriceLabel;
    JButton profileButton;
    JButton proceedButton;
    JButton goToShopButton;
    Basket basket;
    JPanel listPanel;
    ArrayList<Product> products;

    public BasketPanel() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        ArrayList<Basket> baskets = BasketHandler.fetchBasketFromClient(Initialize.connection, Initialize.session.getIdclient(), false);

        basket = baskets.get(0);
        ArrayList<Product> products = BasketHandler.fetchProductsFromBasket(Initialize.connection, basket, Initialize.session);


        JPanel belowPanel = new JPanel(new GridBagLayout());

        introLabel = new JLabel("Your basket");
        balanceLabel = new JLabel("Balance: " + Initialize.session.getClientCredit());
        totalPriceLabel = new JLabel("Total price: " + basket.getTotal());
        profileButton = new JButton("Profile");
        proceedButton = new JButton("Proceed");
        goToShopButton = new JButton("Go to shop");

        introLabel.setPreferredSize(new Dimension(300, 30));
        introLabel.setHorizontalAlignment(SwingConstants.CENTER);
        introLabel.setOpaque(true);
        introLabel.setBackground(Color.GREEN);
        introLabel.setFont(new Font(introLabel.getFont().getName(), Font.BOLD, introLabel.getFont().getSize() + 3));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        balanceLabel.setOpaque(true);
        balanceLabel.setBackground(Color.ORANGE);
        totalPriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalPriceLabel.setOpaque(true);
        totalPriceLabel.setBackground(Color.ORANGE);
        profileButton.setPreferredSize(goToShopButton.getPreferredSize());
        proceedButton.setPreferredSize(goToShopButton.getPreferredSize());

        profileButton.addActionListener(this);
        proceedButton.addActionListener(this);
        goToShopButton.addActionListener(this);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(introLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        this.add(createScrollPanel(), gbc);
        gbc.weightx = 0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        belowPanel.add(totalPriceLabel, gbc);
        gbc.gridwidth = 1;

        gbc.gridy++;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        belowPanel.add(balanceLabel, gbc);
        gbc.gridwidth = 1;

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        belowPanel.add(profileButton, gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        belowPanel.add(proceedButton, gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        belowPanel.add(goToShopButton, gbc);


        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(belowPanel, gbc);
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

        ArrayList<Basket> baskets = BasketHandler.fetchBasketFromClient(Initialize.connection, Initialize.session.getIdclient(), false);
        Basket basket = baskets.getFirst();

        if (basket.getTotal() == 0)
            proceedButton.setEnabled(false);
        else
            proceedButton.setEnabled(true);
        products = BasketHandler.fetchProductsFromBasket(Initialize.connection, basket, Initialize.session);

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        for (Product product : products)
            listPanel.add(product.createClientBasketPanel(this));

        JScrollPane scroll = new JScrollPane(listPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Setting a maximum height for scroll pane
        scroll.setPreferredSize(new Dimension(scroll.getPreferredSize().width, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2));

        scrollPanel.add(scroll, gbc);

        return scrollPanel;
    }

    public void updateBelowPanel() {
        basket = BasketHandler.fetchBasketFromClient(Initialize.connection, Initialize.session.getIdclient(), false).getFirst();
        balanceLabel.setText("Balance: " + Initialize.session.getClientCredit());
        totalPriceLabel.setText("Total price: " + basket.getTotal());
        if (basket.getTotal() == 0)
            proceedButton.setEnabled(false);
        else
            proceedButton.setEnabled(true);
    }

    public void updateListPanel() {
        basket = BasketHandler.fetchBasketFromClient(Initialize.connection, Initialize.session.getIdclient(), false).getFirst();
        products = BasketHandler.fetchProductsFromBasket(Initialize.connection, basket, Initialize.session);
        listPanel.removeAll();
        for (Product product : products)
            listPanel.add(product.createClientBasketPanel(this));
        Main.refreshFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src.equals(profileButton)) {
            Main.changePanel(new ClientProfilePanel());
        } else if (src.equals(proceedButton)) {

            try {
                BasketHandler.checkout(Initialize.connection, basket);
                // TODO maybe a message dialog
                System.out.println("Success!");
                updateListPanel();
                updateBelowPanel();
                Main.changePanel(new ClientProfilePanel());
            } catch (Exception ex){
                //TODO
            }

        } else if (src.equals(goToShopButton)) {
            Main.changePanel(new ClientProductsPanel());
        }
    }
}
