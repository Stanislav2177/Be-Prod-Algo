package Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ProductMenu {
    Client client = new Client();
    ProductPriceCalculator calculator = new ProductPriceCalculator();
    DecimalFormat df = new DecimalFormat("#.##");


    public  void start() {
        System.out.println("Welcome to Beverage Producer\n");

        List<Product> listWithProducts = createTemplatesAndSetQuantities();

        System.out.println("Client ID: " + client.getId() +"\n");

        for (Product product : listWithProducts) {
            if(product.getQuantity() != 0){
                displayProductInformation(product);
            }
        }

        double totalPriceWithoutDiscount = calculateTotalPriceForAllProducts(listWithProducts);

        System.out.println("Total price without discounts " + df.format(totalPriceWithoutDiscount));

        System.out.println("Client basic discount: " + calculator.basicClientDiscount(client));



        System.out.println("Client addition volume discount: "
                + df.format(calculator.additionalVolumeDiscount(client, totalPriceWithoutDiscount)));

        System.out.println("Price with applied discounts " + df.format(calculator.applyAllDiscounts(totalPriceWithoutDiscount, client)));
    }

    private void displayProductInformation(Product product) {
        System.out.println(product.toString());

        double price =  calculator.getProductWithoutMarkup(product);

        System.out.println("Price without markup: " + df.format(price));

        price = calculator.getProductWithMarkup(product);

        System.out.println("Price with markup: " + df.format(price));

        String promotionType = calculator.promotionType(product);
        if(promotionType.equals("percent")) {
            price = calculator.applyPromotion(product);
            System.out.println("Product promotion (" + product.getPromotion() +") price: " + df.format(price));

        }else if(promotionType.equals("BuyXGetY")){
            calculator.setFreeProductItems(product);

            System.out.println("Product promotion ("+ product.getPromotion() +"), quantity: " +
                    product.getQuantity());
        }else {
            System.out.println("Product promotion: none");
        }
        System.out.println();
    }



    private double calculateTotalPriceForAllProducts(List<Product> listWithProducts) {
        Product product = new Product();

        double totalPrice = 0;

        for (int i = 0; i < 4; i++) {
            product = listWithProducts.get(i);

            if (product.getQuantity() != 0) {
                //this is the total price without basic client discount and
                //additional volume discount
                totalPrice += calculator.getFinalPrice(product);
            }
        }

        return totalPrice;
    }


    private List<Product> createTemplatesAndSetQuantities() {
        int[] quantities = input();
        ProductsTemplate template = new ProductsTemplate();

        List<Product> productList = template.createTemplates();

        client.setId(quantities[0]);

        for (int i = 1; i < 5; i++) {
            productList.get(i-1).setQuantity(quantities[i]);
        }

        return productList;
    }


    private int[] input(){
        //Receive the input from the console and return it as array
        //then it distributed by its needs
        int[] quantities = new int[5];
        try {
            System.out.print("Enter your request: ");
            Scanner scanner = new Scanner(System.in);

            String query = scanner.nextLine();
            StringTokenizer stringTokenizer = new StringTokenizer(query);

            for (int i = 0; stringTokenizer.hasMoreTokens(); i++) {
                quantities[i] = Integer.parseInt(stringTokenizer.nextToken());
            }

            if(quantities[0] > 5){
                System.out.print("Client ID is too big for the requirements, enter new one:");
                quantities[0] = scanner.nextInt();
            }

        }catch (IndexOutOfBoundsException e){
            System.out.println("too many elements added in the input,try again");
            input();
        }

        return quantities;
    }
}
