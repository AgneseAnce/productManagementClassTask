import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Product {

    private String _id;
    private String name;
    private Category category;
    private double price;
    private double quantity;

    @Override
    public String toString() {
        return _id +  ": " +
                name + ", " +
                category + ", " +
                price + ", " +
                quantity + "\n";
    }
}
