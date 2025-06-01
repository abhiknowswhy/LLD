package pattern.Visitors;

import pattern.Menu.Menu;
import pattern.Menu.MenuItem;
import pattern.Menu.Ingredients.Nutrition;

public class NutritionAnalysisVisitor implements MenuVisitor {
    private MenuAnalysisState state;
    
    public NutritionAnalysisVisitor() {
        this.state = new MenuAnalysisState();
        state.setValue("totalCalories", 0);
        state.setValue("totalProteins", 0.0);
        state.setValue("totalCarbs", 0.0);
        state.setValue("totalFats", 0.0);
        state.setValue("itemCount", 0);
        state.setValue("healthiestRating", 0);
        state.setValue("leastHealthyRating", 5);
    }
    
    @Override
    public void visit(Menu menu) {
        // No calculation needed for menu itself
    }
    
    @Override
    public void visit(MenuItem menuItem) {
        Nutrition nutrition = menuItem.getNutrition();
        int count = state.getIntValue("itemCount") + 1;
        
        state.setValue("totalCalories", state.getIntValue("totalCalories") + nutrition.getCalories());
        state.setValue("totalProteins", state.getDoubleValue("totalProteins") + nutrition.getProteins());
        state.setValue("totalCarbs", state.getDoubleValue("totalCarbs") + nutrition.getCarbs());
        state.setValue("totalFats", state.getDoubleValue("totalFats") + nutrition.getFats());
        state.setValue("itemCount", count);
        
        int healthRating = nutrition.getHealthRating();
        state.setValue("healthiestRating", Math.max(state.getIntValue("healthiestRating"), healthRating));
        state.setValue("leastHealthyRating", Math.min(state.getIntValue("leastHealthyRating"), healthRating));
    }
    
    public MenuAnalysisState getState() {
        return state;
    }
    
    public double getAverageCalories() {
        int count = state.getIntValue("itemCount");
        return count > 0 ? state.getIntValue("totalCalories") / (double)count : 0;
    }
    
    public double getAverageProteins() {
        int count = state.getIntValue("itemCount");
        return count > 0 ? state.getDoubleValue("totalProteins") / count : 0;
    }
    
    public double getAverageCarbs() {
        int count = state.getIntValue("itemCount");
        return count > 0 ? state.getDoubleValue("totalCarbs") / count : 0;
    }
    
    public double getAverageFats() {
        int count = state.getIntValue("itemCount");
        return count > 0 ? state.getDoubleValue("totalFats") / count : 0;
    }
    
    public int getHealthiestRating() {
        return state.getIntValue("healthiestRating");
    }
    
    public int getLeastHealthyRating() {
        return state.getIntValue("leastHealthyRating");
    }
    
    public int getTotalCalories() {
        return state.getIntValue("totalCalories");
    }
    
    public double getTotalProteins() {
        return state.getDoubleValue("totalProteins");
    }
    
    public double getTotalCarbs() {
        return state.getDoubleValue("totalCarbs");
    }
    
    public double getTotalFats() {
        return state.getDoubleValue("totalFats");
    }
}
