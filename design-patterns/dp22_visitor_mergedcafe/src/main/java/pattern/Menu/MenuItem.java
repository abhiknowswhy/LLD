package pattern.Menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import pattern.Iterators.NullIterator;
import pattern.Menu.Ingredients.IngredientPortion;
import pattern.Menu.Ingredients.Nutrition;
import pattern.Visitors.MenuVisitor;

public class MenuItem extends MenuComponent {
    String name;
    String description;
    boolean vegetarian;
    double price;
    Nutrition nutrition;
    List<IngredientPortion> ingredients;

    public MenuItem(String name, 
    String description, 
    double price,
    List<IngredientPortion> ingredients) 
    { 
        this.name = name;
        this.description = description;
        this.price = price;
        this.ingredients = new ArrayList<>(ingredients);
        
        // Calculate total nutrition from ingredient portions
        List<Nutrition> portionNutritions = ingredients.stream()
            .map(IngredientPortion::getNutritionForPortion)
            .collect(Collectors.toList());
            
        this.nutrition = Nutrition.combine(portionNutritions);
        
        // Set vegetarian status based on dietary type
        this.vegetarian = this.nutrition.getDietaryType() != Nutrition.DietaryType.NON_VEG;
    }
    
    public Nutrition getNutrition() {
        return nutrition;
    }
    
    public List<IngredientPortion> getIngredients() {
        return new ArrayList<>(ingredients);
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public double getPrice() {
        return price;
    }
    
    public boolean isVegetarian() {
        return vegetarian;
    }
    
    @Override
    public Iterator<MenuComponent> createIterator() {
        return new NullIterator();
    }
    
    @Override
    public void accept(MenuVisitor visitor) {
        visitor.visit(this);
    }

    public void print() {
        System.out.print("  " + getName());
        
        // Show dietary type marker
        switch (nutrition.getDietaryType()) {
            case VEGAN:
                System.out.print(" (VG)");
                break;
            case VEGETARIAN:
                System.out.print(" (V)");
                break;
            case EGGETARIAN:
                System.out.print(" (E)");
                break;
            case NON_VEG:
                System.out.print(" (N)");
                break;
        }
        
        System.out.println(", " + getPrice());
        System.out.println("     -- " + getDescription());
        System.out.printf("     -- Nutrition: Cal: %d, Protein: %.1fg, Carbs: %.1fg, Fat: %.1fg, Health Rating: %d/5, Diet: %s%n",
            nutrition.getCalories(), nutrition.getProteins(), nutrition.getCarbs(), nutrition.getFats(), 
            nutrition.getHealthRating(), nutrition.getDietaryType());
    }
}
