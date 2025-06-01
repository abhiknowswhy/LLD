package pattern.Menu.Ingredients;

public class IngredientPortion {
    private Ingredient ingredient;
    private double weightInGrams;

    public IngredientPortion(Ingredient ingredient, double weightInGrams) {
        this.ingredient = ingredient;
        this.weightInGrams = weightInGrams;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public double getWeightInGrams() {
        return weightInGrams;
    }    
    
    public Nutrition getNutritionForPortion() {
        Nutrition baseNutrition = ingredient.getNutrition();
        double factor = weightInGrams / 100.0; // since base nutrition is per 100g
        return baseNutrition.scale(factor);
    }
}
