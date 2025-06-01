package pattern.Visitors;

import pattern.Menu.Menu;
import pattern.Menu.MenuItem;

public class DescriptionAnalysisVisitor implements MenuVisitor {
    private MenuAnalysisState state;
    
    public DescriptionAnalysisVisitor() {
        this.state = new MenuAnalysisState();
        state.setValue("healthyItemCount", 0);
        state.setValue("vegetarianItemCount", 0);
        state.setValue("spicyItemCount", 0);
        state.setValue("friedItemCount", 0);
        state.setValue("grilledItemCount", 0);
        state.setValue("totalItems", 0);
        state.setValue("freshIngredientsCount", 0);
        state.setValue("itemsWithAllergensCount", 0);
    }
    
    @Override
    public void visit(Menu menu) {
        // No calculation needed for menu itself
    }
    
    @Override
    public void visit(MenuItem menuItem) {
        String description = menuItem.getDescription().toLowerCase();
        int totalItems = state.getIntValue("totalItems") + 1;
        
        // Check for healthy items (contains healthy ingredients or preparation methods)
        if (containsHealthyIndicators(description)) {
            state.setValue("healthyItemCount", state.getIntValue("healthyItemCount") + 1);
        }
        
        // Check for vegetarian items
        if (menuItem.isVegetarian() || containsVegetarianIndicators(description)) {
            state.setValue("vegetarianItemCount", state.getIntValue("vegetarianItemCount") + 1);
        }
        
        // Check for spicy items
        if (containsSpicyIndicators(description)) {
            state.setValue("spicyItemCount", state.getIntValue("spicyItemCount") + 1);
        }
        
        // Analyze cooking methods
        if (containsCookingMethod(description, "fried", "deep-fried", "pan-fried")) {
            state.setValue("friedItemCount", state.getIntValue("friedItemCount") + 1);
        }
        if (containsCookingMethod(description, "grilled", "chargrilled", "barbecued", "bbq")) {
            state.setValue("grilledItemCount", state.getIntValue("grilledItemCount") + 1);
        }
        
        // Check for fresh ingredients
        if (containsFreshIndicators(description)) {
            state.setValue("freshIngredientsCount", state.getIntValue("freshIngredientsCount") + 1);
        }
        
        // Check for allergen warnings
        if (containsAllergenIndicators(description)) {
            state.setValue("itemsWithAllergensCount", state.getIntValue("itemsWithAllergensCount") + 1);
        }
        
        state.setValue("totalItems", totalItems);
    }
      private boolean containsHealthyIndicators(String description) {
        String[] primaryIndicators = {"healthy", "low-fat", "lean"};
        String[] secondaryIndicators = {"grilled", "steamed", "baked", "fresh", 
                                     "organic", "light", "salad"};
        
        // Must contain at least one primary indicator, or two secondary indicators to be considered healthy
        boolean hasPrimary = containsAnyTerm(description, primaryIndicators);
        if (hasPrimary) return true;
        
        int secondaryCount = 0;
        for (String term : secondaryIndicators) {
            if (description.contains(term)) {
                secondaryCount++;
                if (secondaryCount >= 2) return true;
            }
        }
        return false;
    }
    
    private boolean containsVegetarianIndicators(String description) {
        String[] vegIndicators = {"vegetarian", "vegan", "plant-based", "meatless", "veggie"};
        return containsAnyTerm(description, vegIndicators);
    }
    
    private boolean containsSpicyIndicators(String description) {
        String[] spicyIndicators = {"spicy", "hot", "cajun", "jalapeno", "chili", "sriracha"};
        return containsAnyTerm(description, spicyIndicators);
    }
    
    private boolean containsFreshIndicators(String description) {
        String[] freshIndicators = {"fresh", "house-made", "hand-made", "daily", "seasonal"};
        return containsAnyTerm(description, freshIndicators);
    }
    
    private boolean containsAllergenIndicators(String description) {
        String[] allergens = {"nuts", "peanuts", "dairy", "gluten", "soy", "eggs", "milk", "wheat"};
        return containsAnyTerm(description, allergens);
    }
    
    private boolean containsCookingMethod(String description, String... methods) {
        return containsAnyTerm(description, methods);
    }
    
    private boolean containsAnyTerm(String description, String[] terms) {
        for (String term : terms) {
            if (description.contains(term)) {
                return true;
            }
        }
        return false;
    }
      public int getHealthyItemCount() {
        return state.getIntValue("healthyItemCount");
    }
    
    public int getVegetarianItemCount() {
        return state.getIntValue("vegetarianItemCount");
    }
    
    public int getSpicyItemCount() {
        return state.getIntValue("spicyItemCount");
    }
    
    public int getFriedItemCount() {
        return state.getIntValue("friedItemCount");
    }
    
    public int getGrilledItemCount() {
        return state.getIntValue("grilledItemCount");
    }
    
    public int getFreshIngredientsCount() {
        return state.getIntValue("freshIngredientsCount");
    }
    
    public int getItemsWithAllergensCount() {
        return state.getIntValue("itemsWithAllergensCount");
    }
    
    public int getTotalItems() {
        return state.getIntValue("totalItems");
    }
    
    public double getHealthyItemPercentage() {
        return calculatePercentage("healthyItemCount");
    }
    
    public double getVegetarianItemPercentage() {
        return calculatePercentage("vegetarianItemCount");
    }
    
    public double getSpicyItemPercentage() {
        return calculatePercentage("spicyItemCount");
    }
    
    private double calculatePercentage(String countKey) {
        int total = state.getIntValue("totalItems");
        if (total == 0) return 0.0;
        return (state.getIntValue(countKey) * 100.0) / total;
    }
}
