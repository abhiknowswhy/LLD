package pattern.Menu.impl.Diner;

import java.util.Map;
import java.util.HashMap;
import pattern.Menu.Ingredients.Ingredient;
import pattern.Menu.Ingredients.Nutrition;

public class DinerIngredientStore {
    private static final Map<String, Ingredient> INGREDIENTS = new HashMap<>();
    
    static {
        initializeIngredients();
    }
    
    private static void initializeIngredients() {
        // Initialize bread and proteins
        INGREDIENTS.put("bread.wheat", new Ingredient("Wheat Bread", false, true, "local",
            new Nutrition(265, 9, 49, 3, Nutrition.DietaryType.VEGAN)));
        INGREDIENTS.put("bacon.fake", new Ingredient("Fake Bacon", false, true, "imported",
            new Nutrition(336, 29, 11, 21, Nutrition.DietaryType.VEGAN)));
        INGREDIENTS.put("bacon.real", new Ingredient("Bacon", false, false, "local",
            new Nutrition(541, 37, 1.4, 42, Nutrition.DietaryType.NON_VEG)));
            
        // Initialize vegetables
        INGREDIENTS.put("lettuce", new Ingredient("Lettuce", false, true, "local",
            new Nutrition(15, 1.5, 2.9, 0.2, Nutrition.DietaryType.VEGAN)));
        INGREDIENTS.put("tomato", new Ingredient("Tomato", false, true, "local",
            new Nutrition(18, 0.9, 3.9, 0.2, Nutrition.DietaryType.VEGAN)));
            
        // Initialize dessert ingredients
        INGREDIENTS.put("flour", new Ingredient("Flour", false, true, "local",
            new Nutrition(364, 10, 76, 1, Nutrition.DietaryType.VEGAN)));
        INGREDIENTS.put("apples", new Ingredient("Apples", false, true, "local",
            new Nutrition(52, 0.3, 14, 0.2, Nutrition.DietaryType.VEGAN)));
        INGREDIENTS.put("ice.cream", new Ingredient("Ice Cream", true, false, "local",
            new Nutrition(207, 3.5, 24, 11, Nutrition.DietaryType.VEGETARIAN)));
        INGREDIENTS.put("sugar", new Ingredient("Sugar", false, true, "imported",
            new Nutrition(400, 0, 100, 0, Nutrition.DietaryType.VEGAN)));
    }
    
    public static Ingredient getIngredient(String key) {
        Ingredient ingredient = INGREDIENTS.get(key);
        if (ingredient == null) {
            throw new IllegalArgumentException("Unknown ingredient: " + key);
        }
        return ingredient;
    }
}
