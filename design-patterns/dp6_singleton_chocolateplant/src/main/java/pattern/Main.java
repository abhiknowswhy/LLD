package pattern;

import pattern.boiler.ChocolateBoiler;
import pattern.boiler.impl.*;

/**
 * Demonstrates different singleton implementations of the ChocolateBoiler.
 */
public class Main {
    private static void demonstrateBoiler(String title, ChocolateBoiler boiler) {
        System.out.println("\n" + "-".repeat(70));
        System.out.println(title);
        System.out.println("-".repeat(70));
        
        boiler.fill();
        boiler.boil();
        boiler.fill();  // Should show warning
        boiler.drain();
    }

    public static void main(String[] args) {
        String[] boilerNames = {
            "SIMPLE BOILER (Not Thread-Safe)",
            "SYNCHRONIZED BOILER",
            "EAGERLY CREATED BOILER",
            "DOUBLE-CHECKED LOCKING BOILER",
            "ENUM BOILER"
        };

        ChocolateBoiler[] boilers = {
            SimpleChocolateBoiler.getInstance(),
            SynchronizedChocolateBoiler.getInstance(),
            EagerChocolateBoiler.getInstance(),
            DoubleCheckedChocolateBoiler.getInstance(),
            EnumChocolateBoiler.getInstance()
        };

        for (int i = 0; i < boilers.length; i++) {
            demonstrateBoiler(boilerNames[i], boilers[i]);
        }

        // Demonstrate singleton behavior by checking if multiple calls return the same instance
        System.out.println("\n" + "-".repeat(70));
        System.out.println("DEMONSTRATING SINGLETON BEHAVIOR");
        System.out.println("-".repeat(70));

        for (int i = 0; i < boilers.length; i++) {
            ChocolateBoiler first = boilers[i];
            ChocolateBoiler second = null;
            switch (i) {
                case 0: second = SimpleChocolateBoiler.getInstance(); break;
                case 1: second = SynchronizedChocolateBoiler.getInstance(); break;
                case 2: second = EagerChocolateBoiler.getInstance(); break;
                case 3: second = DoubleCheckedChocolateBoiler.getInstance(); break;
                case 4: second = EnumChocolateBoiler.getInstance(); break;
            }
            System.out.println(boilerNames[i].split(" ")[0] + " instances are same: " + (first == second));
        }
    }
}
