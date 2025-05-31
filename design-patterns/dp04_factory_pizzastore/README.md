# Factory Method Pattern

This project demonstrates the Factory Method Pattern through a Pizza Store example.

## Overview

The Factory Method Pattern defines an interface for creating an object, but lets subclasses decide which class to instantiate. Factory Method lets a class defer instantiation to subclasses. The Factory Method Pattern gives us a way to encapsulate the instantiations of concrete types.

## Structure

The project is organized into the following packages:

```text
pattern/
├── pizza/              # Base pizza abstract class
├── store/              # Base store abstract class
├── pizza/impl/         # Pizza implementations
│   ├── ny/            # New York style pizzas
│   └── chicago/       # Chicago style pizzas
└── store/impl/        # Store implementations
    ├── ny/            # NY pizza store
    └── chicago/       # Chicago pizza store
```

## Key Components

1. Abstract Creator (`PizzaStore`)
   - Declares the factory method that returns a product object
   - May call the factory method for product creation

2. Concrete Creators (`NYPizzaStore`, `ChicagoPizzaStore`)
   - Override the factory method to return a specific product

3. Abstract Product (`Pizza`)
   - Declares the interface for product objects

4. Concrete Products (NY and Chicago style pizzas)
   - Implement the Product interface with specific implementations

## Design Principle

This implementation follows the "Dependency Inversion Principle":
> "Depend upon abstractions. Do not depend upon concrete classes."

In simpler words, this suggests that our high-level components should not depend on our low-level components; rather, they should both depend on abstractions. The Factory Method pattern allows us to achieve this by:

1. No direct instantiation of concrete classes
2. Classes extending from abstract types
3. Methods operating on abstract types

## Running the Example

The `PizzaTestDrive` class contains the main method that demonstrates the use of different pizza stores creating their respective style pizzas.
