package DataTypeClasses;

public class Product {
    private int idProduct;
    private String title;
    private float price;
    private String pathToImage;
    private int stock;
    private int ratingCount;
    private float averageRating;
    private boolean availableForClient;
    private int clientRating;
    private int stockInBasket;

    public Product(int idProduct, String title, float price, String pathToImage, int stock, int ratingCount, float averageRating, boolean availableForClient, int clientRating, int stockInBasket) {
        this.idProduct = idProduct;
        this.title = title;
        this.price = price;
        this.pathToImage = pathToImage;
        this.stock = stock;
        this.ratingCount = ratingCount;
        this.averageRating = averageRating;
        this.availableForClient = availableForClient;
        this.clientRating = clientRating;
        this.stockInBasket = stockInBasket;
    }

    public int getStockInBasket() {
        return stockInBasket;
    }

    public void setStockInBasket(int stockInBasket) {
        this.stockInBasket = stockInBasket;
    }

    public int getClientRating() {
        return clientRating;
    }

    public void setClientRating(int clientRating) {
        this.clientRating = clientRating;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPathToImage() {
        return pathToImage;
    }

    public void setPathToImage(String pathToImage) {
        this.pathToImage = pathToImage;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public boolean isAvailableForClient() {
        return availableForClient;
    }

    public void setAvailableForClient(boolean availableForClient) {
        this.availableForClient = availableForClient;
    }
}
