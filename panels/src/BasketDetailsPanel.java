import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BasketDetailsPanel extends JPanel {
    private static final int IMAGE_DIMENSION = 150;
    private String pathToImage;

    public BasketDetailsPanel(Basket basket) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        ArrayList<Product> products = new ArrayList<>();
        String error = "";
        try {
            products = BasketHandler.retrieveProductsFromClientBasket(Initialize.connection, basket);
        } catch (Exception e) {
            error = e.getMessage();
        }


        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new GridBagLayout());

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        if (error.isBlank())
            for (Product product : products) {
                listPanel.add(createProductPanel(product));
            }
        else
            listPanel.add(new JLabel("Something went wrong in fetching products"));

        JScrollPane scroll = new JScrollPane(listPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Setting a maximum height for scroll pane
        scroll.setPreferredSize(new Dimension(scroll.getPreferredSize().width, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        scrollPanel.add(scroll, gbc);

        JPanel belowPanel = new JPanel(new GridLayout(4, 2));

        JLabel totalCostLabel1 = new JLabel("Total cost: ");
        JLabel timeLabel1 = new JLabel("Time: ");
        JLabel usernameLabel1 = new JLabel("Username: ");
        JLabel totalCostLabel2 = new JLabel(String.valueOf(basket.getTotal()));
        JLabel timeLabel2 = new JLabel(String.valueOf(basket.getTimestamp()));
        JLabel usernameLabel2 = new JLabel(User.fetchClientById(Initialize.connection, basket.getClient()).getUsername());
        JButton backButton = new JButton("Back");
        JButton goToShopButton = new JButton("Go to shop");

        totalCostLabel1.setForeground(Color.RED);
        timeLabel1.setForeground(Color.RED);
        usernameLabel1.setForeground(Color.RED);
        totalCostLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        timeLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        usernameLabel1.setHorizontalAlignment(SwingConstants.RIGHT);

        backButton.addActionListener(e -> {
            if (Initialize.session.isAdmin()) Main.changePanel(new SalesPanel());
            else Main.changePanel(new ClientBasketHistoryPanel());
        });

        goToShopButton.addActionListener(e -> {
            if (Initialize.session.isAdmin()) Main.changePanel(new AdminProductsPanel());
            else Main.changePanel(new ClientProductsPanel());
        });

        belowPanel.add(usernameLabel1);
        belowPanel.add(usernameLabel2);
        belowPanel.add(timeLabel1);
        belowPanel.add(timeLabel2);
        belowPanel.add(totalCostLabel1);
        belowPanel.add(totalCostLabel2);
        belowPanel.add(backButton);
        belowPanel.add(goToShopButton);


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(scrollPanel, gbc);

        gbc.gridy++;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(belowPanel, gbc);
    }

    private JPanel createProductPanel(Product product) {
        JPanel bigPanel = new JPanel(new GridBagLayout());
        bigPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        this.pathToImage = product.getPathToImage();

        gbc.weightx = 0;
        gbc.weighty = 0;


        // Resizing the product's image into a standard size
        // and then adding it to a JLabel and finally adding it
        // to the panel
        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(IMAGE_DIMENSION, IMAGE_DIMENSION));
        try {
            ImageIcon imageIcon = this.loadImage();
            Image resizedImage = imageIcon.getImage();

            if (imageIcon.getIconWidth() >= imageIcon.getIconHeight()) {
                resizedImage = imageIcon.getImage().getScaledInstance(IMAGE_DIMENSION, imageIcon.getIconHeight() * IMAGE_DIMENSION / imageIcon.getIconWidth(), Image.SCALE_SMOOTH);
            } else if (imageIcon.getIconWidth() < imageIcon.getIconHeight()) {
                resizedImage = imageIcon.getImage().getScaledInstance(imageIcon.getIconWidth() * IMAGE_DIMENSION / imageIcon.getIconHeight(), IMAGE_DIMENSION, Image.SCALE_SMOOTH);
            }
            imageLabel.setIcon(new ImageIcon(resizedImage));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            imageLabel.setForeground(Color.RED);
            imageLabel.setText("error");
        }


        // This panel contains product's details
        JPanel detailsPanel = new JPanel(new GridLayout(4, 1));

        JLabel titleLabel = new JLabel("<html><font color='blue'>Title: </font>" + product.getTitle() + "</html>");
        JLabel priceLabel = new JLabel("<html><font color='blue'>Price: </font>" + product.getPrice() + "</html>");
        JLabel averageRating = new JLabel("<html><font color='blue'>Average rating: </font>" + product.getAverageRating() + "</html>");
        JLabel userRating;
        if (product.getClientRating() != -1)
            userRating = new JLabel("<html><font color='blue'>User rating: </font>" + product.getClientRating() + "</html>");
        else
            userRating = new JLabel("<html><font color='blue'> User rating: </font>" + "<font color='red'> null </font>" + "</html>");
        JLabel purchasedCountLabel = new JLabel("<html><font color='blue'>Purchased count: </font>" + product.getStockInBasket() + "</html>");

        detailsPanel.add(titleLabel);
        detailsPanel.add(priceLabel);
        detailsPanel.add(averageRating);
        detailsPanel.add(userRating);
        detailsPanel.add(purchasedCountLabel);

        // Adding image
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        bigPanel.add(imageLabel, gbc);

        // Adding details
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 0;
        bigPanel.add(detailsPanel, gbc);

        return bigPanel;
    }

    private ImageIcon loadImage() throws Exception {
        ImageIcon imageIcon;

        BufferedImage bufferedImage = null;

        if (this.pathToImage == null) bufferedImage = ImageIO.read(Product.class.getResource("default_image.png"));
        else {
            try {
                bufferedImage = ImageIO.read(new File(this.pathToImage));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Error: No image input");
            } catch (IOException e) {
                throw new IOException("Error: Something went wrong during reading image");
            } finally {
                if (bufferedImage == null) {
                    throw new Exception("Error: Something went wrong during reading image");
                }
            }
        }

        imageIcon = new ImageIcon(bufferedImage);

        return imageIcon;
    }
}
