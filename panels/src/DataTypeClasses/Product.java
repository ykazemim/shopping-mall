package DataTypeClasses;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Product {
    private static final int IMAGE_DIMENSION = 150;
    private int idProduct;
    private String title;
    private float price;
    private String pathToImage;
    private int stock;
    private int ratingCount;
    private float averageRating;
    private boolean availableForClient;
    private int clientRating;
    private int stockInBasket;

    public Product(int idProduct, String title, float price, String pathToImage, int stock, int ratingCount, float averageRating, boolean availableForClient, int clientRating, int stockInBasket) {
        this.idProduct = idProduct;
        this.title = title;
        this.price = price;
        this.pathToImage = pathToImage;
        this.stock = stock;
        this.ratingCount = ratingCount;
        this.averageRating = averageRating;
        this.availableForClient = availableForClient;
        this.clientRating = clientRating;
        this.stockInBasket = stockInBasket;
    }

    public int getStockInBasket() {
        return stockInBasket;
    }

    public void setStockInBasket(int stockInBasket) {
        this.stockInBasket = stockInBasket;
    }

    public int getClientRating() {
        return clientRating;
    }

    public void setClientRating(int clientRating) {
        this.clientRating = clientRating;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPathToImage() {
        return pathToImage;
    }

    public void setPathToImage(String pathToImage) {
        this.pathToImage = pathToImage;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public boolean isAvailableForClient() {
        return availableForClient;
    }

    public void setAvailableForClient(boolean availableForClient) {
        this.availableForClient = availableForClient;
    }

    public JPanel createPanel() {
        JPanel bigPanel = new JPanel(new GridBagLayout());
        bigPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);


        // Resizing the product's image into a standard size
        // and then adding it to a Jlabel and finally adding it
        // to the panel
        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(IMAGE_DIMENSION, IMAGE_DIMENSION));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
        try {
            ImageIcon imageIcon = this.loadImage();
            Image resizedImage = imageIcon.getImage();

            int max = Math.max(imageIcon.getIconHeight(), imageIcon.getIconWidth());
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
        JPanel detailsPanel = new JPanel(new GridLayout(5, 3));

        JLabel tempLabel = new JLabel("test");
        JLabel titleLabel = new JLabel("<html><font color='blue'>Title: </font>" + this.title + "</html>");
        JLabel priceLabel = new JLabel("<html><font color='blue'>Price: </font>" + this.price + "</html>");
        JLabel averageRating = new JLabel("<html><font color='blue'>Average rating: </font>" + this.averageRating + "</html>");
        JLabel ratingCount = new JLabel("<html><font color='blue'>Rating count: </font>" + this.ratingCount + "</html>");
        JLabel stockLabel = new JLabel("<html><font color='blue'>Stock: </font>" + this.stock + "</html>");
        JLabel availableForClientLabel = new JLabel("<html><font color='blue'>Available for client: </font>" + this.availableForClient + "</html>");
        JButton rateButton = new JButton("Rate");


        detailsPanel.add(titleLabel);
        detailsPanel.add(priceLabel);
        detailsPanel.add(averageRating);
        detailsPanel.add(ratingCount);
        detailsPanel.add(stockLabel);
        detailsPanel.add(availableForClientLabel);

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


        JSlider ratingSlider = new JSlider(1, 5, 1);
        ratingSlider.setMajorTickSpacing(1);
        ratingSlider.setPaintLabels(true);

        ratingSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
            }
        });

        // Every user can rate a particular product only once
        if(this.clientRating!=0){
            rateButton.setEnabled(false);
            ratingSlider.setValue(this.clientRating);
            ratingSlider.setEnabled(false);
        }

        detailsPanel.add(ratingSlider);

        JPanel rateButtonPanel = new JPanel();
        rateButtonPanel.add(rateButton);
        detailsPanel.add(rateButtonPanel);

        return bigPanel;
    }

    private ImageIcon loadImage() throws Exception {
        ImageIcon imageIcon;

        BufferedImage bufferedImage = null;
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

        imageIcon = new ImageIcon(bufferedImage);

        return imageIcon;
    }
}
