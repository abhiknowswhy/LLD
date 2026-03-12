# Splitwise Application

## Problem Statement
Design an expense-splitting application (like Splitwise) that tracks shared expenses among groups of users, calculates balances, and simplifies debts.

## Requirements
- User registration and group management
- Add expenses with different split strategies (equal, exact, percentage)
- Track balances between pairs of users
- Simplify debts to minimize number of transactions
- Expense history per user and per group
- Support for multiple groups per user

## Class Diagram

```mermaid
classDiagram
    class SplitwiseService {
        +registerUser(User) void
        +createGroup(String name, List~User~ members) Group
        +addExpense(String groupId, String payerId, double amount, SplitStrategy) Expense
        +getBalances(String userId) Map~String, Double~
        +simplifyDebts(String groupId) List~Payment~
        +getExpenseHistory(String userId) List~Expense~
    }

    class User {
        -String id
        -String name
        -String email
        -Map~String, Double~ balances
    }

    class Group {
        -String id
        -String name
        -List~User~ members
        -List~Expense~ expenses
    }

    class Expense {
        -String id
        -User paidBy
        -double amount
        -String description
        -SplitStrategy splitStrategy
        -Map~User, Double~ splits
    }

    class SplitStrategy {
        <<interface>>
        +split(double amount, List~User~ participants) Map~User, Double~
    }

    class EqualSplit {
        +split(double, List~User~) Map~User, Double~
    }

    class ExactSplit {
        -Map~User, Double~ exactAmounts
        +split(double, List~User~) Map~User, Double~
    }

    class PercentageSplit {
        -Map~User, Double~ percentages
        +split(double, List~User~) Map~User, Double~
    }

    class Payment {
        -User from
        -User to
        -double amount
    }

    SplitwiseService --> User : manages
    SplitwiseService --> Group : manages
    Group --> User : has members
    Group --> Expense : tracks
    Expense --> User : paid by
    Expense --> SplitStrategy : uses
    EqualSplit ..|> SplitStrategy
    ExactSplit ..|> SplitStrategy
    PercentageSplit ..|> SplitStrategy
```

> **Note:** This project is currently a stub. The class diagram above represents a suggested design for implementation.

## Potential Discussion Points
- How to implement debt simplification using a greedy algorithm?
- How to handle multi-currency expenses?
- How to add recurring expenses?
- How to implement settlement tracking (marking debts as paid)?
- How to handle unequal group sizes and partial participation?
