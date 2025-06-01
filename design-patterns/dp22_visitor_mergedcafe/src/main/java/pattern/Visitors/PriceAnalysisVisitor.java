package pattern.Visitors;

import pattern.Menu.Menu;
import pattern.Menu.MenuItem;

public class PriceAnalysisVisitor implements MenuVisitor {
    private MenuAnalysisState state;
    
    public PriceAnalysisVisitor() {
        this.state = new MenuAnalysisState();
        state.setValue("totalPrice", 0.0);
        state.setValue("maxPrice", Double.MIN_VALUE);
        state.setValue("minPrice", Double.MAX_VALUE);
        state.setValue("itemCount", 0);
    }
    
    @Override
    public void visit(Menu menu) {
        // No price calculation needed for menu itself
    }
    
    @Override
    public void visit(MenuItem menuItem) {
        double price = menuItem.getPrice();
        int count = state.getIntValue("itemCount") + 1;
        double total = state.getDoubleValue("totalPrice") + price;
        double max = Math.max(state.getDoubleValue("maxPrice"), price);
        double min = Math.min(state.getDoubleValue("minPrice"), price);
        
        state.setValue("itemCount", count);
        state.setValue("totalPrice", total);
        state.setValue("maxPrice", max);
        state.setValue("minPrice", min);
    }
    
    public MenuAnalysisState getState() {
        return state;
    }
    
    public double getAveragePrice() {
        int count = state.getIntValue("itemCount");
        return count > 0 ? state.getDoubleValue("totalPrice") / count : 0;
    }
    
    public double getMaxPrice() {
        double max = state.getDoubleValue("maxPrice");
        return max != Double.MIN_VALUE ? max : 0;
    }
    
    public double getMinPrice() {
        double min = state.getDoubleValue("minPrice");
        return min != Double.MAX_VALUE ? min : 0;
    }
    
    public double getTotalPrice() {
        return state.getDoubleValue("totalPrice");
    }
    
    public int getItemCount() {
        return state.getIntValue("itemCount");
    }
}
