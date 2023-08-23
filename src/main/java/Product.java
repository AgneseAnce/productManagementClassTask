import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Product {

    private String _id;
    private String name;
    private Category category;
    private double price;
    private int quantity;

    @Override
    public String toString() {
        return
                name + ", " +
                category + ", " +
                price + ", " +
                quantity + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 && quantity == product.quantity && Objects.equals(_id, product._id) && Objects.equals(name, product.name) && category == product.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, name, category, price, quantity);
    }

}
