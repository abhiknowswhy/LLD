# Singleton Pattern - Chocolate Plant

This module demonstrates the implementation of the Singleton pattern through the example of a Chocolate Boiler in a chocolate factory. The Singleton pattern ensures a class has only one instance and provides a global point of access to that instance.

## Directory Structure

```text
src/main/java/pattern/
├── boiler/
│   ├── impl/
│   │   ├── SimpleChocolateBoiler.java         # Basic implementation (not thread-safe)
│   │   ├── SynchronizedChocolateBoiler.java   # Thread-safe using synchronized method
│   │   ├── EagerChocolateBoiler.java         # Thread-safe using eager initialization
│   │   ├── DoubleCheckedChocolateBoiler.java # Thread-safe using double-checked locking
│   │   └── EnumChocolateBoiler.java          # Thread-safe using enum
│   ├── ChocolateBoiler.java                  # Base interface
│   └── AbstractChocolateBoiler.java          # Abstract base class with common functionality
└── demo/
    └── ChocolateBoilerDemo.java              # Demonstration class
```

## Implementation Approaches

1. **Simple Singleton (Not Thread-Safe)**
   - Uses lazy initialization
   - Not suitable for multi-threaded environments
   - Simplest implementation

2. **Synchronized Singleton**
   - Thread-safe using synchronized method
   - Performance overhead due to synchronization
   - Simple thread-safe implementation

3. **Eager Singleton**
   - Thread-safe using eager initialization
   - No synchronization needed
   - Instance created even if not used

4. **Double-Checked Locking**
   - Thread-safe using double-checked locking
   - Better performance than synchronized method
   - Requires Java 5 or higher (volatile keyword)

5. **Enum Singleton**
   - Thread-safe by default
   - Prevents serialization issues
   - Most concise implementation
   - Recommended by Joshua Bloch

## Running the Demo

The `ChocolateBoilerDemo` class demonstrates all implementations and verifies their singleton behavior. Each boiler instance performs these operations:

- Fill the boiler
- Boil the contents
- Attempt to fill again (shows warning)
- Drain the boiler

It also verifies that multiple calls to `getInstance()` return the same instance.
