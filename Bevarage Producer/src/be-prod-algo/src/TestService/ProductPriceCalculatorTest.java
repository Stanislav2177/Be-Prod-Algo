package TestService;

import Service.Client;
import Service.Product;
import Service.ProductPriceCalculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductPriceCalculatorTest {
    ProductPriceCalculator calculator = new ProductPriceCalculator();

    @Test
    void testGetProductWithoutMarkup() {
        Product product = new Product("Product A", 0.50, 10000, "80", "none");
        ProductPriceCalculator calculator = new ProductPriceCalculator();

        double actualResult = calculator.getProductWithoutMarkup(product);

        assertEquals(5000, actualResult);
    }

    @Test
    void testGetProductWithMarkup() {
        Product product = new Product("Product A", 0.50, 10000, "80", "none");
        ProductPriceCalculator calculator = new ProductPriceCalculator();

        double actualResult = calculator.getProductWithMarkup(product);

        assertEquals(9000, actualResult);
    }

    @Test
    void test1GetProductPriceWithFixedMarkup(){
        Product product = new Product("Product A", 0.50, 10000, "0.9 EUR/unit");
        ProductPriceCalculator calculator = new ProductPriceCalculator();

        double actualResult = calculator.getProductPriceWithFixedMarkup(product);

        assertEquals(14000, actualResult);
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
        Product product = new Product("Product A", 0.50, 100000, "0.9 EUR/unit", "buy 1, get 2 for free");
        ProductPriceCalculator calculator = new ProductPriceCalculator();

        int productFreeItems = calculator.getProductFreeItems(product, 1, 2);

        assertEquals(200000, productFreeItems);
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


        double finalPrice = calculator.getFinalPrice(product);

        finalPrice = calculator.applyAllDiscounts(finalPrice, client);

        assertEquals(46550.0, finalPrice);
        assertEquals(15000, product.getQuantity());
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

        double priceA = calculator.getFinalPrice(productA);
        double priceB = calculator.getFinalPrice(productC);

        double total = priceA + priceB;

        total = calculator.applyAllDiscounts(total, client);

        assertEquals(33070.80, total);
    }

    @Test
    void testProductPromotion(){
        Product productA = new Product("A", 0.52, 10000, "80%", "30% off");

        double priceWithoutMark = calculator.getProductWithoutMarkup(productA);
        double priceWithMark = calculator.getProductWithMarkup(productA);

        double priceWithPromotion = calculator.applyPromotion(productA);


        assertEquals(5200, priceWithoutMark);
        assertEquals(9360, priceWithMark);
        assertEquals(6552, priceWithPromotion);
    }



        @Test
        public void testGetFinalPriceWithPercentMarkupAndNoPromotion() {
            // create a product and a client
            Product product = new Product("Product A", 10, 100, "50%", "none");
            Client client = new Client(1);

            // calculate the final price
            ProductPriceCalculator calculator = new ProductPriceCalculator();
            double finalPrice = calculator.getFinalPrice(product);

            // verify the result
            assertEquals(1500.0, finalPrice);
        }

        @Test
        public void testGetFinalPriceWithBuyXGetYPromotion() {
            Product product = new Product("Product A", 10, 100, "50%", "buy 3 get 1 free");
            Client client = new Client(1);

            ProductPriceCalculator calculator = new ProductPriceCalculator();
            int freeItems = calculator.getProductFreeItems(product,3 ,1);
            double finalPrice = calculator.getFinalPrice(product);
            
            assertEquals(33, freeItems);
            assertEquals(1500.0, finalPrice);

        }
}