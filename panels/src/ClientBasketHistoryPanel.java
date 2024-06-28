import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ClientBasketHistoryPanel extends JPanel {

    private int totalBaskets=0;
    private float totalSpent = 0;

    public ClientBasketHistoryPanel() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JButton backButton = new JButton("Back");

        backButton.addActionListener(e -> {
            Main.changePanel(new ClientProductsPanel());
        });

        ArrayList<Basket> baskets;
        try {
            baskets = BasketHandler.fetchBasketFromClient(Initialize.connection,Initialize.session.getIdclient(),true);
        } catch (Exception e) {
            System.out.println("Something went wrong in fetching baskets.");
            e.printStackTrace();
            this.removeAll();
            return;
        }

        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new GridBagLayout());

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        for (Basket basket : baskets) {
            listPanel.add(Basket.createPanel(basket));
            totalBaskets++;
            totalSpent+=basket.getTotal();
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
        gbc.weightx=0;
        gbc.weighty=0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(createUpperPanel(), gbc);

        gbc.gridy = 1;
        gbc.weightx=1;
        gbc.weighty=1;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(scrollPanel, gbc);


        gbc.gridy++;
        gbc.anchor=GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        this.add(backButton, gbc);
    }

    private JPanel createUpperPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel introLabel = new JLabel("All proceeded baskets");
        JLabel totalBasketsLabel1 = new JLabel("Total baskets: ");
        JLabel totalBasketsLabel2 = new JLabel(String.valueOf(totalBaskets));
        JLabel totalSpentLabel1 = new JLabel("Total spent: ");
        JLabel totalSpentLabel2 = new JLabel(String.valueOf(totalSpent));

        introLabel.setPreferredSize(new Dimension(400, 30));
        introLabel.setHorizontalAlignment(SwingConstants.CENTER);
        introLabel.setOpaque(true);
        introLabel.setBackground(Color.GREEN);
        introLabel.setFont(new Font(introLabel.getFont().getName(), Font.BOLD, introLabel.getFont().getSize() + 4));
        totalBasketsLabel1.setFont(new Font(totalBasketsLabel1.getFont().getName(), Font.BOLD, totalBasketsLabel1.getFont().getSize() + 3));
        totalSpentLabel1.setFont(new Font(totalSpentLabel1.getFont().getName(), Font.BOLD, totalSpentLabel1.getFont().getSize() + 3));
        totalBasketsLabel2.setFont(new Font(totalBasketsLabel2.getFont().getName(), Font.BOLD, totalBasketsLabel2.getFont().getSize() + 3));
        totalSpentLabel2.setFont(new Font(totalSpentLabel2.getFont().getName(), Font.BOLD, totalSpentLabel2.getFont().getSize() + 3));
        totalBasketsLabel1.setForeground(Color.RED);
        totalSpentLabel1.setForeground(Color.RED);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth=4;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(introLabel, gbc);
        gbc.gridwidth=1;

        gbc.gridx=0;
        gbc.gridy++;
        gbc.weightx = 0.25;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(totalBasketsLabel1, gbc);

        gbc.gridx++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(totalBasketsLabel2, gbc);

        gbc.gridx++;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(totalSpentLabel1, gbc);

        gbc.gridx++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(totalSpentLabel2, gbc);

        return panel;
    }
}
