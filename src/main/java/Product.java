import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode

public class Product {

    private String _id;
    private String name;
    private Category category;
    private double price;
    private int quantity;

    @Override
    public String toString() {
        return
                name + " -- " +
                category + " -- " +
                price + " EUR -- " +
                "Qu.: " + quantity + "\n";
    }
}
