# Stock Exchange

## Problem Statement
Design a stock exchange system that supports order placement (buy/sell), order matching with price-time priority, trade execution, and order book management.

## Requirements
- User and stock registration
- Place buy and sell orders with price and quantity
- Order matching engine with price-time priority (best price first, then earliest)
- Partial order fills and remaining quantity tracking
- Trade execution with transaction records
- Order book display (aggregated buy/sell sides)
- Order cancellation

## Class Diagram

```mermaid
classDiagram
    class StockExchange {
        -Map~String, OrderBook~ orderBooks
        +registerStock(Stock) void
        +placeOrder(Order) List~Trade~
        +cancelOrder(String orderId) void
        +getOrderBook(String stockSymbol) OrderBook
    }

    class OrderBook {
        -String stockSymbol
        -TreeMap~Double, Queue~Order~~ buyOrders
        -TreeMap~Double, Queue~Order~~ sellOrders
        +addOrder(Order) List~Trade~
        +cancelOrder(String orderId) void
        +getBestBid() double
        +getBestAsk() double
    }

    class Order {
        -String id
        -String userId
        -String stockSymbol
        -OrderType type
        -double price
        -int quantity
        -int filledQuantity
        -OrderStatus status
        -Instant timestamp
        +getRemainingQuantity() int
        +fill(int qty) void
    }

    class Trade {
        -String id
        -Order buyOrder
        -Order sellOrder
        -double price
        -int quantity
        -Instant timestamp
    }

    class Stock {
        -String symbol
        -String name
        -double lastPrice
    }

    class OrderType {
        <<enumeration>>
        BUY
        SELL
    }

    class OrderStatus {
        <<enumeration>>
        OPEN
        PARTIALLY_FILLED
        FILLED
        CANCELLED
    }

    StockExchange --> OrderBook : manages per stock
    OrderBook --> Order : contains buy/sell
    StockExchange --> Trade : executes
    Trade --> Order : matches buy+sell
    Order --> OrderType : has
    Order --> OrderStatus : has
    StockExchange --> Stock : lists
```

> **Note:** This project is currently a stub. The class diagram above represents a suggested design for implementation.

## Potential Discussion Points
- How to implement market orders (execute at best available price)?
- How to handle stop-loss and limit orders?
- How to add real-time price streaming?
- How to make the matching engine thread-safe for high throughput?
- How to implement circuit breakers (halt trading on extreme volatility)?
