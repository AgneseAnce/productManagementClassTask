package controller;

import controller.ProductController;

import javax.swing.*;

public class ProductMenuController {
    private final ProductController productController = new ProductController();
        public void start(){
            String userChoice = JOptionPane.showInputDialog(
                    this.getMenuItems());
            this.handleUserChoice(userChoice);
            this.start();
    }

    private void handleUserChoice(String userChoice) {
        if (userChoice != null) {
            int hashCode = userChoice.hashCode();

            switch (userChoice) {
                case "1":
                    // create
                    this.productController.addProduct();
                    break;
                case "2":
                    // View all
                    this.productController.viewAllProduct();
                    break;
                case "3":
                    // Update
                    this.productController.updateProduct();
                    break;
                case "4":
                    // Delete
                    this.productController.removeProduct();
                    break;
                case "5":
                    // Sort
                    this.productController.sortProductsBy();
                    break;
                case "6":
                    // Exit
                 System.exit(0);
                    break;
                default:
                    JOptionPane.showInputDialog(null,
                            "Please choose an option from the list");
            }
        }
    }

    private String getMenuItems() {
        return """
                Welcome to the Product Management App!
                1. Add a new product
                2. View all products
                3. Update a product
                4. Delete a product
                5. Sort/filter products
                6. Exit
                """;
    }
}
