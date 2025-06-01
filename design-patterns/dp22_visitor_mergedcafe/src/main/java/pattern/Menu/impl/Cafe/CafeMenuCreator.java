package pattern.Menu.impl.Cafe;

import java.util.Arrays;
import java.util.List;
import pattern.Menu.Menu;
import pattern.Menu.MenuComponent;
import pattern.Menu.MenuItem;
import pattern.Menu.Ingredients.IngredientPortion;

public class CafeMenuCreator {

    public static MenuComponent createMenu() {
        MenuComponent cafeMenu = new Menu("CAFE MENU", "Dinner");
        
        // Create Veggie Burger with portion sizes in grams
        List<IngredientPortion> veggieBurgerIngredients = Arrays.asList(            new IngredientPortion(CafeIngredientStore.getIngredient("bun.wheat"), 80),          // 80g bun
            new IngredientPortion(CafeIngredientStore.getIngredient("patty.veggie"), 120),      // 120g veggie patty
            new IngredientPortion(CafeIngredientStore.getIngredient("lettuce"), 30),            // 30g lettuce
            new IngredientPortion(CafeIngredientStore.getIngredient("tomato"), 50),             // 50g tomato
            new IngredientPortion(CafeIngredientStore.getIngredient("fries.air"), 150)          // 150g air fries
        );
        
        cafeMenu.add(new MenuItem(
            "Veggie Burger and Air Fries",
            "Veggie burger on a whole wheat bun, lettuce, tomato, and fries",
            3.99,
            veggieBurgerIngredients));

        // Create Soup of the Day with portion sizes in grams
        List<IngredientPortion> soupIngredients = Arrays.asList(
            new IngredientPortion(CafeIngredientStore.getIngredient("vegetables.seasonal"), 180),  // 180g seasonal vegetables
            new IngredientPortion(CafeIngredientStore.getIngredient("stock.chicken"), 240),        // 240g chicken stock
            new IngredientPortion(CafeIngredientStore.getIngredient("greens.mixed"), 45),          // 45g mixed greens
            new IngredientPortion(CafeIngredientStore.getIngredient("dressing.house"), 30)         // 30g house dressing
        );

        cafeMenu.add(new MenuItem(
            "Soup of the day",
            "A cup of the soup of the day, with a side salad",
            3.69,
            soupIngredients));
            
        return cafeMenu;
    }
}
