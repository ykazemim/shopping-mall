import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminProfilePanel extends JPanel implements ActionListener {

    private final JLabel introLabel;
    private final JLabel fullnameLabel1;
    private final JLabel usernameLabel1;
    private final JLabel phoneLabel1;
    private final JLabel fullnameLabel2;
    private final JLabel usernameLabel2;
    private final JLabel phoneLabel2;
    private final JButton editButton;
    private final JButton goToShopButton;


    public AdminProfilePanel() {
        introLabel = new JLabel("Profile");
        fullnameLabel1 = new JLabel("Full Name");
        usernameLabel1 = new JLabel("Username");
        phoneLabel1 = new JLabel("Phone");
        fullnameLabel2 = new JLabel(Initialize.session.getName());
        usernameLabel2 = new JLabel(Initialize.session.getUsername());
        phoneLabel2 = new JLabel(Initialize.session.getPhone());
        editButton = new JButton("Edit");
        goToShopButton = new JButton("Go to shop");

        introLabel.setPreferredSize(new Dimension(300, 30));
        introLabel.setHorizontalAlignment(SwingConstants.CENTER);
        introLabel.setOpaque(true);
        introLabel.setBackground(Color.GREEN);
        introLabel.setFont(new Font(introLabel.getFont().getName(), Font.BOLD, introLabel.getFont().getSize() + 1));

        editButton.addActionListener(this);
        goToShopButton.addActionListener(this);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5); // Add some padding between components

        gbc.gridx = 0; // First column
        gbc.gridy = 0; // First row
        gbc.gridwidth = 2; // Span two columns
        gbc.anchor = GridBagConstraints.CENTER; // Center alignment
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(introLabel, gbc);

        gbc.gridwidth = 1; // Reset to one column span
        gbc.anchor = GridBagConstraints.LINE_START; // Right alignment for labels
        gbc.gridy++; // Move to next row
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        add(fullnameLabel1, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_END; // Left alignment for text fields
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(fullnameLabel2, gbc);

        gbc.gridwidth = 1; // Reset to one column span
        gbc.anchor = GridBagConstraints.LINE_START; // Right alignment for labels
        gbc.gridy++; // Move to next row
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        add(usernameLabel1, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_END; // Left alignment for text fields
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(usernameLabel2, gbc);


        gbc.gridy++; // Move to next row
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        add(phoneLabel1, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(phoneLabel2, gbc);

        // This panel contains editButton and goToShopButton
        JPanel tempPanel = new JPanel(new GridLayout(1,2));
        tempPanel.add(editButton);
        tempPanel.add(goToShopButton);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tempPanel, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src.equals(editButton)) {
            Main.changePanel(new AdminEditPanel());
        } else if (src.equals(goToShopButton)) {
            Main.changePanel(new AdminProductsPanel());
        }
    }
}
