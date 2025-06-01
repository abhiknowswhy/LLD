package pattern.Menu.impl.PancakeHouse;

import java.util.Map;
import java.util.HashMap;
import pattern.Menu.Ingredients.Ingredient;
import pattern.Menu.Ingredients.Nutrition;

public class PancakeHouseIngredientStore {
    private static final Map<String, Ingredient> INGREDIENTS = new HashMap<>();
    
    static {
        initializeIngredients();
    }
    
    private static void initializeIngredients() {
        // Initialize pancake ingredients
        INGREDIENTS.put("pancakeMix.organic", new Ingredient("Pancake Mix", false, true, "local", 
            new Nutrition(364, 10, 76, 1, Nutrition.DietaryType.VEGAN)));
        INGREDIENTS.put("pancakeMix.regular", new Ingredient("Pancake Mix", false, false, "local",
            new Nutrition(360, 8, 78, 1, Nutrition.DietaryType.VEGAN)));
        INGREDIENTS.put("eggs.organic", new Ingredient("Eggs", true, true, "local",
            new Nutrition(155, 13, 1.1, 11, Nutrition.DietaryType.EGGETARIAN)));
        INGREDIENTS.put("eggs.regular", new Ingredient("Eggs", true, false, "local",
            new Nutrition(155, 13, 1.1, 11, Nutrition.DietaryType.EGGETARIAN)));
        INGREDIENTS.put("toast", new Ingredient("Toast", false, false, "local",
            new Nutrition(265, 9, 49, 3, Nutrition.DietaryType.VEGAN)));
        INGREDIENTS.put("sausage", new Ingredient("Sausage", false, false, "local",
            new Nutrition(301, 12, 2.3, 27, Nutrition.DietaryType.NON_VEG)));
    }
    
    public static Ingredient getIngredient(String key) {
        Ingredient ingredient = INGREDIENTS.get(key);
        if (ingredient == null) {
            throw new IllegalArgumentException("Unknown ingredient: " + key);
        }
        return ingredient;
    }
}
