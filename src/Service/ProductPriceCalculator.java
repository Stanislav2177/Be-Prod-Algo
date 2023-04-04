package Service;

import java.util.StringTokenizer;

public class ProductPriceCalculator {
    private Product product;
    private Client client;


    public double getFinalPrice(Product product, Client client){
        //main method which combines all sub-methods to calculate final price

        //get the total price only with markup
//        double price = chooseCorrectMarkup(product);



        return 0;
    }


    public double getProductWithoutMarkup(Product product){
        return product.getQuantity()*product.getUnitCost();
    }

    public double getProductWithMarkup(Product product){
        double priceWithoutMarkup = getProductWithoutMarkup(product);

        double markup = checkMarkup(product);


        return priceWithoutMarkup + priceWithoutMarkup*markup/100;

    }

    public double getProductPriceWithFixedMarkup(Product product){
        double fixedMarkup = checkMarkup(product);

        return product.getQuantity()*fixedMarkup;
    }


    private double basicClientDiscount(Client client) {
        double[] discounts = {5, 4, 3, 2, 0};
        int index = client.getId() - 1; // subtract 1 to convert ID to index in array
        if (index >= 0 && index < discounts.length) {
            return discounts[index];
        }
        return 0; // default discount if client ID is out of range
    }

    public double getAdditionalDiscounts(Product product, Client client){
        //this method implements the second table used in the file
        //Logic here is to return total discount from basic client discount
        //and additional volume discount and to be used in the main method

        double discount = basicClientDiscount(client);
        double priceWithMarkup = getProductWithMarkup(product);

        double idDiscount = 0;

        if(priceWithMarkup >= 10000 && priceWithMarkup <= 30000){
            idDiscount = getDiscountAbove10000(client);
        }else if(priceWithMarkup >= 30000) {
            idDiscount = getDiscountAbove30000(client);
        }

        return discount + idDiscount;
    }

    public int getProductFreeItems(Product product, int buyAmount, int getForFree) {
        //method which implement the logic "buy x, get y for free"

        int totalAmount = product.getQuantity();

        //logic to check how many products will be delivered for free
        int freeAmount = totalAmount / (buyAmount + getForFree);

        //checks if the remaining amount will be enough to give another free product
        int leftAmount = totalAmount % (buyAmount + getForFree);

        //check the statement if the leftover amount is equal to buy amount
        //and if it's, then will be added free product
        System.out.println("Left amount = " + leftAmount);

        if(leftAmount == buyAmount){
            freeAmount+=getForFree;
        }

        return freeAmount;
    }

    public double productPromotionWithFixedMarkup(Product product, double discount){
        //method which calculate the promotion if there is a markup which is fixed
        double priceWithFixedMarkup = getProductPriceWithFixedMarkup(product);

        return priceWithFixedMarkup - priceWithFixedMarkup*discount/100;
    }

    public double productPromotionWithMarkup(Product product, double discount){
        double priceWithMarkup = getProductWithMarkup(product);

        return priceWithMarkup - priceWithMarkup*discount/100;
    }


    private int getDiscountAbove30000(Client client) {
        int[] discounts = {2, 2, 3, 5, 7};
        int index = client.getId() - 1; // subtract 1 to convert ID to index in array
        if (index >= 0 && index < discounts.length) {
            return discounts[index];
        }
        return 1; // default discount if client ID is out of range
    }

    private int getDiscountAbove10000(Client client){
        int[] discounts = {0, 1, 1, 3, 5};
        int index = client.getId() - 1; // subtract 1 to convert ID to index in array
        if (index >= 0 && index < discounts.length) {
            return discounts[index];
        }
        return 1; // default discount if client ID is out of range
    }

//    private double chooseCorrectMarkup(Product product) {
//        String markupToCheck = checkMarkup(product);
//
//
//        //quick statement to check if the last char in the markup is percent
//        //for example, if the markup is 100%, instance of stringBuilder would be
//        //made and the last char (%) will be removed, test made for this case,
//        //test name: test2GetProductPriceWithFixedMarkup
//        int markupLength = markupToCheck.length();
//        String checkIfItsPercent = String.valueOf(markupToCheck.charAt(markupLength-1));
//
//        if(checkIfItsPercent.equals("%")){
//            StringBuilder stringBuilder = new StringBuilder(markupToCheck);
//            stringBuilder.delete(markupLength-1, markupLength);
//            return Double.parseDouble(stringBuilder.toString());
//        }
//
//
//
//
//    }

    private double checkMarkup(Product product){
        String markup = product.getMarkup();
        StringTokenizer stringTokenizer = new StringTokenizer(markup);

        String s = stringTokenizer.nextToken();

        //quick statement to check if the last char in the markup is percent
        //for example, if the markup is 100%, instance of stringBuilder would be
        //made and the last char (%) will be removed, test made for this case,
        //test name: test2GetProductPriceWithFixedMarkup
        int markupLength = s.length();
        String checkIfItsPercent = String.valueOf(s.charAt(markupLength-1));

        if(checkIfItsPercent.equals("%")){
            StringBuilder stringBuilder = new StringBuilder(s);
            stringBuilder.delete(markupLength-1, markupLength);
            return Double.parseDouble(stringBuilder.toString());
        }
        return Double.parseDouble(s);
    }
}
