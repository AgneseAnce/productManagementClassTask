import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class ProductController {

    private final ProductService productService = new ProductService();
    public void addProduct() {

            try {
                Product product = this.collectProductData();
                this.productService.createProduct(product);
                this.displayMessage("Product created successfully");
            } catch (Exception e) {
                this.displayMessage(e.getMessage());
            }
    }

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
        product.setQuantity(Double.parseDouble(this.getUserInput("Enter the quantity: ")));

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
            StringBuilder todoItemsAsString = new StringBuilder();

            for (Product product: this.productService.getAllProducts()){
                todoItemsAsString.append(product.toString());
            }
            this.displayMessage(todoItemsAsString.toString());
        } catch (Exception exception) {
            this.displayMessage(exception.getMessage());
        }
    }

    public void updateProduct() {

        try {
            List<Product> existingToDoItem = this.productService.getAllProducts();
            // Casting
            Product selectedProduct = (Product) this.getUserInputFromDropDown
                    (existingToDoItem.toArray(),
                            "Update item",
                            "Choose the item to update"
                    );

            Product product = this.collectProductData();
            this.productService.updateSingleProduct(product, selectedProduct.get_id());
            this.displayMessage("ToDo item updated successfully.");

        } catch (Exception e){
            this.displayMessage(e.getMessage());
        }
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
            this.displayMessage("Product deleted successfully.");

        } catch (Exception exception) {
            this.displayMessage(exception.getMessage());

        }
    }
}
