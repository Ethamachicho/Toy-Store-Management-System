public class Toy {

    private String name;
    private boolean isDisney;
    private int stock;
    private double price;
    private boolean blackFriday;

    public Toy(String name, boolean isDisney, int stock, double price, boolean blackFriday) {
        this.name = name;
        this.isDisney = isDisney;
        this.stock = stock;
        this.price = price;
        this.blackFriday = blackFriday;
    }

    // Getters
    public String getName() { return name; }
    public boolean getIsDisney() { return isDisney; }
    public int getStock() { return stock; }
    public double getPrice() { return price; }
    public boolean getBlackFriday() { return blackFriday; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setIsDisney(boolean isDisney) { this.isDisney = isDisney; }
    public void setStock(int stock) { 
        if (stock >= 0) this.stock = stock; 
    }
    public void setPrice(double price) { 
        if (price >= 0) this.price = price; 
    }
    public void setBlackFriday(boolean blackFriday) { this.blackFriday = blackFriday; }

    // Setter that reduces stock but never below 0
    public void sell(int amount) {
        if (amount < 0) return;
        stock = Math.max(0, stock - amount);
    }

    // Reduce price by 20%
    public void applyTwentyPercentSale() {
        if (blackFriday) {
            price *= 0.8;
        }
    }

    // Reduce price by 40%
    public void applyFortyPercentSale() {
        if (blackFriday) {
            price *= 0.6;
        }
    }
}
