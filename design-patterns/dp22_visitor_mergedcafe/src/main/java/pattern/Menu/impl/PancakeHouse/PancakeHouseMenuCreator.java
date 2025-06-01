package pattern.Menu.impl.PancakeHouse;

import java.util.Arrays;
import java.util.List;
import pattern.Menu.Menu;
import pattern.Menu.MenuComponent;
import pattern.Menu.MenuItem;
import pattern.Menu.Ingredients.IngredientPortion;

public class PancakeHouseMenuCreator {
    public static MenuComponent createMenu() {
        MenuComponent pancakeHouseMenu = new Menu("PANCAKE HOUSE MENU", "Breakfast");
        
        // Create K&B's Pancake Breakfast with portion sizes in grams
        List<IngredientPortion> pancakeBreakfastIngredients = Arrays.asList(
            new IngredientPortion(PancakeHouseIngredientStore.getIngredient("pancakeMix.organic"), 150),  // 150g pancake mix
            new IngredientPortion(PancakeHouseIngredientStore.getIngredient("eggs.organic"), 100),        // 100g eggs (2 large eggs)
            new IngredientPortion(PancakeHouseIngredientStore.getIngredient("toast"), 60)                 // 60g toast (2 slices)
        );
        
        pancakeHouseMenu.add(new MenuItem(
            "K&B's Pancake Breakfast", 
            "Pancakes with scrambled eggs and toast",
            2.99,
            pancakeBreakfastIngredients));

        // Create Regular Pancake Breakfast with portion sizes in grams
        List<IngredientPortion> regularPancakeIngredients = Arrays.asList(
            new IngredientPortion(PancakeHouseIngredientStore.getIngredient("pancakeMix.regular"), 150),  // 150g pancake mix
            new IngredientPortion(PancakeHouseIngredientStore.getIngredient("eggs.regular"), 100),        // 100g eggs (2 large eggs)
            new IngredientPortion(PancakeHouseIngredientStore.getIngredient("sausage"), 80)              // 80g sausage
        );
        
        pancakeHouseMenu.add(new MenuItem(
            "Regular Pancake Breakfast",
            "Pancakes with fried eggs, sausage",
            2.99,
            regularPancakeIngredients));
            
        return pancakeHouseMenu;
    }
}
