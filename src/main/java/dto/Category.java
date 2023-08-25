package dto;

public enum Category {
    FOOD,
    ELECTRONICS,
    BEVERAGES,
    CLOTHING,
    SHOES,
    FURNITURE,
    OTHER;

    public String toString() {
        switch (this) {
            case FOOD:
                return "C: Food";
            case ELECTRONICS:
                return "C: Electronics";
            case BEVERAGES:
                return "C: Beverages";
            case CLOTHING:
                return "C: Clothing";
            case SHOES:
                return "C: Shoes";
            case FURNITURE:
                return "C: Furniture";
            case OTHER:
                return "C: Other";
            default:
                return null;
        }
    }
}
