import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SalesPanel extends JPanel {
    private int basketNumber = 1;

    public SalesPanel() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(createUpperPanel(), gbc);


        ArrayList<Basket> baskets;
        try {
            baskets = BasketHandler.fetchProceededBaskets(Initialize.connection);
        } catch (Exception e) {
            System.out.println("Something went wrong in fetching all proceeded products.");
            e.printStackTrace();
            this.removeAll();
            return;
        }


        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new GridBagLayout());

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        ArrayList<Client> clients = User.fetchAllClients(Initialize.connection);

        for (Basket basket : baskets) {
            listPanel.add(createPanelForBasket(basket));
        }

        JScrollPane scroll = new JScrollPane(listPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Setting a maximum height for scroll pane
        scroll.setPreferredSize(new Dimension(scroll.getPreferredSize().width, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        scrollPanel.add(scroll, gbc);

        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(scrollPanel, gbc);
    }

    private JPanel createUpperPanel() {
        JPanel panel = new JPanel();
        JLabel introLabel =  new JLabel();

        return panel;
    }

    private JPanel createPanelForBasket(Basket basket) {
        JPanel bigPanel = new JPanel(new GridLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // This panel contains basket details
        JPanel midPanel = new JPanel(new GridBagLayout());
        midPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));

        JLabel basketNumberLabel1 = new JLabel("Basket number");
        JLabel usernameLabel1 = new JLabel("Username");
        JLabel dateLabel1 = new JLabel("Date");
        JLabel totalPriceLabel1 = new JLabel("Total price");
        JLabel basketNumberLabel2 = new JLabel(String.valueOf(basketNumber++));
        JLabel usernameLabel2 = new JLabel("To do");
        JLabel dateLabel2 = new JLabel(String.valueOf(basket.getTimestamp()));
        JLabel totalPriceLabel2 = new JLabel(String.valueOf(basket.getTotal()));
        JButton detailsButton = new JButton("Details");

        basketNumberLabel1.setForeground(Color.BLUE);
        usernameLabel1.setForeground(Color.BLUE);
        dateLabel1.setForeground(Color.BLUE);
        totalPriceLabel1.setForeground(Color.BLUE);

        detailsButton.addActionListener(e -> {
            // TODO
            System.out.println("detailsButton clicked");
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        midPanel.add(basketNumberLabel1, gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.LINE_END;
        midPanel.add(basketNumberLabel2, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.LINE_START;
        midPanel.add(usernameLabel1, gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.LINE_END;
        midPanel.add(usernameLabel2, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.LINE_START;
        midPanel.add(dateLabel1, gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.LINE_END;
        midPanel.add(dateLabel2, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.LINE_START;
        midPanel.add(totalPriceLabel1, gbc);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        midPanel.add(totalPriceLabel2, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        midPanel.add(detailsButton, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        bigPanel.add(midPanel, gbc);

        return bigPanel;
    }
}
