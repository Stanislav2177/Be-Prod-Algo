import Service.Client;
import Service.ProductPriceCalculator;
import Service.Product;
import Service.ProductsTemplate;

public class Main {
    public static void main(String[] args) {
        ProductPriceCalculator orderCalculator = new ProductPriceCalculator();
        Product productA = new Product("A", 0.50, 10000, 80, "none", 10);
        Client client5 = new Client(5);

        Product productC = new Product("A", 0.60, 20000, 1, "none", 10);

        double v = orderCalculator.getProductWithoutMarkup(productA);

        System.out.println("Product A without markup = " + v);

        double v1 = orderCalculator.getProductWithMarkup(productA);

        System.out.println("Product A with markup = " + v1);



//        System.out.println(v1);
//
//        double v2 = orderCalculator.additionalVolumeDiscount(productA, client5);
//        double v3 = orderCalculator.additionalVolumeDiscount(productC, client5);
//
//        System.out.println(v2+v3);
//        System.out.println(orderCalculator.productWithFixedMarkup(productC));


        System.out.println("Free products = " + orderCalculator.getProductFreeItems(productA, 5, 5));


        System.out.println("the price with discount would be " + orderCalculator.productPromotionWithMarkup(productA, productA.getDiscount()));


        ProductsTemplate productsTemplate = new ProductsTemplate();

        Product defaultProductA = productsTemplate.createDefaultProductA();

        System.out.println(defaultProductA.getName());
    }


}