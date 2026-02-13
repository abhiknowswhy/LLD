package questions.lld;

import questions.lld.HashTable.HashTable;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Hash Table Demo ===\n");

        HashTable<String, Integer> table = new HashTable<>();

        // Insert entries
        table.put("Alice", 90);
        table.put("Bob", 85);
        table.put("Charlie", 92);
        table.put("Diana", 88);
        System.out.println("After insertions: " + table);
        System.out.println("Size: " + table.size() + ", Capacity: " + table.capacity());

        // Lookup
        System.out.println("\nGet Alice: " + table.get("Alice"));
        System.out.println("Get Bob: " + table.get("Bob"));
        System.out.println("Contains Charlie: " + table.containsKey("Charlie"));
        System.out.println("Contains Eve: " + table.containsKey("Eve"));

        // Update
        Integer oldValue = table.put("Alice", 95);
        System.out.println("\nUpdated Alice: " + oldValue + " -> " + table.get("Alice"));

        // Remove
        Integer removed = table.remove("Bob");
        System.out.println("Removed Bob: " + removed);
        System.out.println("After removal: " + table);

        // Keys and values
        System.out.println("\nAll keys: " + table.keys());
        System.out.println("All values: " + table.values());
    }
}