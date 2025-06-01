package pattern.Menu.Ingredients;

public class Ingredient {
    private String name;
    private boolean isAllergenic;
    private boolean isOrganic;
    private String source; // e.g., "local", "imported"
    private Nutrition nutritionPer100g;

    public Ingredient(String name, boolean isAllergenic, boolean isOrganic, String source, Nutrition nutritionPer100g) {
        this.name = name;
        this.isAllergenic = isAllergenic;
        this.isOrganic = isOrganic;
        this.source = source;
        this.nutritionPer100g = nutritionPer100g;
    }

    public String getName() {
        return name;
    }

    public boolean isAllergenic() {
        return isAllergenic;
    }

    public boolean isOrganic() {
        return isOrganic;
    }

    public String getSource() {
        return source;
    }

    public Nutrition getNutrition() {
        return nutritionPer100g;
    }
}
