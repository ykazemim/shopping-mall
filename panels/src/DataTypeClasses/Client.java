package DataTypeClasses;

public class Client {
    private final int idclient;
    private final int iduser;
    private String clientAddress;
    private float clientCredit;
    public Client(int idclient, int iduser ,String clientAddress, float clientCredit) {
        this.idclient = idclient;
        this.iduser = iduser;
        this.clientAddress = clientAddress;
        this.clientCredit = clientCredit;
    }

    public int getIdclient() {
        return idclient;
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
