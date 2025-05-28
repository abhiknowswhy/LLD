package pattern;

public class Main {
	public static void main(String[] args) {
		try {
			// Create prototypes
			Dragon dragon = new Dragon("Smaug", true, true);
			Drakon drakon = new Drakon("Hydra", 3, true);
			Godzilla godzilla = new Godzilla("Gojira", true, 100);
			Frankenstein frankenstein = new Frankenstein("Frank", 12, true);

			// Clone monsters
			Dragon dragonClone = (Dragon) dragon.copy();
			dragonClone.setName("Baby Smaug");
			
			Drakon drakonClone = (Drakon) drakon.copy();
			drakonClone.setName("Baby Hydra");
			
			Godzilla godzillaClone = (Godzilla) godzilla.copy();
			godzillaClone.setName("Godzilla Jr.");
			
			Frankenstein frankClone = (Frankenstein) frankenstein.copy();
			frankClone.setName("Young Frank");

			// Display each monster and its clone together
			System.out.println("\n=== Dragon Family ===");
			System.out.println("Original: " + dragon);
			dragon.specialAbility();
			System.out.println("Clone: " + dragonClone);
			dragonClone.specialAbility();

			System.out.println("\n=== Drakon Family ===");
			System.out.println("Original: " + drakon);
			drakon.specialAbility();
			System.out.println("Clone: " + drakonClone);
			drakonClone.specialAbility();

			System.out.println("\n=== Godzilla Family ===");
			System.out.println("Original: " + godzilla);
			godzilla.specialAbility();
			System.out.println("Clone: " + godzillaClone);
			godzillaClone.specialAbility();

			System.out.println("\n=== Frankenstein Family ===");
			System.out.println("Original: " + frankenstein);
			frankenstein.specialAbility();
			System.out.println("Clone: " + frankClone);
			frankClone.specialAbility();

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
}