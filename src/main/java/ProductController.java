import javax.swing.*;
import java.sql.Array;
import java.util.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductController {

    private List<Product> products = new ArrayList<>();

    private final ProductService productService = new ProductService();
    public List<Product> addProduct() {

            try {
                Product product = this.collectProductData();
                this.productService.createProduct(product);
                this.products.add(product);
                this.displayMessage("Product created successfully");
            } catch (Exception e) {
                this.displayMessage(e.getMessage());
            }
            return products;

    }

//    private void reloadProducts() {
//        try {
//            this.products = this.contactRepository.getContacts();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private Product collectProductData() {
        Product product = new Product();


        product.setName(this.getUserInput("Enter the name of the product: "));

        String selectedCategory = (String) this.getUserInputFromDropDown( // use single method to ask user dropdown selection in multiple places
                Arrays.stream(Category.values()).
                        map(Category::name).toArray(), // Convert enum to list to array of strings
                "Choose Category",
                "Choose product category: "
        );

        product.setCategory(Category.valueOf(selectedCategory));
        product.setPrice(Double.parseDouble(this.getUserInput("Enter the price: ")));
        product.setQuantity(Integer.parseInt(this.getUserInput("Enter the quantity: ")));

//        this.products.add(product);

        return product;
    }



    private void displayMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
    private String getUserInput(String message) {
            return JOptionPane.showInputDialog(message);
    }

    private Object getUserInputFromDropDown(Object[] dropDownOptions,
                                            String title, String message){
        return JOptionPane.showInputDialog(
                null,
                message,
                title,
                JOptionPane.QUESTION_MESSAGE,
                null,
                dropDownOptions,
                dropDownOptions[0]
        );
    }

    public void viewAllProduct() {
        try {
            StringBuilder products = new StringBuilder();

            for (Product product: this.productService.getAllProducts()){
                products.append(product.toString());
            }
            this.displayMessage(products.toString());
        } catch (Exception exception) {
            this.displayMessage(exception.getMessage());
        }
    }

    public List<Product> updateProduct() {

        try {
            List<Product> existingProduct = this.productService.getAllProducts();
            // Casting
            Product selectedProduct = (Product) this.getUserInputFromDropDown
                    (existingProduct.toArray(),
                            "Update product",
                            "Choose the product to update"
                    );

            Product product = this.collectProductData();
            this.productService.updateSingleProduct(product, selectedProduct.get_id());
            this.products.add(product);
            this.products.remove(selectedProduct);
            this.displayMessage("Product  updated successfully.");

        } catch (Exception e){
            this.displayMessage(e.getMessage());
        }
        return products;
    }

    public void removeProduct() {

        try {
          List<Product> existingProduct = this.productService.getAllProducts();
            // Casting
            Product selectedProduct = (Product) this.getUserInputFromDropDown
                    (existingProduct.toArray(),
                            "Delete product",
                            "Choose the product to delete"
                    );

            this.productService.deleteProductService(selectedProduct.get_id());
            this.products.remove(selectedProduct);
            this.displayMessage("Product deleted successfully.");

        } catch (Exception exception) {
            this.displayMessage(exception.getMessage());

        }
    }

//    private Product getProductDataByDetail(String detail){
//
//    }

    public void sortProductsBy(){
List<String> sortOptions = new ArrayList<>();
sortOptions.add("Name");
sortOptions.add("Quantity");
sortOptions.add("Price");

       String userChoice = (String) getUserInputFromDropDown(
               sortOptions.toArray(),
               "Sort by: ",
               "Select parameter to sort by");

//       products.forEach(System.out::println);

        products.forEach(currentProduct -> {
            // Do some manipulations with data
            System.out.println(currentProduct);
        });
        Collections.sort(products, new Comparator<Product>() {
                    @Override
                    public int compare(Product product1, Product product2) {
                        if (userChoice.equals("Price")) {

                            if (product1.getPrice() == product2.getPrice()) return 0;
                            return product1.getPrice() > product2.getPrice() ? -1 : 1;
                        } else if (userChoice.equals("Name")) {
//                            if (product1.getName() == product2.getName()) return 0;
                            return product1.getName().compareTo(product2.getName());
                        } else if (userChoice.equals("Quantity")) {
                            if (product1.getQuantity() == product2.getQuantity()) return 0;
                            return product1.getQuantity() > product2.getQuantity() ? -1 : 1;
                        }
                        return 0;
                    }
                }
        );

        StringBuilder stringBuilder = new StringBuilder();
        products.forEach(product -> stringBuilder.append(product).append("\n"));
        System.out.println(stringBuilder.toString());
        displayMessage(stringBuilder.toString());

//        Collections.reverse(products);
//        products.forEach(currentProduct -> System.out.println(currentProduct + " EUR"));

    }

    public void filterProducts(){
//
    }
}
