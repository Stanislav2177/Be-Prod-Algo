package Service;

public class Product {
    private String name;
    private double unitCost;
    private int quantity;
    private double markup;
    private String promotion;
    private double discount;

    public Product(){

    }

    public Product(String name, double unitCost, int quantity, double markup, String promotion, double discount) {
        this.name = name;
        this.unitCost = unitCost;
        this.quantity = quantity;
        this.markup = markup;
        this.promotion = promotion;
        this.discount = discount;
    }

    public Product(String name, double unitCost, int quantity, double markup) {
        //Constructor for product which don't have promotion
        this.name = name;
        this.unitCost = unitCost;
        this.quantity = quantity;
        this.markup = markup;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(double unitCost) {
        this.unitCost = unitCost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getMarkup() {
        return markup;
    }

    public void setMarkup(double markup) {
        this.markup = markup;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
