package pattern.Menu.Ingredients;

import java.util.List;

public class Nutrition {
    public enum DietaryType {
        VEGAN,      // Only plant-based ingredients
        VEGETARIAN, // May include dairy but no eggs
        EGGETARIAN, // May include eggs and dairy but no meat
        NON_VEG    // Contains meat or fish
    }

    private int calories;
    private double proteins;  // in grams
    private double carbs;     // in grams
    private double fats;      // in grams
    private int healthRating; // 1-5 scale, 5 being the healthiest
    private DietaryType dietaryType;

    public Nutrition(int calories, double proteins, double carbs, double fats) {
        this(calories, proteins, carbs, fats, DietaryType.VEGAN); // Default to VEGAN if not specified
    }

    public Nutrition(int calories, double proteins, double carbs, double fats, DietaryType dietaryType) {
        this.calories = calories;
        this.proteins = proteins;
        this.carbs = carbs;
        this.fats = fats;
        this.dietaryType = dietaryType;
        calculateHealthRating();
    }

    private void calculateHealthRating() {
        // Simple algorithm for health rating based on macronutrient balance
        // Higher protein, moderate carbs, lower fats = better rating
        double proteinRatio = proteins / calories * 400; // Protein calories / total calories
        double carbRatio = carbs / calories * 400;      // Carb calories / total calories
        double fatRatio = fats / calories * 900;        // Fat calories / total calories

        // Ideal ratios: ~30% protein, ~50% carbs, ~20% fat
        double idealScore = 
            (proteinRatio >= 25 && proteinRatio <= 35 ? 2 : 1) +
            (carbRatio >= 45 && carbRatio <= 55 ? 2 : 1) +
            (fatRatio >= 15 && fatRatio <= 25 ? 1 : 0);

        this.healthRating = (int) idealScore;
    }

    public int getCalories() {
        return calories;
    }

    public double getProteins() {
        return proteins;
    }

    public double getCarbs() {
        return carbs;
    }

    public double getFats() {
        return fats;
    }    public int getHealthRating() {
        return healthRating;
    }

    public DietaryType getDietaryType() {
        return dietaryType;
    }

    public Nutrition scale(double factor) {
        return new Nutrition(
            (int)(this.calories * factor),
            this.proteins * factor,
            this.carbs * factor,
            this.fats * factor,
            this.dietaryType
        );
    }

    public static Nutrition combine(List<Nutrition> nutritions) {
        int totalCalories = 0;
        double totalProteins = 0;
        double totalCarbs = 0;
        double totalFats = 0;
        DietaryType combinedType = DietaryType.VEGAN;
        
        for (Nutrition n : nutritions) {
            totalCalories += n.getCalories();
            totalProteins += n.getProteins();
            totalCarbs += n.getCarbs();
            totalFats += n.getFats();
            // Update combined type based on hierarchy
            if (n.getDietaryType() == DietaryType.NON_VEG) {
                combinedType = DietaryType.NON_VEG;
            } else if (n.getDietaryType() == DietaryType.EGGETARIAN && combinedType != DietaryType.NON_VEG) {
                combinedType = DietaryType.EGGETARIAN;
            } else if (n.getDietaryType() == DietaryType.VEGETARIAN && combinedType == DietaryType.VEGAN) {
                combinedType = DietaryType.VEGETARIAN;
            }
        }
        
        return new Nutrition(totalCalories, totalProteins, totalCarbs, totalFats, combinedType);
    }
}
