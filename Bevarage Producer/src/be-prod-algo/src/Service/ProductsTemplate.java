package Service;

import java.util.ArrayList;
import java.util.List;

public class ProductsTemplate {

    //TODO: Da popitam po meila dali moga da vklu4a baza danni

    //this is template class which will store default arguments for products

    public List<Product> createTemplates(){
        List<Product> list = new ArrayList<>();

        Product productA = createDefaultProductA();
        list.add(productA);

        Product productB = createDefaultProductB();
        list.add(productB);

        Product productC = createDefaultProductC();
        list.add(productC);

        Product productD = createDefaultProductD();
        list.add(productD);

        return list;
    }

    private Product createDefaultProductA(){
        Product product = new Product();
        product.setName("A");
        product.setUnitCost(0.52);
        product.setMarkup("80%");
        product.setPromotion("none");

        return product;
    }

    private Product createDefaultProductB(){
        Product product = new Product();
        product.setName("B");
        product.setUnitCost(0.38);
        product.setMarkup("120%");
        product.setPromotion("30% off");

        return product;
    }

    private Product createDefaultProductC(){
        Product product = new Product();
        product.setName("C");
        product.setUnitCost(0.41);
        product.setMarkup("0.9 EUR/unit");
        product.setPromotion("none");

        return product;
    }

    private Product createDefaultProductD(){
        Product product = new Product();
        product.setName("D");
        product.setUnitCost(0.60);
        product.setMarkup("1 EUR/unit");
        product.setPromotion("Buy 2, get 1 free");

        return product;
    }
}
