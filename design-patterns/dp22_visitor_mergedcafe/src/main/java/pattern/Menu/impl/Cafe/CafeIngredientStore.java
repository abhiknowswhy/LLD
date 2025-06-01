package pattern.Menu.impl.Cafe;

import java.util.Map;
import java.util.HashMap;
import pattern.Menu.Ingredients.Ingredient;
import pattern.Menu.Ingredients.Nutrition;

public class CafeIngredientStore {
    private static final Map<String, Ingredient> INGREDIENTS = new HashMap<>();
    
    static {
        initializeIngredients();
    }
    
    private static void initializeIngredients() {
        // Burger ingredients
        INGREDIENTS.put("bun.wheat", new Ingredient("Wheat Bun", false, true, "local",
            new Nutrition(265, 9, 49, 3, Nutrition.DietaryType.VEGAN)));
        INGREDIENTS.put("patty.veggie", new Ingredient("Veggie Patty", false, true, "imported",
            new Nutrition(240, 21, 28, 8, Nutrition.DietaryType.VEGAN)));
        INGREDIENTS.put("lettuce", new Ingredient("Lettuce", false, true, "local",
            new Nutrition(15, 1.5, 2.9, 0.2, Nutrition.DietaryType.VEGAN)));
        INGREDIENTS.put("tomato", new Ingredient("Tomato", false, true, "local",
            new Nutrition(18, 0.9, 3.9, 0.2, Nutrition.DietaryType.VEGAN)));
        INGREDIENTS.put("fries.air", new Ingredient("Air Fries", false, true, "local",
            new Nutrition(280, 3, 52, 6, Nutrition.DietaryType.VEGAN)));

        // Soup ingredients
        INGREDIENTS.put("vegetables.seasonal", new Ingredient("Seasonal Vegetables", false, true, "local",
            new Nutrition(65, 3, 12, 0.5, Nutrition.DietaryType.VEGAN)));
        INGREDIENTS.put("stock.chicken", new Ingredient("Chicken Stock", false, false, "local",
            new Nutrition(10, 1.5, 1, 0.5, Nutrition.DietaryType.NON_VEG)));
        INGREDIENTS.put("greens.mixed", new Ingredient("Mixed Greens", false, true, "local",
            new Nutrition(17, 1.6, 3.3, 0.2, Nutrition.DietaryType.VEGAN)));
        INGREDIENTS.put("dressing.house", new Ingredient("House Dressing", false, true, "local",
            new Nutrition(120, 0.5, 3, 12, Nutrition.DietaryType.VEGAN)));
    }
    
    public static Ingredient getIngredient(String key) {
        Ingredient ingredient = INGREDIENTS.get(key);
        if (ingredient == null) {
            throw new IllegalArgumentException("Unknown ingredient: " + key);
        }
        return ingredient;
    }
}
