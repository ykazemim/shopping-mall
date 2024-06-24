import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Client {
    private final int idclient;
    private final int iduser;
    private String clientAddress;
    private float clientCredit;
    private String clientName;
    private String clientPhone;
    private String username;

    public Client(int idclient, int iduser, String clientAddress, float clientCredit, String clientName, String clientPhone, String username) {
        this.idclient = idclient;
        this.iduser = iduser;
        this.clientAddress = clientAddress;
        this.clientCredit = clientCredit;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.username = username;
    }

    public static void addBalance(int idclient, float amount, Connection connection) {
        String query = "UPDATE client SET credit = credit + ? WHERE idclient = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setFloat(1, amount);
            preparedStatement.setInt(2, idclient);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public int getIdclient() {
        return idclient;
    }

    public int getIduser() {
        return iduser;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public float getClientCredit() {
        return clientCredit;
    }

    public void setClientCredit(float clientCredit) {
        this.clientCredit = clientCredit;
    }

    public JPanel createPanel() {
        JPanel panel = new JPanel(new GridLayout(5,2));
        panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));

        JLabel fullnameLabel1 = new JLabel("Full Name");
        JLabel usernameLabel1 = new JLabel("Username");
        JLabel phoneLabel1 = new JLabel("Phone");
        JLabel addressLabel1 = new JLabel("Address");
        JLabel balanceLabel1 = new JLabel("Balance");
        JLabel fullnameLabel2 = new JLabel(this.getClientName());
        JLabel usernameLabel2 = new JLabel(this.getUsername());
        JLabel phoneLabel2 = new JLabel(this.getClientPhone());
        JLabel addressLabel2 = new JLabel(this.getClientAddress());
        JLabel balanceLabel2 = new JLabel(String.valueOf(this.getClientCredit()));

        fullnameLabel1.setForeground(Color.BLUE);
        usernameLabel1.setForeground(Color.BLUE);
        phoneLabel1.setForeground(Color.BLUE);
        addressLabel1.setForeground(Color.BLUE);
        balanceLabel1.setForeground(Color.BLUE);

        panel.add(fullnameLabel1);
        panel.add(fullnameLabel2);
        panel.add(usernameLabel1);
        panel.add(usernameLabel2);
        panel.add(phoneLabel1);
        panel.add(phoneLabel2);
        panel.add(addressLabel1);
        panel.add(addressLabel2);
        panel.add(balanceLabel1);
        panel.add(balanceLabel2);

        return panel;
    }
}
