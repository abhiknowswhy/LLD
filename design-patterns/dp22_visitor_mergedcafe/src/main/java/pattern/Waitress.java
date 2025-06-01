package pattern;

import java.util.Iterator;
import pattern.Menu.MenuComponent;
import pattern.Visitors.PriceAnalysisVisitor;
import pattern.Visitors.VegetarianAnalysisVisitor;
import pattern.Visitors.NutritionAnalysisVisitor;

public class Waitress {
    MenuComponent allMenus;
    
    public Waitress(MenuComponent allMenus) {
        this.allMenus = allMenus;
    }
    
    public void printMenu() {
        allMenus.print();
    }
    
    public void printVegetarianMenu() {
        Iterator<MenuComponent> iterator = allMenus.createIterator();
        
        System.out.println("\nVEGETARIAN MENU\n----");
        while (iterator.hasNext()) {
            MenuComponent menuComponent = iterator.next();
            try {
                if (menuComponent.isVegetarian()) {
                    menuComponent.print();
                }
            } catch (UnsupportedOperationException e) {}
        }
    }

    // New methods using visitors    
    public void printMenuStatistics() {
        // Price analysis
        PriceAnalysisVisitor priceVisitor = new PriceAnalysisVisitor();
        allMenus.accept(priceVisitor);
        
        System.out.println("\nPRICE ANALYSIS\n----");
        System.out.printf("Average Price: $%.2f%n", priceVisitor.getAveragePrice());
        System.out.printf("Maximum Price: $%.2f%n", priceVisitor.getMaxPrice());
        System.out.printf("Minimum Price: $%.2f%n", priceVisitor.getMinPrice());
        System.out.printf("Total Menu Value: $%.2f%n", priceVisitor.getTotalPrice());
        System.out.printf("Total Items: %d%n", priceVisitor.getItemCount());
        
        // Vegetarian analysis
        VegetarianAnalysisVisitor vegVisitor = new VegetarianAnalysisVisitor();
        allMenus.accept(vegVisitor);
        
        System.out.println("\nVEGETARIAN ANALYSIS\n----");
        System.out.printf("Vegetarian Items: %d%n", vegVisitor.getVegetarianCount());
        System.out.printf("Total Items: %d%n", vegVisitor.getTotalItems());
        System.out.printf("Vegetarian Percentage: %.1f%%%n", vegVisitor.getVegetarianPercentage());
        
        // Nutrition analysis
        NutritionAnalysisVisitor nutritionVisitor = new NutritionAnalysisVisitor();
        allMenus.accept(nutritionVisitor);
        
        System.out.println("\nNUTRITION ANALYSIS\n----");
        System.out.printf("Average Calories: %.1f%n", nutritionVisitor.getAverageCalories());
        System.out.printf("Average Proteins: %.1fg%n", nutritionVisitor.getAverageProteins());
        System.out.printf("Average Carbs: %.1fg%n", nutritionVisitor.getAverageCarbs());
        System.out.printf("Average Fats: %.1fg%n", nutritionVisitor.getAverageFats());
        System.out.printf("Healthiest Rating: %d/5%n", nutritionVisitor.getHealthiestRating());
        System.out.printf("Least Healthy Rating: %d/5%n", nutritionVisitor.getLeastHealthyRating());
        System.out.printf("Total Calories: %d%n", nutritionVisitor.getTotalCalories());
        System.out.printf("Total Proteins: %.1fg%n", nutritionVisitor.getTotalProteins());
        System.out.printf("Total Carbs: %.1fg%n", nutritionVisitor.getTotalCarbs());
        System.out.printf("Total Fats: %.1fg%n", nutritionVisitor.getTotalFats());
    }
}
