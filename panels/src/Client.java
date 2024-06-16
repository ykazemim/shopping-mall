public class Client {
    private final int idclient;
    private final int iduser;
    private String clientAddress;
    private float clientCredit;
    private String clientName;
    private String clientPhone;
    private String username;

    public Client(int idclient, int iduser ,String clientAddress, float clientCredit, String clientName, String clientPhone, String username) {
        this.idclient = idclient;
        this.iduser = iduser;
        this.clientAddress = clientAddress;
        this.clientCredit = clientCredit;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.username = username;
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
}
