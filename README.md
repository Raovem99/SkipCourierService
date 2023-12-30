 Solution Design Document: SkipTheDishes Courier Statement Calculation System

 Introduction
This document outlines the solution design for the SkipTheDishes challenge, focusing on developing a Java-based application that subscribes to specific events, calculates courier statements, and provides REST endpoints.

Objective
- To calculate weekly payments for couriers based on various events like deliveries, adjustments, and bonuses.
- To provide REST endpoints for accessing delivery transactions by period and courier and for retrieving weekly courier statements.

System Overview

1. Event Subscription:
   - Subscribe to events: `DeliveryCreated`, `AdjustmentModified`, and `BonusModified`.
   - Event payloads include various attributes like `deliveryId`, `courierId`, `timestamp`, and `value`.

2. Data Processing:
   - Calculate delivery transactions by summing values for each `deliveryId`.
   - Aggregate transactions to compute weekly courier statements.

3. REST API Endpoints:
   - Endpoint to return delivery transactions for a specified period and courier.
   - Endpoint to return weekly courier statements for a specific courier.

4. Technologies and Tools:
   - Java for backend development.
   - Apache Kafka for event subscription and processing.
   - Spring Boot for REST API development.
   - A relational database (My SQL) for data persistence.

Detailed Design

1. Event Handling with Kafka:
   - Kafka consumers subscribe to topics corresponding to the events.
   - Process events to update or create records in the database.

Detailed Design for Event Handling with Kafka in the SkipTheDishes Project

Overview
In the SkipTheDishes courier statement calculation system, Kafka plays a crucial role in handling event-driven data flow. The system subscribes to specific events (`DeliveryCreated`, `AdjustmentModified`, and `BonusModified`) and processes these events to calculate and store relevant data.

 Kafka Event Handling Architecture

1. Kafka Topics:
   - Three Kafka topics corresponding to the events:
     - `DeliveryTopic'
     - `AdjustmentsTopic`
     - `BonusTopic'

2. Kafka Producers:
   - External systems or services produce events to these Kafka topics.
   - Producers serialize event data into a suitable format (e.g., JSON).

3. Kafka Consumers:
   - The application consumes messages from these topics.
   - Each consumer group is dedicated to a specific topic.

4. Event Processing:
   - Once an event is consumed, the application processes it according to its type.
   - Updates or creates records in the database based on the event data.

Kafka Consumer Configuration

- Utilize Spring Boot’s `@KafkaListener` annotation to create listeners for each topic.
- Configure consumers with `StringDeserializer` for key and a custom `JsonDeserializer` for the value to handle JSON formatted messages.

 Event Data Models

- DeliveryCreatedEvent
  - `deliveryId`, `courierId`, `createdTimestamp`, `value`
- AdjustmentModifiedEvent:
  - `adjustmentId`, `deliveryId`, `modifiedTimestamp`, `value`
- BonusModifiedEvent:
  - `bonusId`, `deliveryId`, `modifiedTimestamp`, `value`

 Event Processing Logic

 DeliveryCreatedEvent:
   - Create or update a delivery record.
   - Associate the delivery with the courier.

 AdjustmentModifiedEvent and BonusModifiedEvent:
   - Update the delivery record with new adjustment or bonus values.
   - Recalculate the total value for the delivery.

Database Integration
Detailed Design for Database Integration in the SkipTheDishes Project

 Overview
The database integration in the SkipTheDishes courier statement calculation system is central to storing and retrieving data related to couriers, deliveries, adjustments, and bonuses. It involves designing a relational database schema, integrating it with the Spring Boot application, and ensuring efficient data operations.

Database Schema Design

1. Tables:
   - `Couriers`: Stores information about couriers.
   - `Deliveries`: Records each delivery event.
   - `Adjustments`: Captures adjustments made to deliveries.
   - `Bonuses`: Details bonuses associated with deliveries.

2. Table Structures:
   - `Couriers`: `courierId`, `name`, other relevant details.
   - `Deliveries`: `deliveryId`, `courierId`, `createdTimestamp`, `value`.
   - `Adjustments`: `adjustmentId`, `deliveryId`, `modifiedTimestamp`, `value`.
   - `Bonuses`: `bonusId`, `deliveryId`, `modifiedTimestamp`, `value`.
  

3. Relationships:
   - `Deliveries` is related to `Couriers` through `courierId`.
   - `Adjustments` and `Bonuses` are related to `Deliveries` through `deliveryId`.
   

Database Integration with Spring Boot

1. ORM (Object-Relational Mapping):
   - Utilize Spring Data JPA for ORM.
   - Create entity classes corresponding to each table.

2. Repository Layer:
   - Implement JPA repositories for CRUD operations.
   - Use Spring Data’s method query derivation 

 Data Operations and Business Logic

1. Event Processing:
   - On receiving a Kafka event, update the corresponding tables. For example, add a new record in `Deliveries` for a `DeliveryCreated` event.

2. Courier Statement Calculation:
   - Aggregate data from `Deliveries`, `Adjustments`, and `Bonuses` to calculate the weekly statement.
   - Store the result in the `Delivery Transaction`.

3. Data Retrieval for API Endpoints:
   - Implemented methods in the service layer to fetch data based on API requests, such as fetching courier statements for a given period.

 Error Handling and Validation

- Implemented error handling in the service layer to manage exceptions during database operations.


 Performance Considerations

- Use indexing on frequently queried columns like `courierId` and `deliveryId`.
- Optimize query performance using JPA query hints and proper join fetches.

Conclusion

The database integration design provides a structured and efficient approach to managing data in the SkipTheDishes application. By leveraging Spring Data JPA and following best practices for ORM, the system ensures robust and scalable database operations aligned with the application's event-driven architecture.


3. Service Layer:
   Detailed Design of the `DeliveryTransactionService` in the service layer

 Overview
The `DeliveryTransactionService` class in the SkipTheDishes project is a crucial component of the service layer. It handles the business logic related to delivery transactions, including fetching transactions, calculating weekly statements for couriers, and aggregating deliveries with bonuses and adjustments.

 Class Structure and Dependencies
- Dependencies: 
  - `DeliveryRepository`
  - `BonusRepository`
  - `AdjustmentRepository`
- Annotations:
  - `@Service`: Marks the class as a Spring-managed service.

Key Methods

1. getDeliveryTransactionsByCourierAndPeriod:
   - Purpose: Fetches delivery transactions for a specific courier within a given period.
   - Parameters: `String courierId`, `LocalDate startDate`, `LocalDate endDate`.
   - Process: Retrieves deliveries from `deliveryRepository` using the courier ID and date range, then aggregates them into a `DeliveryTransaction`.

2. getWeeklyStatementForCourier:
   - Purpose: Calculates the weekly statement for a specific courier.
   - Parameters: `String courierId`.
   - Process: Determines the current week's date range and delegates to `getDeliveryTransactionsByCourierAndPeriod` for data aggregation.

3. getAllDeliveries:
   - Purpose: Retrieves all delivery transactions.
   - Process: Fetches all deliveries from `deliveryRepository` and aggregates them into a `DeliveryTransaction`.

4. getDeliveryTransaction (private method):
   - Purpose: Aggregates deliveries, bonuses, and adjustments into a single `DeliveryTransaction`.
   - Parameters: `List<Delivery> deliveries`.
   - Process:
     - Fetches bonuses and adjustments associated with each delivery.
     - Calculates the total values for deliveries, bonuses, and adjustments.
     - Creates and returns a `DeliveryTransaction` object with aggregated data.

Business Logic

- The service primarily focuses on aggregating data related to deliveries, bonuses, and adjustments.
- It computes the total transaction value by summing up the values of deliveries, bonuses, and adjustments.
- This aggregation is essential for generating courier statements and transaction details.

 Conclusion

The `DeliveryTransactionService` is a well-structured component that effectively handles the business logic related to delivery transactions in the SkipTheDishes project. Enhancements in error handling, transaction management, and performance optimization could further improve its functionality and robustness.

4. REST Controllers:
 Detailed Design of Controller Classes

Overview
The project includes two primary controller classes, `CourierStatementController` and `DeliveryTransactionController`. These controllers are responsible for handling HTTP requests related to courier statements and delivery transactions, respectively.

 1. `CourierStatementController`
- Key Methods:
  1. getWeeklyStatement:
     - Endpoint: `GET /{courierId}`
     - Purpose: Fetches the weekly statement for a specified courier.
     - Parameters: `String courierId` (Path Variable)
     - Process: Calls `deliveryTransactionService.getWeeklyStatementForCourier(courierId)` and returns the result.
     - Error Handling: Catches exceptions and logs them. Returns an error response in case of failure.

  2. getAllDeliveryStatement:
     - Endpoint: `GET /getAllDeliveryStatement`
     - Purpose: Retrieves all delivery transactions.
     - Process: Calls `deliveryTransactionService.getAllDeliveries()` and returns the result.
        - Error Handling: Catches exceptions and logs them. Returns an error response in case of failure.

 2. `DeliveryTransactionController`

- Key Method:
  - getDeliveryTransactions:
    - Endpoint: `GET`
    - Purpose: Fetches delivery transactions for a specified courier within a date range.
    - Parameters: `String courierId`, `LocalDate startDate`, `LocalDate endDate` (Request Parameters)
    - Process: Invokes `deliveryTransactionService.getDeliveryTransactionsByCourierAndPeriod` and returns the result.
    - Error Handling: Catches exceptions, logs them, and returns an appropriate response.


 Testing Strategy

- Unit Testing: Test controller methods in isolation using mock objects for service dependencies.
- Integration Testing: Test the actual interaction with the service layer and the HTTP request-response cycle.

 Conclusion

The controller classes in the SkipTheDishes project are well-structured to handle specific RESTful endpoints related to courier statements and delivery transactions. Enhancements in error handling, response structuring, and security can further improve their robustness and usability.

6. Error Handling and Validation:
   Detailed Design for Error Handling and Exceptions in the SkipTheDishes Project

The effective management of errors and exceptions is crucial for maintaining the robustness and reliability of the SkipTheDishes project. This involves a structured approach to error handling across different layers of the application.

1. Global Exception Handling

- Implementation:
  - Utilize `@ControllerAdvice` to create a global exception handler (`GlobalExceptionHandler`).
  - Centralize exception handling for the entire application, ensuring consistent error responses.

- Key Features:
  - Custom error response class (`ErrorResponse`) to standardize the format of error messages.
  - Handlers for different types of exceptions, each annotated with `@ExceptionHandler`.
  - Use `@ResponseStatus` to set appropriate HTTP status codes based on the exception type.
  - Log exceptions for audit and debugging purposes.

 2. Service Layer Exception Handling

- Implementation:
  - Each service method includes try-catch blocks to handle exceptions that are specific to the business logic.
  - Throw custom exceptions that encapsulate specific error scenarios or business rule violations.

3. Controller Layer Exception Handling

- Implementation:
  - Rely on `GlobalExceptionHandler` for handling general exceptions.
  - Use `@ExceptionHandler` within controllers for handling specific exceptions related to controller logic.
  - Ensure proper HTTP response statuses and messages are returned.

- Validation Errors:
  - Handle `MethodArgumentNotValidException` for request validation errors.
  - Return detailed information about validation failures.

 4. Kafka Exception Handling

- Consumer and Listener Exception Handling:
  - Implement error handling within Kafka listeners (`@KafkaListener`) to manage exceptions during message consumption and processing.
  - Log errors and decide on a retry or dead-letter queue strategy for unprocessable messages.

- Producer Exception Handling:
  - Handle exceptions in Kafka producer services (`KafkaProducerService`).
  - Utilize callbacks and futures to handle asynchronous sending errors.

5. Database and Repository Layer Exception Handling

- Implementation:
  - Wrap database operations in try-catch blocks within repository methods or service methods interacting with the database.
  - Translate database-specific exceptions into more generic exceptions or custom-defined exceptions.

 6. Logging and Monitoring

- Integrate a logging framework (like SLF4J with Logback or Log4J2).
- Log exceptions at appropriate levels (error).


 Conclusion

A comprehensive error and exception handling strategy in the SkipTheDishes project enhances its resilience and reliability. By standardizing responses, logging critical information, and carefully managing exceptions at different application layers, the system can gracefully handle errors and provide useful feedback to users and developers. This approach also aids in maintaining and troubleshooting the application effectively.

 


 Conclusion

This solution provides a robust and scalable system for processing courier-related events and providing access to courier statements through REST endpoints. It leverages modern technologies like Kafka and Spring Boot to build a reliable and efficient application.
