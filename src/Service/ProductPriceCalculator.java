package Service;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductPriceCalculator {
    private Product product;
    private Client client;

    //TODO : AFTER FINISHING THE APPLICATION AND UPLOAD IT IN GITHUB, MAKE A VERSION WHICH SEPARATE METHODS,
    //TODO: FOR EXAMPLE, THE METHODS WHICH RETURN TYPE OF SOMETHING, MAKE IT IN ANOTHER CLASS


    //TODO: ------------------


    //TODO: --------------

    public double getFinalPrice(Product product, Client client){
        //main method which combines all sub-methods to calculate final price

        //TODO: MAKE IT A BIT SMALLER

        double price = 0;

        //get the total price only with markup

        String markupType = markupType(product);

        System.out.println(product.toString());


        if(markupType.equals("percent")){
            price = getProductWithMarkup(product);
        }else if (markupType.equals("fixed")){
            price = getProductPriceWithFixedMarkup(product);
        }

        System.out.println("Price after markup = " + price);

        //calculating the price depending on product promotion

        String promotionType = promotionType(product);
        String promotion;

        if(promotionType.equals("percent")){
            promotion = product.getPromotion();
            int number = Integer.parseInt(promotion.replaceAll("[^0-9]", ""));

            price = productPromotionWithMarkup(product, number);
        } else if (promotionType.equals("BuyXGetY")) {
            //for this statement, all free items will be added to product quantity

            promotion = product.getPromotion();

            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(promotion);

            int[] numbers = new int[2];
            int i = 0;

            while (matcher.find()) {
                numbers[i] = Integer.parseInt(matcher.group());
                i++;
            }

            int freeItems = getProductFreeItems(product, numbers[0], numbers[1]);
        }

        System.out.println("Price after promotion = " + price + " and product quantity " + product.getQuantity());


//
//        //Applying the basic client discount
//
//        double clientDiscount = basicClientDiscount(client);
//        price = applyBasicClientDiscount(price, clientDiscount);
//
//        System.out.println("Price after basic client discount = " + price);
//
//        //applying the additional volume discount
//
//
//        clientDiscount = additionalVolumeDiscount(client, price);
//        price = applyAdditionalVolumeDiscount(price, clientDiscount);
//
//        System.out.println("Price after additional volume discount = " + price);
//
//        System.out.println();


        return price;


    }

    public double applyAdditionalVolumeDiscount(double price, double clientDiscount) {
        return price - price*clientDiscount/100;
    }

    public double additionalVolumeDiscount(Client client, double price) {
        if(price >=10000 & price <= 30000){
            return getDiscountAbove10000(client);
        } else if (price > 30000) {
            return getDiscountAbove30000(client);
        }
        return 0; // if price is below 10000
    }

    public String promotionType(Product product) {
        //use the same logic as markupType method, return
        //the type of promotion

        String promotionType = product.getPromotion();

        StringTokenizer stringTokenizer = new StringTokenizer(promotionType);



        if(stringTokenizer.nextToken().equals("none")){
            return "none";
        }else if(stringTokenizer.nextToken().contains("off")){
            return "percent";
        }else {
            return "BuyXGetY";
        }
    }

    public String markupType(Product product) {
        //with this method, correct type of markup is returned
        //percent -> 50% or fixed markup -> 1.5 EUR/unit and then
        //algorithm make decision which method to use for calculating the
        //price after markup

        String markupType = product.getMarkup();

        StringTokenizer stringTokenizer = new StringTokenizer(markupType);

        //using the length of the markup, so the percent to not be capped only at 2
        //before the %

        String percent;

        try{
            int lengthOfMarkup = markupType.length();
            percent = String.valueOf(stringTokenizer.nextToken().charAt(lengthOfMarkup - 1));
            if(percent.equals("%")){
                return "percent";
            }
        }catch (StringIndexOutOfBoundsException e){
            if(stringTokenizer.nextToken().equals("EUR/unit")){
                return "fixed";
            }
        }
        return null;
    }

    public double getProductWithoutMarkup(Product product){

        System.out.println();
        return product.getQuantity()*product.getUnitCost();
    }

    public double getProductWithMarkup(Product product){
        double priceWithoutMarkup = getProductWithoutMarkup(product);

        double markup = checkMarkup(product);

        return priceWithoutMarkup + priceWithoutMarkup*markup/100;

    }

    public double getProductPriceWithFixedMarkup(Product product){
        double markup = getProductWithoutMarkup(product);

        System.out.println("in ------- price with markup " + markup);

        double fixedMarkup = checkMarkup(product);

        System.out.println("in --------- fixedMarkup " + fixedMarkup);
        return  markup + product.getQuantity()*fixedMarkup;
    }


    public double basicClientDiscount(Client client) {
        double[] discounts = {5, 4, 3, 2, 0};
        int index = client.getId() - 1; // subtract 1 to convert ID to index in array
        if (index >= 0 && index < discounts.length) {
            return discounts[index];
        }
        return 0; // default discount if client ID is out of range
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


    public int getDiscountAbove30000(Client client) {
        int[] discounts = {2, 2, 3, 5, 7};
        int index = client.getId() - 1; // subtract 1 to convert ID to index in array
        if (index >= 0 && index < discounts.length) {
            return discounts[index];
        }
        return 1; // default discount if client ID is out of range
    }

    public int getDiscountAbove10000(Client client){
        int[] discounts = {0, 1, 1, 3, 5};
        int index = client.getId() - 1; // subtract 1 to convert ID to index in array
        if (index >= 0 && index < discounts.length) {
            return discounts[index];
        }
        return 1; // default discount if client ID is out of range
    }

    private double checkMarkup(Product product){
        String markup = product.getMarkup();
        StringTokenizer stringTokenizer = new StringTokenizer(markup);

        String s = stringTokenizer.nextToken();

        //quick statement to check if the last char in the markup is percent
        //for example, if the markup is 100%, instance of stringBuilder would be
        //made and the last char (%) will be removed, test made for this case,
        //test name: test2GetProductPriceWithFixedMarkup


        //TODO: use markupType method

        int markupLength = s.length();
        String checkIfItsPercent = String.valueOf(s.charAt(markupLength-1));

        if(checkIfItsPercent.equals("%")){
            StringBuilder stringBuilder = new StringBuilder(s);
            stringBuilder.delete(markupLength-1, markupLength);
            return Double.parseDouble(stringBuilder.toString());
        }
        return Double.parseDouble(s);
    }

    public double applyBasicClientDiscount(double price, double clientDiscount) {
        return price - price*clientDiscount/100;
    }
}
