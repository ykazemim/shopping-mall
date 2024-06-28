import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Product {
    public static final int ADMIN_MAIN_PANEL = 0;
    public static final int CLIENT_MAIN_PANEL = 1;
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

    public JPanel createPanel(int panelType) {
        return switch (panelType) {
            case ADMIN_MAIN_PANEL -> this.createAdminPanel();
            case CLIENT_MAIN_PANEL -> this.createClientPanel();
            default -> null;
        };
    }

    private JPanel createAdminPanel() {

        JPanel bigPanel = new JPanel(new GridBagLayout());
        bigPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(5, 5, 5, 5);


        // Resizing the product's image into a standard size
        // and then adding it to a JLabel and finally adding it
        // to the panel
        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(IMAGE_DIMENSION, IMAGE_DIMENSION));
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
        JPanel detailsPanel = new JPanel(new GridLayout(3, 3));

        JLabel titleLabel = new JLabel("<html><font color='blue'>Title: </font>" + this.title + "</html>");
        JLabel priceLabel = new JLabel("<html><font color='blue'>Price: </font>" + Main.decimalFormat.format(this.price) + "</html>");
        JLabel averageRating = new JLabel("<html><font color='blue'>Average rating: </font>" + Main.decimalFormat.format(this.averageRating) + "</html>");
        JLabel ratingCount = new JLabel("<html><font color='blue'>Rating count: </font>" + this.ratingCount + "</html>");
        JLabel stockLabel = new JLabel("<html><font color='blue'>Stock: </font>" + this.stock + "</html>");
        JLabel availableForClientLabel = new JLabel("<html><font color='blue'>Available for client: </font>" + this.availableForClient + "</html>");
        JButton modifyButton = new JButton("Modify");


        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.changePanel(new ModifyProductPanel(Product.this));
            }
        });


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

        // Adding modify button
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        bigPanel.add(modifyButton, gbc);
        gbc.gridwidth = 1;

        return bigPanel;
    }

    private JPanel createClientPanel() {

        JPanel bigPanel = new JPanel(new GridBagLayout());
        bigPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(5, 5, 5, 5);


        // Resizing the product's image into a standard size
        // and then adding it to a JLabel and finally adding it
        // to the panel
        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(IMAGE_DIMENSION, IMAGE_DIMENSION));
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
        JPanel detailsPanel = new JPanel(new GridLayout(4, 3));

        JLabel titleLabel = new JLabel("<html><font color='blue'>Title: </font>" + this.title + "</html>");
        JLabel priceLabel = new JLabel("<html><font color='blue'>Price: </font>" + Main.decimalFormat.format(this.price) + "</html>");
        JLabel averageRating = new JLabel("<html><font color='blue'>Average rating: </font>" + Main.decimalFormat.format(this.averageRating) + "</html>");
        JLabel ratingCount = new JLabel("<html><font color='blue'>Rating count: </font>" + this.ratingCount + "</html>");
        JButton rateButton = new JButton("Rate");
        JButton addToBasketButton = new JButton("Add to basket");
        JButton removeFromBasket = new JButton("Remove from basket");
        JLabel stockInBasket = new JLabel("<html><font color='blue'>Stock in basket: </font>" + this.stockInBasket + "</html>");


        detailsPanel.add(titleLabel);
        detailsPanel.add(priceLabel);
        detailsPanel.add(averageRating);
        detailsPanel.add(ratingCount);


        JSlider ratingSlider = new JSlider(1, 5, 1);
        ratingSlider.setMajorTickSpacing(1);
        ratingSlider.setPaintLabels(true);
        ratingSlider.setPreferredSize(new Dimension(100, (int) ratingSlider.getPreferredSize().getHeight()));

        rateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Make changes to the database
                ProductHandler.calculateRating(Initialize.connection, Initialize.session, Product.this, ratingSlider.getValue());

                // Update product object accordingly
                Product temp = ProductHandler.fetchProduct(Initialize.connection, Product.this.idProduct, Initialize.session);
                Product.this.updateProduct(temp);
                Product.this.updatePanelFields(ratingCount, averageRating, stockInBasket);
                rateButton.setEnabled(false);
                ratingSlider.setEnabled(false);

                Main.refreshFrame();
            }
        });

        addToBasketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Make changes to the database
                if (Product.this.stockInBasket + 1 <= Product.this.stock) {
                    BasketHandler.addProductToBasket(Initialize.connection, Product.this, Initialize.session.getClientBasket());
                }

                // Make changes accordingly in the runtime
                if (Product.this.stockInBasket + 1 == Product.this.stock) {
                    addToBasketButton.setEnabled(false);
                }

                if (!removeFromBasket.isEnabled()) removeFromBasket.setEnabled(true);

                // Update product object accordingly
                Product temp = ProductHandler.fetchProduct(Initialize.connection, Product.this.idProduct, Initialize.session);
                Product.this.updateProduct(temp);
                Product.this.updatePanelFields(ratingCount, averageRating, stockInBasket);

                Main.refreshFrame();
            }
        });

        removeFromBasket.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Make changes to the database
                BasketHandler.removeProductFromBasket(Initialize.connection, Product.this, Initialize.session.getClientBasket());
                if (!addToBasketButton.isEnabled()) {
                    addToBasketButton.setEnabled(true);
                }
                if (Product.this.stockInBasket - 1 == 0) removeFromBasket.setEnabled(false);

                // Update product object accordingly
                Product temp = ProductHandler.fetchProduct(Initialize.connection, Product.this.idProduct, Initialize.session);
                Product.this.updateProduct(temp);
                Product.this.updatePanelFields(ratingCount, averageRating, stockInBasket);

                Main.refreshFrame();
            }
        });

        // Every user can rate a particular product only once
        if (this.clientRating != 0) {
            rateButton.setEnabled(false);
            ratingSlider.setValue(this.clientRating);
            ratingSlider.setEnabled(false);
        }

        // If user doesn't have a particular product in his basket, then he
        // must not be able to delete that product from his basket
        if (this.stockInBasket == 0) {
            removeFromBasket.setEnabled(false);
        }

        // If user has the most possible count of a particular product in his basket,
        // then he must not be able to add that product to his basket again
        if (this.stockInBasket == this.stock) {
            addToBasketButton.setEnabled(false);
        }


        // This panel contains rate slider and rate button
        JPanel ratePanel = new JPanel(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        ratePanel.add(ratingSlider, gbc);

        gbc.gridx++;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_END;
        ratePanel.add(rateButton, gbc);

        detailsPanel.add(ratePanel);
        detailsPanel.add(stockInBasket);


        // These two tempPanels contains just one button each
        JPanel tempPanel1 = new JPanel(new GridBagLayout());
        JPanel tempPanel2 = new JPanel(new GridBagLayout());

        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        tempPanel1.add(addToBasketButton, gbc);
        tempPanel2.add(removeFromBasket, gbc);

        detailsPanel.add(tempPanel1);
        detailsPanel.add(tempPanel2);

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

    public JPanel createClientBasketPanel(BasketPanel basketPanel) {

        JPanel bigPanel = new JPanel(new GridBagLayout());
        bigPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(5, 5, 5, 5);


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
        JPanel detailsPanel = new JPanel(new GridLayout(4, 3));

        JLabel titleLabel = new JLabel("<html><font color='blue'>Title: </font>" + this.title + "</html>");
        JLabel priceLabel = new JLabel("<html><font color='blue'>Price: </font>" + Main.decimalFormat.format(this.price) + "</html>");
        JLabel averageRating = new JLabel("<html><font color='blue'>Average rating: </font>" + Main.decimalFormat.format(this.averageRating) + "</html>");
        JLabel ratingCount = new JLabel("<html><font color='blue'>Rating count: </font>" + this.ratingCount + "</html>");
        JButton rateButton = new JButton("Rate");
        JButton addToBasketButton = new JButton("Increase count");
        JButton removeFromBasket = new JButton("Decrease count");
        JLabel stockInBasket = new JLabel("<html><font color='blue'>Stock in basket: </font>" + this.stockInBasket + "</html>");


        detailsPanel.add(titleLabel);
        detailsPanel.add(priceLabel);
        detailsPanel.add(averageRating);
        detailsPanel.add(ratingCount);


        JSlider ratingSlider = new JSlider(1, 5, 1);
        ratingSlider.setMajorTickSpacing(1);
        ratingSlider.setPaintLabels(true);
        ratingSlider.setPreferredSize(new Dimension(100, (int) ratingSlider.getPreferredSize().getHeight()));

        rateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Make changes to the database
                ProductHandler.calculateRating(Initialize.connection, Initialize.session, Product.this, ratingSlider.getValue());

                // Update product object accordingly
                Product temp = ProductHandler.fetchProduct(Initialize.connection, Product.this.idProduct, Initialize.session);
                Product.this.updateProduct(temp);
                Product.this.updatePanelFields(ratingCount, averageRating, stockInBasket);
                rateButton.setEnabled(false);
                ratingSlider.setEnabled(false);

                Main.refreshFrame();
            }
        });

        addToBasketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Make changes to the database
                if (Product.this.stockInBasket + 1 <= Product.this.stock) {
                    BasketHandler.addProductToBasket(Initialize.connection, Product.this, Initialize.session.getClientBasket());
                }

                // Make changes accordingly in the runtime
                if (Product.this.stockInBasket + 1 == Product.this.stock) {
                    addToBasketButton.setEnabled(false);
                }

                if (!removeFromBasket.isEnabled()) removeFromBasket.setEnabled(true);

                // Update product object accordingly
                Product temp = ProductHandler.fetchProduct(Initialize.connection, Product.this.idProduct, Initialize.session);
                Product.this.updateProduct(temp);
                Product.this.updatePanelFields(ratingCount, averageRating, stockInBasket);

                basketPanel.updateBelowPanel();
                Main.refreshFrame();
            }
        });

        removeFromBasket.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Make changes to the database
                BasketHandler.removeProductFromBasket(Initialize.connection, Product.this, Initialize.session.getClientBasket());
                if (!addToBasketButton.isEnabled()) {
                    addToBasketButton.setEnabled(true);
                }
                if (Product.this.stockInBasket - 1 == 0) {
                    removeFromBasket.setEnabled(false);
                    bigPanel.removeAll();
                }

                // Update product object accordingly
                Product temp = ProductHandler.fetchProduct(Initialize.connection, Product.this.idProduct, Initialize.session);
                Product.this.updateProduct(temp);
                Product.this.updatePanelFields(ratingCount, averageRating, stockInBasket);

                basketPanel.updateBelowPanel();
                basketPanel.updateListPanel();
                Main.refreshFrame();
            }
        });

        // Every user can rate a particular product only once
        if (this.clientRating != 0) {
            rateButton.setEnabled(false);
            ratingSlider.setValue(this.clientRating);
            ratingSlider.setEnabled(false);
        }

        // If user doesn't have a particular product in his basket, then he
        // must not be able to delete that product from his basket
        if (this.stockInBasket == 0) {
            removeFromBasket.setEnabled(false);
        }

        // If user has the most possible count of a particular product in his basket,
        // then he must not be able to add that product to his basket again
        if (this.stockInBasket == this.stock) {
            addToBasketButton.setEnabled(false);
        }


        // This panel contains rate slider and rate button
        JPanel ratePanel = new JPanel(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        ratePanel.add(ratingSlider, gbc);

        gbc.gridx++;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_END;
        ratePanel.add(rateButton, gbc);

        detailsPanel.add(ratePanel);
        detailsPanel.add(stockInBasket);


        // These two tempPanels contains just one button each
        JPanel tempPanel1 = new JPanel(new GridBagLayout());
        JPanel tempPanel2 = new JPanel(new GridBagLayout());

        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        tempPanel1.add(addToBasketButton, gbc);
        tempPanel2.add(removeFromBasket, gbc);

        detailsPanel.add(tempPanel1);
        detailsPanel.add(tempPanel2);

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

    private void updateProduct(Product product) {
        this.idProduct = product.getIdProduct();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.pathToImage = product.getPathToImage();
        this.stock = product.getStock();
        this.ratingCount = product.getRatingCount();
        this.averageRating = product.getAverageRating();
        this.availableForClient = product.isAvailableForClient();
        this.clientRating = product.getClientRating();
        this.stockInBasket = product.getStockInBasket();
    }


    private void updatePanelFields(JLabel ratingCount, JLabel averageRating, JLabel stockInBasket) {
        ratingCount.setText("<html><font color='blue'>Rating count: </font>" + Main.decimalFormat.format(this.ratingCount) + "</html>");
        averageRating.setText("<html><font color='blue'>Average rating: </font>" + Main.decimalFormat.format(this.averageRating) + "</html>");
        stockInBasket.setText("<html><font color='blue'>Stock in basket: </font>" + this.stockInBasket + "</html>");
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
