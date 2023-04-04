package TestService;

import Service.Client;
import Service.Product;
import Service.ProductPriceCalculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductPriceCalculatorTest {

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
    void testGetAdditionalDiscountsAbove30000EUR(){
        Product product = new Product("Product А", 0.50, 97957, "0.9 EUR/unit");
        Client client = new Client(4);

        ProductPriceCalculator calculator = new ProductPriceCalculator();

        double totalDiscount = calculator.getAdditionalDiscounts(product, client);

        assertEquals(7, totalDiscount);
    }

    @Test
    void testGetAdditionalDiscountsAbove10000EUR(){
        Product product = new Product("Product А", 0.13, 97957, "0.9 EUR/unit");
        Client client = new Client(2);

        ProductPriceCalculator calculator = new ProductPriceCalculator();

        double totalDiscount = calculator.getAdditionalDiscounts(product, client);

        assertEquals(5, totalDiscount);
    }

    @Test
    void testGetAdditionalDiscountsBelow10000EUR(){
        Product product = new Product("Product А", 0.05, 97957, "0.9 EUR/unit");
        Client client = new Client(2);

        ProductPriceCalculator calculator = new ProductPriceCalculator();

        double totalDiscount = calculator.getAdditionalDiscounts(product, client);

        assertEquals(4, totalDiscount);
    }



}