import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminEditPanel extends JPanel implements ActionListener {

    private final JLabel introLabel;
    private final JLabel fullnameLabel;
    private final JLabel usernameLabel;
    private final JLabel passwordLabel;
    private final JLabel phoneLabel;
    private final JTextArea errorsLabel;
    private final JButton editButton;
    private final JButton backButton;
    private final JTextField fullnameTextField;
    private final JTextField usernameTextField;
    private final JPasswordField passwordTextField;
    private final JTextField phoneTextField;


    public AdminEditPanel() {
        introLabel = new JLabel("Please fill the fields");
        fullnameLabel = new JLabel("Full Name");
        usernameLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");
        phoneLabel = new JLabel("Phone");
        errorsLabel = new JTextArea();
        editButton = new JButton("Edit");
        backButton = new JButton("Back");
        fullnameTextField = new JTextField(Initialize.session.getName());
        usernameTextField = new JTextField(Initialize.session.getUsername());
        passwordTextField = new JPasswordField();
        phoneTextField = new JTextField(Initialize.session.getPhone());

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
        backButton.addActionListener(this);

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
        add(fullnameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_END; // Left alignment for text fields
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(fullnameTextField, gbc);

        gbc.gridwidth = 1; // Reset to one column span
        gbc.anchor = GridBagConstraints.LINE_START; // Right alignment for labels
        gbc.gridy++; // Move to next row
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_END; // Left alignment for text fields
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(usernameTextField, gbc);

        gbc.gridy++; // Move to next row
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(passwordTextField, gbc);

        gbc.gridy++; // Move to next row
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        add(phoneLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(phoneTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        add(errorsLabel, gbc);


        gbc.anchor = GridBagConstraints.CENTER; // set to default
        gbc.gridy++; // Move to next row
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(backButton, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(editButton, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src.equals(editButton)) {

            // Get the inputs
            String username = usernameTextField.getText();
            String password = String.valueOf(passwordTextField.getPassword());
            String name = fullnameTextField.getText();
            String phone = phoneTextField.getText();

            ArrayList<String> errors = Validator.validateEditAdminProfileForm(name, username, password, phone);

            if (errors.isEmpty()) {
                try {
                    User.modifyAdmin(Initialize.session.getIduser(), name, username, password, phone, Initialize.connection);
                    Initialize.session = new Session(Initialize.connection, username, password);
                    Main.changePanel(new AdminProfilePanel());
                    errorsLabel.setText("");
                } catch (Exception exception) {
                    errorsLabel.setText(exception.getMessage());
                }
            } else {
                errorsLabel.setText("");
                for (String error : errors) {
                    errorsLabel.setText(errorsLabel.getText() + "\n* " + error);
                }
            }

            errorsLabel.setVisible(true);
            Main.refreshFrame();
        } else if (src.equals(backButton)) {
            Main.changePanel(new AdminProfilePanel());
        }
    }
}
