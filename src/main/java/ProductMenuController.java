import javax.swing.*;

public class ProductManagementController {

        public void start(){
            String userChoice = JOptionPane.showInputDialog(
                    this.getMenuItems());
            this.handleUserChoice(userChoice);
            // recursion
            this.start();


        // create
        // read
        // update
        // delete
    }

    private String getMenuItems() {
        return """
                Welcome to the ToDo App!
                1. Add To-Do item
                2. Display To-Do list
                3. View a single item
                4. Remove To-Do item
                5. Update To-Do item
                6. Exit
                """;
    }
}
