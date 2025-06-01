# Duck Script Interpreter - Interpreter Pattern Implementation

## Overview

This project demonstrates the **Interpreter Pattern** by implementing a domain-specific language (DSL) for creating and manipulating duck objects. The interpreter can parse and execute scripts that create different types of ducks and modify their behaviors dynamically.

**Key Architecture Feature**: This implementation **imports and reuses** the duck classes and behaviors from the `dp01_strategy_ducks` module instead of duplicating code, demonstrating proper module dependency management and the DRY (Don't Repeat Yourself) principle.

## Module Dependencies

```xml
<dependency>
    <groupId>pattern</groupId>
    <artifactId>dp01_strategy_ducks</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

This dependency provides:
- **Duck base class** with name support and enhanced methods
- **Duck implementations**: MallardDuck, RubberDuck, DecoyDuck
- **Behavior interfaces**: FlyBehavior, QuackBehavior  
- **Behavior implementations**: FlyWithWings, FlyNoWay, Quack, Squeak, MuteQuack

## Pattern Components

### 1. Expression Interface
- `Expression` - Defines the interpret method that all concrete expressions must implement

### 2. Context
- `Context` - Maintains the state of the interpreter, storing created duck objects

### 3. Concrete Expressions
- `CreateDuckExpression` - Creates new duck instances
- `SetBehaviorExpression` - Modifies duck behaviors  
- `CallMethodExpression` - Invokes duck methods
- `ListDucksExpression` - Lists all created ducks

### 4. Parser
- `DuckScriptParser` - Converts text commands into Expression objects

### 5. Interpreter
- `DuckScriptInterpreter` - Orchestrates the parsing and execution of scripts

## Supported Commands

### CREATE DUCK
```
CREATE DUCK <name> <type>
```
Creates a new duck instance. Supported types:
- `MALLARD` - Real duck that can fly and quack
- `RUBBER` - Toy duck that can't fly but squeaks
- `DECOY` - Fake duck that can't fly or make noise

### SET Behavior
```
SET <duck_name> <behavior_type> <behavior_value>
```
Modifies duck behaviors:

**Fly Behaviors:**
- `FLY_WITH_WINGS` - Normal flying
- `FLY_NO_WAY` - Cannot fly

**Quack Behaviors:**
- `QUACK` - Normal quacking
- `SQUEAK` - Squeaking sound
- `MUTE_QUACK` - Silent

### CALL Method
```
CALL <duck_name> <method>
```
Invokes duck methods:
- `DISPLAY` - Shows duck description
- `FLY` - Executes fly behavior
- `QUACK` - Executes quack behavior
- `SWIM` - Makes duck swim

### LIST DUCKS
```
LIST DUCKS
```
Shows all created ducks with their types.

## Example Script

```
// Create different types of ducks
CREATE DUCK Donald MALLARD
CREATE DUCK Rubber RUBBER
CREATE DUCK Woody DECOY

// List all created ducks
LIST DUCKS

// Test behaviors
CALL Donald DISPLAY
CALL Donald FLY
CALL Donald QUACK

// Modify behaviors
SET Donald FLY_BEHAVIOR FLY_NO_WAY
SET Rubber QUACK_BEHAVIOR QUACK

// Test modified behaviors
CALL Donald FLY
CALL Rubber QUACK
```

## Design Patterns Used

1. **Interpreter Pattern** - Main pattern for parsing and executing the DSL
2. **Strategy Pattern** - Used for duck behaviors (from dp01_strategy_ducks)
3. **Template Method Pattern** - Abstract Duck class with concrete implementations

## Architecture Benefits

- **Extensible Grammar**: Easy to add new commands and expressions
- **Separation of Concerns**: Parser, interpreter, and execution logic are separate
- **Runtime Behavior Modification**: Duck behaviors can be changed during execution
- **Type Safety**: Strong typing for duck types and behaviors
- **Error Handling**: Comprehensive error messages for invalid commands

## Running the Application

First, ensure the strategy ducks module is built and installed:

```bash
cd ../dp01_strategy_ducks
mvn clean install
```

Then run the interpreter:

```bash
cd dp18_interpreter__
mvn compile exec:java
```

The application will execute a demonstration script and show various interpreter capabilities including error handling and runtime behavior modification.
