import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignInPanel extends JPanel implements ActionListener {

    private final JLabel introLabel;
    private final JLabel usernameLabel;
    private final JLabel passwordLabel;
    private final JTextArea errorsLabel;
    private final JButton signInButton;
    private final JButton signUpButton;
    private final JTextField usernameTextField;
    private final JPasswordField passwordTextField;


    public SignInPanel() {
        introLabel = new JLabel("Please enter your username and password");
        usernameLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");
        errorsLabel = new JTextArea("Put an error here\nPut an error here\n");
        signInButton = new JButton("Sign in");
        signUpButton = new JButton("Sign up");
        usernameTextField = new JTextField();
        passwordTextField = new JPasswordField();

        introLabel.setPreferredSize(new Dimension(300,30));
        introLabel.setHorizontalAlignment(SwingConstants.CENTER);
        introLabel.setOpaque(true);
        introLabel.setBackground(Color.GREEN);
        introLabel.setFont(new Font(introLabel.getFont().getName(),Font.BOLD, introLabel.getFont().getSize()+1));
        errorsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorsLabel.setLineWrap(true);
        errorsLabel.setEditable(false);
        errorsLabel.setBackground(null);
        errorsLabel.setForeground(Color.RED);
        errorsLabel.setVisible(false);

        signInButton.addActionListener(this);
        signUpButton.addActionListener(this);

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

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        add(errorsLabel, gbc);

        gbc.anchor = GridBagConstraints.CENTER; // set to default
        gbc.gridy++; // Move to next row
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(signUpButton, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(signInButton, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src.equals(signInButton)) {
            errorsLabel.setVisible(true);
            Main.refreshFrame();
        } else if (src.equals(signUpButton)) {
            Main.changePanel(new SignUpPanel());
        }
    }
}
