# Ride Sharing System

## Problem Statement
Design a ride-sharing system (like Uber/Lyft) that matches riders with nearby drivers, manages ride lifecycle, calculates fares, and tracks ride history.

## Requirements
- Rider and driver registration with location tracking
- Ride request matching with nearest available driver
- Ride lifecycle: REQUESTED → ACCEPTED → IN_PROGRESS → COMPLETED (or CANCELLED)
- Fare calculation based on distance
- Driver availability management
- Ride history for riders and drivers
- Rating system for riders and drivers

## Class Diagram

```mermaid
classDiagram
    class RideSharingService {
        +registerRider(Rider) void
        +registerDriver(Driver) void
        +requestRide(String riderId, Location pickup, Location dropoff) Ride
        +acceptRide(String driverId, String rideId) void
        +startRide(String rideId) void
        +completeRide(String rideId) void
        +cancelRide(String rideId) void
        +rateRide(String rideId, double rating) void
    }

    class Rider {
        -String id
        -String name
        -Location currentLocation
        -List~Ride~ rideHistory
    }

    class Driver {
        -String id
        -String name
        -Location currentLocation
        -DriverStatus status
        -double rating
    }

    class Ride {
        -String id
        -Rider rider
        -Driver driver
        -Location pickup
        -Location dropoff
        -RideStatus status
        -double fare
    }

    class Location {
        -double latitude
        -double longitude
        +distanceTo(Location other) double
    }

    class RideStatus {
        <<enumeration>>
        REQUESTED
        ACCEPTED
        IN_PROGRESS
        COMPLETED
        CANCELLED
    }

    class DriverStatus {
        <<enumeration>>
        AVAILABLE
        ON_RIDE
        OFFLINE
    }

    RideSharingService --> Rider : manages
    RideSharingService --> Driver : manages
    RideSharingService --> Ride : manages
    Ride --> Rider : has
    Ride --> Driver : assigned to
    Ride --> Location : pickup/dropoff
    Ride --> RideStatus : has
    Driver --> DriverStatus : has
```

> **Note:** This project is currently a stub. The class diagram above represents a suggested design for implementation.

## Potential Discussion Points
- How to implement surge pricing based on demand?
- How to handle ride pooling (shared rides)?
- How to implement driver matching with ETA optimization?
- How to add payment integration?
- How to handle real-time location updates and route tracking?
