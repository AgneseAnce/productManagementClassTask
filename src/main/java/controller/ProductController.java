package controller;

import dto.Category;
import dto.Product;
import service.ProductService;

import javax.swing.*;
import java.util.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductController {
    private final ProductService productService = new ProductService();

    // Add product
    public void addProduct() {
            try {
                Product product = this.collectProductData();
                this.productService.createProduct(product);
                this.displayMessage("Product created successfully");
            } catch (Exception e) {
                this.displayMessage(e.getMessage());
            }
    }

  // Collect data when adding or updating a product
    private Product collectProductData() {
        Product product = new Product();

        product.setName(this.getUserInput("Enter the name of the product: "));

        String selectedCategory = (String) this.getUserInputFromDropDown(
                Arrays.stream(Category.values()).
                        map(Category::name).toArray(),
                "Choose a Category",
                "Choose product category: "
        );

        product.setCategory(Category.valueOf(selectedCategory));
        product.setPrice(Double.parseDouble(this.getUserInput("Enter the price: ")));
        product.setQuantity(Integer.parseInt(this.getUserInput("Enter the quantity: ")));

        return product;
    }

    // View all products from HTTP-based web repository
    public void viewAllProduct() {
        try {
            StringBuilder productsB = new StringBuilder();

            for (Product product: this.productService.getAllProducts()){
                productsB.append(product.toString());
            }
            this.displayMessage(productsB.toString());
        } catch (Exception exception) {
            this.displayMessage(exception.getMessage());
        }
    }

    // Update an existing product
    public void updateProduct() {

        try {
            List<Product> existingProduct = this.productService.getAllProducts();
                    Product selectedProduct = (Product) this.getUserInputFromDropDown
                    (existingProduct.toArray(),
                            "Update product",
                            "Choose the product to update"
                    );

            Product product = this.collectProductData();
            this.productService.updateSingleProduct(product, selectedProduct.get_id());
            this.displayMessage("Product  updated successfully.");
        } catch (Exception e){
            this.displayMessage(e.getMessage());
        }
    }

    // Delete a product from HTTP-based web repository
    public void removeProduct() {
        try {
          List<Product> existingProduct = this.productService.getAllProducts();
            Product selectedProduct = (Product) this.getUserInputFromDropDown
                    (existingProduct.toArray(),
                            "Delete product",
                            "Choose the product to delete"
                    );
            this.productService.deleteProductService(selectedProduct.get_id());
            this.displayMessage("Product deleted successfully.");
        } catch (Exception exception) {
            this.displayMessage(exception.getMessage());
        }
    }

    public void sortProductsBy(){
        List<String> sortOptions = new ArrayList<>();
        sortOptions.add("Name");
        sortOptions.add("Quantity");
        sortOptions.add("Price");
        sortOptions.add("Category");

        List<String> sortOrder = new ArrayList<>();
        sortOrder.add("Ascending");
        sortOrder.add("Descending");

    try {
        // Generate a Product ArrayList from HTTP-based web repository
        List<Product> productList = new ArrayList<>(this.productService.getAllProducts());
        String parameter = (String) getUserInputFromDropDown(
               sortOptions.toArray(),
               "Sort by: ",
               "Select parameter to sort by");

        String order = (String) getUserInputFromDropDown(
            sortOrder.toArray(),
            "Select order: ",
            "Ascending (A-Z) or descending (Z-A)");
    Collections.sort(productList, new Comparator<Product>() {
                @Override
                public int compare(Product product1, Product product2) {
                    if(order.equals("Ascending")) {
                        switch (parameter) {
                            case "Price":
                                if (product1.getPrice() == product2.getPrice()) return 0;
                                return product1.getPrice() > product2.getPrice() ? 1 : -1;
                            case "Name":
                                return product1.getName().compareTo(product2.getName());
                            case "Quantity":
                                if (product1.getQuantity() == product2.getQuantity()) return 0;
                                return product1.getQuantity() > product2.getQuantity() ? 1 : -1;
                            case "Category":
                                return product1.getCategory().toString().compareTo(product2.getCategory().toString());
                        }
                    } else if (order.equals("Descending")){
                        // Code for custom sorting by parameter
                        switch (parameter) {
                            case "Price":
                                if (product1.getPrice() == product2.getPrice()) return 0;
                                return product2.getPrice() > product1.getPrice() ? 1 : -1;
                            case "Name":
                                return product2.getName().compareTo(product1.getName());
                            case "Quantity":
                                if (product1.getQuantity() == product2.getQuantity()) return 0;
                                return product2.getQuantity() > product1.getQuantity() ? 1 : -1;
                            case "Category":
                                return product2.getCategory().toString().compareTo(product1.getCategory().toString());
                        }
                    }
                    return 0;
                }
        });

        List<Product> sortedList = new ArrayList<>(productList);
        StringBuilder stringBuilder = new StringBuilder();
        sortedList.forEach(product -> stringBuilder.append(product).append("\n"));
//        System.out.println(stringBuilder.toString());
        displayMessage(stringBuilder.toString());

    } catch (Exception exception) {
        this.displayMessage(exception.getMessage());
    }
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
}
