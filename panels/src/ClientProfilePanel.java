import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientProfilePanel extends JPanel implements ActionListener {

    private final JLabel introLabel;
    private final JLabel fullnameLabel1;
    private final JLabel usernameLabel1;
    private final JLabel phoneLabel1;
    private final JLabel addressLabel1;
    private final JLabel fullnameLabel2;
    private final JLabel usernameLabel2;
    private final JLabel phoneLabel2;
    private final JLabel addressLabel2;
    private final JLabel balanceLabel1;
    private final JLabel balanceLabel2;
    private final JLabel addBalanceLabel;
    private final JTextArea errorsLabel;
    private final JButton editButton;
    private final JButton goToShopButton;
    private final JButton basketHistoryButton;
    private final JButton addBalanceButton;
    private final JTextField addBalanceTextField;


    public ClientProfilePanel() {
        introLabel = new JLabel("Profile");
        fullnameLabel1 = new JLabel("Full Name");
        usernameLabel1 = new JLabel("Username");
        phoneLabel1 = new JLabel("Phone");
        addressLabel1 = new JLabel("Address");
        balanceLabel1 = new JLabel("Balance");
        addBalanceLabel = new JLabel("Add balance");
        fullnameLabel2 = new JLabel(Initialize.session.getName());
        usernameLabel2 = new JLabel(Initialize.session.getUsername());
        phoneLabel2 = new JLabel(Initialize.session.getPhone());
        addressLabel2 = new JLabel(Initialize.session.getClientAddress());
        balanceLabel2 = new JLabel(String.valueOf(Initialize.session.getClientCredit()));
        errorsLabel = new JTextArea();
        editButton = new JButton("Edit");
        goToShopButton = new JButton("Go to shop");
        basketHistoryButton = new JButton("Basket history");
        addBalanceButton = new JButton("Increase");
        addBalanceTextField = new JTextField();

        introLabel.setPreferredSize(new Dimension(300, 30));
        introLabel.setHorizontalAlignment(SwingConstants.CENTER);
        introLabel.setOpaque(true);
        introLabel.setBackground(Color.GREEN);
        introLabel.setFont(new Font(introLabel.getFont().getName(), Font.BOLD, introLabel.getFont().getSize() + 1));
        errorsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorsLabel.setLineWrap(true);
        errorsLabel.setEditable(false);
        errorsLabel.setBackground(null);
        errorsLabel.setForeground(Color.RED);
        errorsLabel.setVisible(false);

        editButton.addActionListener(this);
        goToShopButton.addActionListener(this);
        addBalanceButton.addActionListener(this);
        basketHistoryButton.addActionListener(this);

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

        gbc.gridy++; // Move to next row
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        add(addressLabel1, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(addressLabel2, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        add(balanceLabel1, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(balanceLabel2, gbc);

        gbc.gridy++; // Move to next row
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        add(addBalanceLabel, gbc);

        // This panel contains addBalanceTextField and addBalanceButton
        JPanel tempPanel = new JPanel(new GridBagLayout());
        GridBagConstraints tempGbc = new GridBagConstraints();
        tempGbc.insets = new Insets(0, 5, 0, 5);

        tempGbc.gridx = 0;
        tempGbc.gridy = 0;
        tempGbc.weightx = 1;
        tempGbc.anchor = GridBagConstraints.LINE_START;
        tempGbc.fill = GridBagConstraints.HORIZONTAL;
        tempPanel.add(addBalanceTextField, tempGbc);

        tempGbc.gridx++;
        tempGbc.weightx = 0;
        tempGbc.anchor = GridBagConstraints.LINE_END;
        tempGbc.fill = GridBagConstraints.NONE;
        tempPanel.add(addBalanceButton, tempGbc);

        // Adding tempPanel
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tempPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        add(errorsLabel, gbc);

        // This panel contains basketHistoryButton and editButton
        JPanel tempPanel2 = new JPanel(new GridLayout(1, 2));
        tempPanel2.add(basketHistoryButton);
        tempPanel2.add(editButton);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tempPanel2, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(goToShopButton, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        errorsLabel.setText("");
        if (src.equals(addBalanceButton)) {
            try {
                float amount = Float.parseFloat(addBalanceTextField.getText());
                if (amount < 0) {
                    errorsLabel.setText( errorsLabel.getText() + '\n' + "Amount should be a positive number");
                    errorsLabel.setVisible(true);
                } else {
                    try {
                        Client.addBalance(Initialize.session.getIdclient(), amount,Initialize.connection);
                        Initialize.session.setClientCredit(Initialize.session.getClientCredit() + amount);
                        balanceLabel2.setText(Main.decimalFormat.format(Initialize.session.getClientCredit()));
                        addBalanceTextField.setText("");
                        Main.refreshFrame();
                        errorsLabel.setText("");
                    } catch (Exception ex) {
                        errorsLabel.setText( errorsLabel.getText() + '\n' + ex.getMessage());
                        errorsLabel.setVisible(true);
                    }
                }
            } catch (NumberFormatException ex){
                errorsLabel.setText( errorsLabel.getText() + '\n' + "Amount should be a number");
                errorsLabel.setVisible(true);
            }


        } else if (src.equals(editButton)) {
            Main.changePanel(new ClientEditPanel());
        } else if (src.equals(basketHistoryButton)) {
            Main.changePanel(new ClientBasketHistoryPanel());
        } else if (src.equals(goToShopButton)) {
            Main.changePanel(new ClientProductsPanel());
        }
    }
}
