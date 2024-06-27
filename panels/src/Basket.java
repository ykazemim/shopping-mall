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
}
