package pattern.Menu.impl.Diner;

import java.util.Arrays;
import java.util.List;
import pattern.Menu.Menu;
import pattern.Menu.MenuComponent;
import pattern.Menu.MenuItem;
import pattern.Menu.Ingredients.IngredientPortion;

public class DinerMenuCreator {
    public static MenuComponent createMenu() {
        MenuComponent dinerMenu = new Menu("DINER MENU", "Lunch");
        MenuComponent dessertMenu = new Menu("DESSERT MENU", "Dessert of course!");
        
        // Create Vegetarian BLT with portion sizes in grams
        List<IngredientPortion> veganBLTIngredients = Arrays.asList(
            new IngredientPortion(DinerIngredientStore.getIngredient("bread.wheat"), 60),    // 60g bread (2 slices)
            new IngredientPortion(DinerIngredientStore.getIngredient("bacon.fake"), 30),     // 30g fake bacon
            new IngredientPortion(DinerIngredientStore.getIngredient("lettuce"), 30),        // 30g lettuce
            new IngredientPortion(DinerIngredientStore.getIngredient("tomato"), 50)          // 50g tomato
        );
        
        dinerMenu.add(new MenuItem(
            "Vegetarian BLT",
            "(Fakin') Bacon with lettuce & tomato on whole wheat",
            2.99,
            veganBLTIngredients));

        // Create regular BLT with portion sizes in grams
        List<IngredientPortion> bltIngredients = Arrays.asList(
            new IngredientPortion(DinerIngredientStore.getIngredient("bread.wheat"), 60),    // 60g bread (2 slices)
            new IngredientPortion(DinerIngredientStore.getIngredient("bacon.real"), 30),     // 30g real bacon
            new IngredientPortion(DinerIngredientStore.getIngredient("lettuce"), 30),        // 30g lettuce
            new IngredientPortion(DinerIngredientStore.getIngredient("tomato"), 50)          // 50g tomato
        );

        dinerMenu.add(new MenuItem(
            "BLT",
            "Bacon with lettuce & tomato on whole wheat",
            2.99,
            bltIngredients));

        // Create and add dessert menu
        dinerMenu.add(dessertMenu);

        // Create Apple Pie with portion sizes in grams
        List<IngredientPortion> applePieIngredients = Arrays.asList(
            new IngredientPortion(DinerIngredientStore.getIngredient("flour"), 120),         // 120g flour
            new IngredientPortion(DinerIngredientStore.getIngredient("apples"), 200),        // 200g apples
            new IngredientPortion(DinerIngredientStore.getIngredient("ice.cream"), 50),      // 50g ice cream
            new IngredientPortion(DinerIngredientStore.getIngredient("sugar"), 50)           // 50g sugar
        );
        
        dessertMenu.add(new MenuItem(
            "Apple Pie",
            "Apple pie with flakey crust, topped with vanilla ice cream",
            1.59,
            applePieIngredients));
            
        return dinerMenu;
    }
}
