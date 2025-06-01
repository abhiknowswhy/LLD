package pattern.Visitors;

import pattern.Menu.Menu;
import pattern.Menu.MenuItem;

public class VegetarianAnalysisVisitor implements MenuVisitor {
    private MenuAnalysisState state;
    
    public VegetarianAnalysisVisitor() {
        this.state = new MenuAnalysisState();
        state.setValue("totalItems", 0);
        state.setValue("vegetarianItems", 0);
    }
    
    @Override
    public void visit(Menu menu) {
        // No calculation needed for menu itself
    }
    
    @Override
    public void visit(MenuItem menuItem) {
        int totalItems = state.getIntValue("totalItems") + 1;
        int vegetarianItems = state.getIntValue("vegetarianItems");
        
        if (menuItem.isVegetarian()) {
            vegetarianItems++;
        }
        
        state.setValue("totalItems", totalItems);
        state.setValue("vegetarianItems", vegetarianItems);
    }
    
    public MenuAnalysisState getState() {
        return state;
    }
    
    public double getVegetarianPercentage() {
        int total = state.getIntValue("totalItems");
        return total > 0 ? (state.getIntValue("vegetarianItems") * 100.0) / total : 0;
    }
    
    public int getVegetarianCount() {
        return state.getIntValue("vegetarianItems");
    }
    
    public int getTotalItems() {
        return state.getIntValue("totalItems");
    }
}
