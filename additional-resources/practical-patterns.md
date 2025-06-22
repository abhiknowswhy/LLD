

# 🎯 Summary: Categorizing Beyond GoF


| Category                           | Examples                                               |
| ---------------------------------- | ------------------------------------------------------ |
| **Object-Oriented Patterns (GoF)** | Strategy, Factory, Observer                            |
| **Architecture Patterns**          | Microservices, Layered, Hexagonal, Serverless          |
| **Concurrency Patterns**           | Actor, Fork/Join, Thread Pool, Future/Promise          |
| **Distributed System Patterns**    | Event Sourcing, Saga, Leader Election, Gossip Protocol |
| **Reactive/Event-Driven**          | Event Loop, Pub-Sub, Command Bus                       |
| **Data-Oriented**                  | CQRS, Dataflow, Blackboards, Materialized Views        |
| **Fault Tolerance**                | Circuit Breaker, Retry, Supervisor Tree, Bulkhead      |



Here's a curated list of **non-GoF design patterns** that are **very useful for backend, systems programming, infra, and distributed systems**


---

## 🔧 **Practical System & Infra Design Patterns (Non-GoF)**

| Pattern                                             | Category                 | Description                                                                     | Real-world Usage                        |
| --------------------------------------------------- | ------------------------ | ------------------------------------------------------------------------------- | --------------------------------------- |
| **Lightswitch**                                     | Concurrency              | Manage access to shared resource; common in reader-writer lock implementations. | OS, thread libraries                    |
| **Janitor** (RAII)                                  | Resource Management      | Automatically clean up resources when no longer needed.                         | C++, Rust, `defer` in Go                |
| **Reactor**                                         | Event-Driven             | Handle I/O using non-blocking events with a single loop.                        | `libuv`, Node.js, nginx                 |
| **Acceptor**                                        | Networking               | Accepts client connections and dispatches to handlers.                          | Servers, RPC, gRPC internals            |
| **Leader Election**                                 | Distributed Systems      | Elect a leader among nodes to avoid split-brain scenarios.                      | ZooKeeper, Kubernetes leader election   |
| **Circuit Breaker**                                 | Resilience               | Prevents system overload by "breaking" when downstream fails repeatedly.        | Netflix Hystrix, Resilience4j           |
| **Bulkhead**                                        | Resilience               | Isolate parts of the system to prevent cascading failures.                      | Microservice isolation                  |
| **Retry with Backoff**                              | Reliability              | Retry failed operations with increasing delays.                                 | Cloud SDKs, Kafka, S3 clients           |
| **Scheduler**                                       | Systems/Infra            | Periodically trigger jobs at fixed or dynamic times.                            | Cron, Airflow, Kubernetes               |
| **Rate Limiter**                                    | Throttling               | Control the rate of requests to protect services.                               | API Gateways, Redis leaky/token bucket  |
| **Work Queue / Task Queue**                         | Async Processing         | Distribute tasks among workers; decouple producers/consumers.                   | Celery, RabbitMQ, Kafka                 |
| **Sidecar**                                         | Deployment               | A helper container alongside the main container in a pod.                       | Kubernetes (e.g., logging agent, envoy) |
| **Ambassador**                                      | Networking/Proxy         | Proxy service that manages access to other services.                            | API gateway, envoy, Istio               |
| **Saga**                                            | Distributed Transactions | Manage long-lived transactions across services using compensating actions.      | Microservices, DB transactions          |
| **Event Sourcing**                                  | State Management         | Store system state as a sequence of events.                                     | Kafka, CQRS systems                     |
| **CQRS (Command Query Responsibility Segregation)** | Architecture             | Separate reads and writes into different models.                                | Event-sourced systems, DDD              |

---

## 🧠 Bonus: Where to Learn More

* 📘 **"Designing Data-Intensive Applications"** by Martin Kleppmann – gold standard.
* 🌐 **Cloud Native Patterns** by Cornelia Davis.
* 📗 **Pattern-Oriented Software Architecture** series.
* 🎓 **Kubernetes Patterns** book – very helpful for infra-related patterns.
* 📄 Case studies/postmortems from Netflix, Uber, Stripe, etc.

---

Would you like a **Notion template** or a **visual mind map** version of this to track your learning across these patterns and tools (like Kafka, K8s, etc.)?
