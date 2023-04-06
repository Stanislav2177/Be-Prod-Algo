package Service;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ProductPriceCalculator {
    public double getFinalPrice(Product product){
        //main method which combines all sub-methods to calculate the final price

        double price = 0;

        //get the total price only with markup
        String markupType = markupType(product);

        if(markupType.equals("percent")){
            price = getProductWithMarkup(product);
        }else if (markupType.equals("fixed")){
            price = getProductPriceWithFixedMarkup(product);
        }

        //calculating the price depending on product promotion
        String promotionType = promotionType(product);

        if(promotionType.equals("percent")){

            price = applyPromotion(product);
        } else if (promotionType.equals("BuyXGetY")) {
            //for this statement, all free items will be added to product quantity
            setFreeProductItems(product);
        }

        return price;
    }

    public double applyAllDiscounts(double price, Client client){
        //method which combine all sub methods related to discounts

        //applying the basic client discount
        double clientDiscount = basicClientDiscount(client);
        price = applyBasicClientDiscount(price, clientDiscount);

        //applying the additional volume discount
        clientDiscount = additionalVolumeDiscount(client, price);
        price = applyAdditionalVolumeDiscount(price, clientDiscount);

        return price;
    }

    public double applyBasicClientDiscount(double price, double clientDiscount) {
        return price - price*clientDiscount/100;
    }

    public double basicClientDiscount(Client client) {
        double[] discounts = {5, 4, 3, 2, 0};
        int index = client.getId() - 1; // subtract 1 to convert ID to index in array
        if (index >= 0 && index < discounts.length) {
            return discounts[index];
        }
        return 0; // default discount if client ID is out of range
    }

    public double applyAdditionalVolumeDiscount(double price, double clientDiscount) {
        return price - price*clientDiscount/100;
    }

    public double additionalVolumeDiscount(Client client, double price) {
        //Method who check the price and return discount percent
        //depending on client id
        if(price >=10000 & price <= 30000){
            return getDiscountAbove10000(client);
        } else if (price > 30000) {
            return getDiscountAbove30000(client);
        }
        return 0; // if price is below 10000
    }

    public void setFreeProductItems(Product product){
        //Method who use Regex to get only numbers in String
        //and then to setQuantity of product after receiving
        //the free items after applying the promotion
        String promotion = product.getPromotion();

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(promotion);

        int[] numbers = new int[2];
        int i = 0;

        while (matcher.find()) {
            numbers[i] = Integer.parseInt(matcher.group());
            i++;
        }

        int freeItems = getProductFreeItems(product, numbers[0], numbers[1]);
        product.setQuantity(product.getQuantity() + freeItems);

    }

    public String promotionType(Product product) {
        //use the same logic as markupType method,
        // return the type of promotion

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

    public double applyPromotion(Product product){
        //applying product promotion of type x%
        String promotionType = promotionType(product);

        StringBuilder stringBuilder = new StringBuilder();
        StringTokenizer stringTokenizer = new StringTokenizer(product.getPromotion());

        int number = 0;

        if(promotionType.equals("percent")){
            stringBuilder.append(stringTokenizer.nextToken());

            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(product.getPromotion());

            if (matcher.find()) {
                number = Integer.parseInt(matcher.group());
            }
        }

        return productPromotionWithMarkup(product, number);
    }

    public String markupType(Product product) {
        //with this method, correct type of markup is returned
        //percent -> 50% or fixed markup -> 1.5 EUR/unit

        String markupType = product.getMarkup();

        StringTokenizer stringTokenizer = new StringTokenizer(markupType);

        //using the length of the markup, so the percent to not be
        // capped only at 2 chars before the %

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
        return product.getQuantity()*product.getUnitCost();
    }

    public double getProductWithMarkup(Product product){
        double priceWithoutMarkup = getProductWithoutMarkup(product);

        double markup = checkMarkup(product);

        return priceWithoutMarkup + priceWithoutMarkup*markup/100;
    }

    public double getProductPriceWithFixedMarkup(Product product){
        double markup = getProductWithoutMarkup(product);

        double fixedMarkup = checkMarkup(product);

        return  markup + product.getQuantity()*fixedMarkup;
    }




    public int getProductFreeItems(Product product, int buyAmount, int freeAmount) {
        //quick calculations to check how many free products to be returned

        if(buyAmount == 1){
            return product.getQuantity()*freeAmount;
        }

        //get total amount
        int totalAmount = product.getQuantity();

        //delete it on buyAmount, for example
        //promotion is -> buy 3, get 1 free
        //100/3 = 33, then in return phase it would be
        //multiplied with freeAmount, or 1.
        totalAmount /= buyAmount;

        //checks if there have any left amounts
        //for example: 100 % 3 = 1, and if in any
        //scenario left amount is bigger than buyAmount
        //it would return +freeAmount
        if(totalAmount % buyAmount >= buyAmount){
            return totalAmount*freeAmount + freeAmount;
        }
        return totalAmount*freeAmount;
    }

    public double productPromotionWithFixedMarkup(Product product, double discount){
        //method which calculate the promotion if there is a markup which is fixed
        double priceWithFixedMarkup = getProductPriceWithFixedMarkup(product);

        return priceWithFixedMarkup - priceWithFixedMarkup*discount/100;
    }

    public double productPromotionWithMarkup(Product product, double discount){
        double priceWithMarkup = getProductWithMarkup(product);

        String promotionType = promotionType(product);

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
}
