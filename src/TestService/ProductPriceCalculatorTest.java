package TestService;

import Service.Client;
import Service.Product;
import Service.ProductPriceCalculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductPriceCalculatorTest {

    //TODO: MAKE PRODUCT TO BE AVAILABLE OUTSIDE THE METHOD SCOPE

    @Test
    void testGetProductWithoutMarkup() {
        // Arrange
        Product product = new Product("Product A", 0.50, 10000, "80", "none");
        ProductPriceCalculator calculator = new ProductPriceCalculator();

        // Act
        double actualResult = calculator.getProductWithoutMarkup(product);

        // Assert
        assertEquals(5000, actualResult);
    }

    @Test
    void testGetProductWithMarkup() {
        // Arrange
        Product product = new Product("Product A", 0.50, 10000, "80", "none");
        ProductPriceCalculator calculator = new ProductPriceCalculator();

        // Act
        double actualResult = calculator.getProductWithMarkup(product);

        // Assert
        assertEquals(9000, actualResult);
    }

    @Test
    void test1GetProductPriceWithFixedMarkup(){
        Product product = new Product("Product A", 0.50, 10000, "0.9 EUR/unit");
        ProductPriceCalculator calculator = new ProductPriceCalculator();

        double actualResult = calculator.getProductPriceWithFixedMarkup(product);

        assertEquals(9000, actualResult);
    }


    @Test
    void test2GetProductPriceWithFixedMarkup(){
        Product product = new Product("Product A", 0.50, 10000, "100%");
        ProductPriceCalculator calculator = new ProductPriceCalculator();

        double actualResult = calculator.getProductWithMarkup(product);

        assertEquals(10000, actualResult);
    }

    @Test
    void test1GetProductFreeItems(){
        Product product = new Product("Product A", 0.50, 99999, "0.9 EUR/unit");
        ProductPriceCalculator calculator = new ProductPriceCalculator();

        int productFreeItems = calculator.getProductFreeItems(product, 1, 2);

        assertEquals(33333, productFreeItems);
    }

    @Test
    void test2GetProductFreeItems(){
        Product product = new Product("Product A", 0.50, 97957, "0.9 EUR/unit");
        ProductPriceCalculator calculator = new ProductPriceCalculator();

        int productFreeItems = calculator.getProductFreeItems(product, 1, 2);

        assertEquals(32654, productFreeItems);
    }


    @Test
    void test1CheckMarkupType(){
        Product product = new Product("Product А", 0.05, 97957, "56765%");
        ProductPriceCalculator calculator = new ProductPriceCalculator();

        String type = calculator.markupType(product);

        assertEquals("percent", type);
    }

    @Test
    void test2CheckMarkupType(){
        Product product = new Product("Product А", 0.05, 97957, "541.24 EUR/unit");
        ProductPriceCalculator calculator = new ProductPriceCalculator();

        String type = calculator.markupType(product);

        assertEquals("fixed", type);
    }

    @Test
    void testGetFinalPrice(){
        Product product = new Product("Product А", 2.50, 10000, "100%", "Buy 2, get 1 for free");
        ProductPriceCalculator calculator = new ProductPriceCalculator();

        Client client = new Client(1);

        //TODO:MAIN METHOD, THIS TEST IS ONLY FOR LOCAL TEST, NOT FINAL ONE

        double finalPrice = calculator.getFinalPrice(product, client);

        assertEquals(46550.0, finalPrice);
    }

    @Test
    void testGetPromotionType(){
        Product product = new Product("Product А", 0.50, 10000, "100%", "none");
        ProductPriceCalculator calculator = new ProductPriceCalculator();

        String promotionType = calculator.promotionType(product);

        assertEquals("none", promotionType);

        product.setPromotion("30% off");

        promotionType = calculator.promotionType(product);

        assertEquals("percent", promotionType);

        product.setPromotion("Buy 5, get 11 for free");

        promotionType = calculator.promotionType(product);

        assertEquals("BuyXGetY", promotionType);
    }

    @Test
    void testToCheckCorrectness(){
        Product productA = new Product("A", 0.52, 10000, "80%", "none");
        Product productC = new Product("C", 0.41, 20000, "0.9 EUR/unit", "none");

        Client client = new Client(5);

        ProductPriceCalculator calculator = new ProductPriceCalculator();

        double priceA = calculator.getFinalPrice(productA, client);
        double priceB = calculator.getFinalPrice(productC, client);

        double total = priceA + priceB;

        System.out.println("before basic = " + total);

        double clientDiscount = calculator.basicClientDiscount(client);
        total = calculator.applyBasicClientDiscount(total, clientDiscount);

        System.out.println("after basic = " + total);

        clientDiscount = calculator.additionalVolumeDiscount(client, total);
        total = calculator.applyAdditionalVolumeDiscount(total, clientDiscount);

        System.out.println("after additional " + total);

        assertEquals(33070.80, total);
    }



}