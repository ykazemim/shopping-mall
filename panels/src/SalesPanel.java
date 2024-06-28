import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SalesPanel extends JPanel {
    private float totalSales=0;
    private int totalBaskets=0;

    public SalesPanel() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JButton backButton = new JButton("Back");

        backButton.addActionListener(e -> {
            Main.changePanel(new AdminProductsPanel());
        });

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

        for (Basket basket : baskets) {
            listPanel.add(Basket.createPanel(basket));
            totalSales+=basket.getTotal();
            totalBaskets++;
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

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(createUpperPanel(), gbc);

        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(scrollPanel, gbc);

        gbc.gridy++;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor=GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        this.add(backButton, gbc);
    }

    private JPanel createUpperPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel introLabel = new JLabel("All sales");
        JLabel totalBasketsLabel1 = new JLabel("Total baskets: ");
        JLabel totalBasketsLabel2 = new JLabel(String.valueOf(totalBaskets));
        JLabel totalSalesLabel1 = new JLabel("Total sales: ");
        JLabel totalSalesLabel2 = new JLabel(String.valueOf(totalSales));

        introLabel.setPreferredSize(new Dimension(400, 30));
        introLabel.setHorizontalAlignment(SwingConstants.CENTER);
        introLabel.setOpaque(true);
        introLabel.setBackground(Color.GREEN);
        introLabel.setFont(new Font(introLabel.getFont().getName(), Font.BOLD, introLabel.getFont().getSize() + 4));
        totalBasketsLabel1.setFont(new Font(totalBasketsLabel1.getFont().getName(), Font.BOLD, totalBasketsLabel1.getFont().getSize() + 3));
        totalSalesLabel1.setFont(new Font(totalSalesLabel1.getFont().getName(), Font.BOLD, totalSalesLabel1.getFont().getSize() + 3));
        totalBasketsLabel2.setFont(new Font(totalBasketsLabel2.getFont().getName(), Font.BOLD, totalBasketsLabel2.getFont().getSize() + 3));
        totalSalesLabel2.setFont(new Font(totalSalesLabel2.getFont().getName(), Font.BOLD, totalSalesLabel2.getFont().getSize() + 3));
        totalBasketsLabel1.setForeground(Color.RED);
        totalSalesLabel1.setForeground(Color.RED);

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
        panel.add(totalSalesLabel1, gbc);

        gbc.gridx++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(totalSalesLabel2, gbc);
        gbc.weightx = 0;

        return panel;
    }
}
