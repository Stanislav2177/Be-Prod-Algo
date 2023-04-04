import Service.Client;
import Service.ProductPriceCalculator;
import Service.Product;
import Service.ProductsTemplate;

public class Main {
    public static void main(String[] args) {
        ProductPriceCalculator orderCalculator = new ProductPriceCalculator();
        Product productA = new Product("A", 0.50, 10000, "80", "none", 10);
        Client client5 = new Client(5);

        Product productC = new Product("A", 0.60, 20000, "1", "none", 10);


    }


}