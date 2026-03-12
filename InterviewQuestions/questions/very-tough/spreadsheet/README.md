# Spreadsheet

## Problem Statement
Design a spreadsheet application that supports cell editing, formula evaluation with cell references, dependency tracking, and automatic recalculation when cells change.

## Requirements
- Grid of cells addressable by column-row notation (e.g., A1, B2)
- Cells can hold raw values (numbers, text) or formulas (e.g., `=A1+B2`)
- Formula evaluation with support for basic arithmetic and cell references
- Dependency tracking — cells that reference other cells
- Automatic recalculation when a referenced cell changes
- Circular dependency detection

## Class Diagram

```mermaid
classDiagram
    class Spreadsheet {
        -Map~String, Cell~ cells
        +setCell(String cellId, String content) void
        +getCell(String cellId) Cell
        +getValue(String cellId) Object
        +getDisplayValue(String cellId) String
    }

    class Cell {
        -String id
        -String rawContent
        -Object evaluatedValue
        -boolean isFormula
        -Set~String~ dependencies
        -Set~String~ dependents
        +evaluate(Spreadsheet context) Object
        +setContent(String content) void
    }

    class FormulaParser {
        +parse(String formula) Expression
        +extractReferences(String formula) Set~String~
    }

    class Expression {
        <<interface>>
        +evaluate(Spreadsheet context) double
    }

    class NumberExpression {
        -double value
        +evaluate(Spreadsheet context) double
    }

    class CellReferenceExpression {
        -String cellId
        +evaluate(Spreadsheet context) double
    }

    class BinaryExpression {
        -Expression left
        -Expression right
        -char operator
        +evaluate(Spreadsheet context) double
    }

    Spreadsheet --> Cell : contains
    Cell --> Expression : evaluates
    FormulaParser --> Expression : creates
    NumberExpression ..|> Expression
    CellReferenceExpression ..|> Expression
    BinaryExpression ..|> Expression
```

> **Note:** This project is currently a stub. The class diagram above represents a suggested design for implementation.

## Potential Discussion Points
- How to implement topological sort for recalculation order?
- How to detect and handle circular dependencies?
- How to support range-based functions (SUM, AVERAGE)?
- How to implement undo/redo with the Memento pattern?
- How to handle concurrent edits in a collaborative spreadsheet?
