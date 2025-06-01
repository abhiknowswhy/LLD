package pattern.Visitors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pattern.Menu.Menu;
import pattern.Menu.MenuItem;
import pattern.Menu.Ingredients.Ingredient;
import pattern.Menu.Ingredients.Nutrition;
import pattern.Menu.Ingredients.IngredientPortion;

import java.util.Arrays;
import java.util.List;

public class DescriptionAnalysisVisitorTest {
    private DescriptionAnalysisVisitor visitor;
    private Menu menu;
    private MenuItem veggieItem;
    private MenuItem spicyItem;
    private MenuItem friedItem;
    private MenuItem healthyItem;

    @BeforeEach
    void setUp() {
        visitor = new DescriptionAnalysisVisitor();
        menu = new Menu("Test Menu", "Test Description");        // Create test ingredients with nutrition info
        Ingredient veggieIngredient = new Ingredient("Veggie Patty", true, true, "local",
            new Nutrition(240, 21, 28, 8, Nutrition.DietaryType.VEGAN));
            
        Ingredient chickenIngredient = new Ingredient("Chicken", false, false, "local",
            new Nutrition(165, 31, 0, 3.6, Nutrition.DietaryType.NON_VEG));
            
        Ingredient noodleIngredient = new Ingredient("Noodles", true, true, "local",
            new Nutrition(200, 7, 40, 1, Nutrition.DietaryType.VEGAN));
            
        Ingredient riceIngredient = new Ingredient("Rice", true, true, "local",
            new Nutrition(130, 2.7, 28, 0.3, Nutrition.DietaryType.VEGAN));

        // Create ingredients lists for each item
        List<IngredientPortion> veggieIngredients = Arrays.asList(
            new IngredientPortion(veggieIngredient, 120)
        );

        List<IngredientPortion> spicyIngredients = Arrays.asList(
            new IngredientPortion(noodleIngredient, 200),
            new IngredientPortion(chickenIngredient, 150)
        );

        List<IngredientPortion> friedIngredients = Arrays.asList(
            new IngredientPortion(riceIngredient, 200),
            new IngredientPortion(chickenIngredient, 100)
        );

        List<IngredientPortion> healthyIngredients = Arrays.asList(
            new IngredientPortion(chickenIngredient, 150)
        );

        veggieItem = new MenuItem(
            "Veggie Burger",
            "A delicious vegetarian burger with fresh vegetables",
            9.99,
            veggieIngredients
        );

        spicyItem = new MenuItem(
            "Spicy Noodles",
            "Hot and spicy noodles with sriracha sauce",
            12.99,
            spicyIngredients
        );

        friedItem = new MenuItem(
            "Fried Rice",
            "Deep-fried rice with vegetables",
            8.99,
            friedIngredients
        );

        healthyItem = new MenuItem(
            "Fresh Salad",
            "Fresh and healthy grilled chicken salad with organic ingredients",
            10.99,
            healthyIngredients
        );
    }

    @Test
    void testMenuVisit() {
        visitor.visit(menu);
        assertEquals(0, visitor.getTotalItems(), "Menu visit should not affect item count");
    }

    @Test
    void testVegetarianItemVisit() {
        visitor.visit(veggieItem);
        assertEquals(1, visitor.getVegetarianItemCount(), "Should count vegetarian item");
        assertEquals(1, visitor.getTotalItems(), "Should increment total items");
    }

    @Test
    void testSpicyItemVisit() {
        visitor.visit(spicyItem);
        assertEquals(1, visitor.getSpicyItemCount(), "Should count spicy item");
        assertEquals(1, visitor.getTotalItems(), "Should increment total items");
    }

    @Test
    void testFriedItemVisit() {
        visitor.visit(friedItem);
        assertEquals(1, visitor.getFriedItemCount(), "Should count fried item");
        assertEquals(1, visitor.getTotalItems(), "Should increment total items");
    }

    @Test
    void testHealthyItemVisit() {
        visitor.visit(healthyItem);
        assertEquals(1, visitor.getHealthyItemCount(), "Should count healthy item");
        assertEquals(1, visitor.getFreshIngredientsCount(), "Should count fresh ingredients");
        assertEquals(1, visitor.getTotalItems(), "Should increment total items");
    }

    @Test
    void testMultipleItemVisits() {
        visitor.visit(veggieItem);
        visitor.visit(spicyItem);
        visitor.visit(friedItem);
        visitor.visit(healthyItem);

        assertEquals(4, visitor.getTotalItems(), "Should count total items correctly");
        assertEquals(1, visitor.getVegetarianItemCount(), "Should count vegetarian items");
        assertEquals(1, visitor.getSpicyItemCount(), "Should count spicy items");
        assertEquals(1, visitor.getFriedItemCount(), "Should count fried items");
        assertEquals(1, visitor.getHealthyItemCount(), "Should count healthy items");
    }

    @Test
    void testPercentageCalculations() {
        visitor.visit(veggieItem);
        visitor.visit(spicyItem);
        visitor.visit(friedItem);
        visitor.visit(healthyItem);

        assertEquals(25.0, visitor.getVegetarianItemPercentage(), "Should calculate vegetarian percentage correctly");
        assertEquals(25.0, visitor.getSpicyItemPercentage(), "Should calculate spicy percentage correctly");
        assertEquals(25.0, visitor.getHealthyItemPercentage(), "Should calculate healthy percentage correctly");
    }

    @Test
    void testEmptyMenu() {
        assertEquals(0, visitor.getTotalItems(), "Empty menu should have 0 items");
        assertEquals(0.0, visitor.getVegetarianItemPercentage(), "Empty menu should have 0% vegetarian items");
        assertEquals(0.0, visitor.getSpicyItemPercentage(), "Empty menu should have 0% spicy items");
        assertEquals(0.0, visitor.getHealthyItemPercentage(), "Empty menu should have 0% healthy items");
    }
}
