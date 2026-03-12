# Vending Machine

Design a vending machine using the State pattern.

## Problem Statement

Implement a vending machine that handles money insertion, product selection,
dispensing, change return, and cancellation with proper state management.

### Requirements

- Insert money (supports multiple insertions)
- Select product by slot code
- Dispense product and return change
- Cancel transaction for full refund
- Stock management (add products, check inventory)
- Proper state transitions (no invalid operations)

## State Diagram

```mermaid
stateDiagram-v2
    [*] --> Idle
    Idle --> HasMoney : insertMoney()
    HasMoney --> HasMoney : insertMoney() [add more]
    HasMoney --> Dispensing : selectProduct() [if valid]
    HasMoney --> Idle : cancel() [refund]
    Dispensing --> Idle : dispense() [auto-transition]
```

## Class Diagram

```mermaid
classDiagram
    class Product {
        -String name
        -double price
    }
    class Slot {
        -String code
        -Product product
        -int quantity
        +stock()
        +addStock()
        +dispense()
    }
    class VendingState {
        <<interface>>
        +insertMoney()
        +selectProduct()
        +dispense()
        +cancel()
    }
    class IdleState {
        +insertMoney()
        +selectProduct()
        +dispense()
        +cancel()
    }
    class HasMoneyState {
        +insertMoney()
        +selectProduct()
        +dispense()
        +cancel()
    }
    class DispensingState {
        +insertMoney()
        +selectProduct()
        +dispense()
        +cancel()
    }
    class VendingMachine {
        -Map~String, Slot~ slots
        -VendingState state
        -double balance
        +insertMoney()
        +selectProduct()
        +cancelTransaction()
        +stockProduct()
        +addStock()
        +displayInventory()
    }
    VendingState <|.. IdleState : implements
    VendingState <|.. HasMoneyState : implements
    VendingState <|.. DispensingState : implements
    VendingMachine --> VendingState : state
    VendingMachine --> Slot : slots
    Slot --> Product : product
```

## Design Benefits

✅ State Pattern — eliminates if/else chains for state management  
✅ Open/Closed — new states can be added without modifying existing ones  
✅ Clean API — actions delegate to current state  
✅ Inventory management built-in  
