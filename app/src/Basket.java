import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;

public class Basket {
    private int idBasket;
    private int client;
    private Timestamp timestamp;
    private float total;
    private boolean isProceeded;

    public Basket(int idBasket, int client, Timestamp timestamp, float total, boolean isProceeded) {
        this.idBasket = idBasket;
        this.client = client;
        this.timestamp = timestamp;
        this.total = total;
        this.isProceeded = isProceeded;
    }

    public int getIdBasket() {
        return idBasket;
    }

    public void setIdBasket(int idBasket) {
        this.idBasket = idBasket;
    }

    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public boolean isProceeded() {
        return isProceeded;
    }

    public void setProceeded(boolean proceeded) {
        isProceeded = proceeded;
    }

    public static JPanel createPanel(Basket basket){
            JPanel bigPanel = new JPanel(new GridLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);

            // This panel contains basket details
            JPanel midPanel = new JPanel(new GridBagLayout());
            midPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));

            JLabel usernameLabel1 = new JLabel("Username");
            JLabel dateLabel1 = new JLabel("Date");
            JLabel totalPriceLabel1 = new JLabel("Total price");
            JLabel usernameLabel2 = new JLabel(User.fetchClientById(Initialize.connection, basket.getClient()).getUsername());
            JLabel dateLabel2 = new JLabel(String.valueOf(basket.getTimestamp()));
            JLabel totalPriceLabel2 = new JLabel(Main.decimalFormat.format(basket.getTotal()));
            JButton detailsButton = new JButton("Details");

            usernameLabel1.setForeground(Color.BLUE);
            dateLabel1.setForeground(Color.BLUE);
            totalPriceLabel1.setForeground(Color.BLUE);
            usernameLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
            dateLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
            totalPriceLabel2.setHorizontalAlignment(SwingConstants.RIGHT);


            detailsButton.addActionListener(e -> {
                Main.changePanel(new BasketDetailsPanel(basket));
            });

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
