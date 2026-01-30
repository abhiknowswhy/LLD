# Design Patterns Classification

This document provides a comprehensive classification of all 23 Gang of Four (GoF) design patterns organized by their type.

---

## Creational Patterns (5)

Creational patterns deal with object creation mechanisms. They abstract the instantiation process to make systems independent of how their objects are composed and represented.

| # | Pattern Name | Purpose | Key Characteristics |
|---|---|---|---|
| 1 | **Singleton** | Ensure a class has only one instance and provide a global point of access to it | Single instance, lazy/eager initialization, thread-safe |
| 2 | **Factory Method** | Define an interface for creating an object, but let subclasses decide which class to instantiate | Encapsulation of object creation, polymorphism |
| 3 | **Abstract Factory** | Provide an interface for creating families of related or dependent objects without specifying their concrete classes | Family of related objects, multiple factories, consistency |
| 4 | **Builder** | Separate the construction of a complex object from its representation so that the same construction process can create different representations | Step-by-step construction, immutability, complex objects |
| 5 | **Prototype** | Specify the kinds of objects to create using a prototypical instance and create new objects by copying this prototype | Object cloning, avoid subclassing, dynamic object creation |

---

## Structural Patterns (7)

Structural patterns deal with object composition, creating relationships between entities to form larger structures. They help ensure that when one part changes, the entire structure doesn't need to change.

| # | Pattern Name | Purpose | Key Characteristics |
|---|---|---|---|
| 1 | **Adapter** | Convert the interface of a class into another interface clients expect | Interface compatibility, two-way adaptation, wrapper |
| 2 | **Bridge** | Decouple an abstraction from its implementation so that the two can vary independently | Separate abstraction and implementation, multiple hierarchies |
| 3 | **Composite** | Compose objects into tree structures to represent part-whole hierarchies | Recursive composition, uniform interface, tree-like structures |
| 4 | **Decorator** | Attach additional responsibilities to an object dynamically | Enhanced functionality, alternative to subclassing, wrapping |
| 5 | **Facade** | Provide a unified, simplified interface to a set of interfaces in a subsystem | Simplified access, subsystem hiding, unified entry point |
| 6 | **Flyweight** | Use sharing to support large numbers of fine-grained objects efficiently | Shared state, memory optimization, intrinsic/extrinsic state |
| 7 | **Proxy** | Provide a surrogate or placeholder for another object to control access to it | Lazy initialization, access control, logging/caching |

---

## Behavioral Patterns (11)

Behavioral patterns deal with object collaboration and the distribution of responsibility. They focus on algorithms, communication between objects, and division of responsibility.

| # | Pattern Name | Purpose | Key Characteristics |
|---|---|---|---|
| 1 | **Observer** | Define a one-to-many dependency between objects so that when one object changes state, all its dependents are notified automatically | Loose coupling, publish-subscribe, event handling |
| 2 | **Strategy** | Define a family of algorithms, encapsulate each one, and make them interchangeable | Algorithm encapsulation, runtime behavior change, flexibility |
| 3 | **Command** | Encapsulate a request as an object, thereby letting you parameterize clients with different requests | Request encapsulation, undo/redo, queuing requests |
| 4 | **State** | Allow an object to alter its behavior when its internal state changes | State-based behavior, state machine, encapsulated states |
| 5 | **Template Method** | Define the skeleton of an algorithm in a base class, letting subclasses override specific steps | Algorithm structure, code reuse, polymorphic steps |
| 6 | **Iterator** | Provide a way to access the elements of an aggregate object sequentially without exposing its underlying representation | Sequential access, abstraction of collection, traversal |
| 7 | **Mediator** | Define an object that encapsulates how a set of objects interact | Centralized communication, loose coupling, complex interactions |
| 8 | **Memento** | Without violating encapsulation, capture and externalize an object's internal state so that the object can be restored to this state later | State capture, undo functionality, encapsulation preservation |
| 9 | **Chain of Responsibility** | Avoid coupling the sender of a request to its receiver by giving more than one object a chance to handle the request | Decoupled handlers, request passing, flexible handling |
| 10 | **Visitor** | Represent an operation to be performed on the elements of an object structure | Operations on complex structures, separation of concerns, double dispatch |
| 11 | **Interpreter** | Define a representation for a grammar and an interpreter to interpret sentences in that language | Language implementation, grammar definition, expression evaluation |

---

## Quick Reference by Use Case

### When to Use Each Pattern

**Object Creation Issues?** → Creational Patterns
- Need single instance: **Singleton**
- Need flexible object creation: **Factory Method** or **Abstract Factory**
- Building complex objects: **Builder**
- Cloning objects: **Prototype**

**Structure/Composition Issues?** → Structural Patterns
- Need interface compatibility: **Adapter**
- Need to separate abstraction and implementation: **Bridge**
- Need tree structures: **Composite**
- Need to add features dynamically: **Decorator**
- Need simplified interface to complex subsystem: **Facade**
- Performance issues with many objects: **Flyweight**
- Need controlled access: **Proxy**

**Communication/Behavior Issues?** → Behavioral Patterns
- Need loose coupling between objects: **Observer**
- Need to switch behaviors: **Strategy**
- Need to encapsulate requests: **Command**
- Need state-dependent behavior: **State**
- Need algorithm structure: **Template Method**
- Need to traverse collections: **Iterator**
- Need centralized communication: **Mediator**
- Need undo/redo functionality: **Memento**
- Need flexible request handling: **Chain of Responsibility**
- Need operations on complex structures: **Visitor**
- Need language interpretation: **Interpreter**

---

## Complexity Matrix

| Complexity | Patterns |
|---|---|
| **Easy** | Singleton, Factory Method, Strategy, Observer, Iterator |
| **Medium** | Builder, Adapter, Facade, Template Method, Decorator, Command |
| **Advanced** | Abstract Factory, Bridge, Composite, Proxy, State, Mediator, Memento, Chain of Responsibility, Visitor, Interpreter, Flyweight |

