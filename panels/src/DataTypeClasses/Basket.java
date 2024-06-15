package DataTypeClasses;


import java.time.LocalDate;

public class Basket {
    private int idBasket;
    private int client;
    private LocalDate date;
    private float total;
    private boolean isProceeded;

    public Basket(int idBasket, int client, LocalDate date, float total, boolean isProceeded) {
        this.idBasket = idBasket;
        this.client = client;
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
