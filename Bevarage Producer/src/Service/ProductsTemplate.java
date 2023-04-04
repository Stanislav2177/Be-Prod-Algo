package Service;

public class ProductsTemplate {

    //TODO: Da popitam po meila dali moga da vklu4a baza danni

    //this is template class which will store default arguments for products


    public Product createDefaultProductA(){
        Product product = new Product();
        product.setName("A");
        product.setUnitCost(0.52);
        product.setMarkup(80);
        product.setPromotion("none");

        return product;
    }

    public Product createDefaultProductB(){
        Product product = new Product();
        product.setName("B");
        product.setUnitCost(0.38);
        product.setMarkup(120);
        product.setPromotion("30");

        return product;
    }

    public Product createDefaultProductC(){
        Product product = new Product();

        product.setName("C");
        product.setUnitCost(0.52);
        product.setMarkup(80);
        product.setPromotion("none");

        return product;
    }

//    public Product createDefaultProductD(){
//
//    }






}
