
# Maybank Exam

This is a Spring Boot application for the Maybank Exam project.

## Getting Started

Follow the steps below to set up and run the application.

### Prerequisites

-   Java Development Kit (JDK) 8 or higher
-   Maven
-   MySQL database
-   Jasper 

### Configuration

1.  Clone the repository:

`git clone https://github.com/audyafiary1104/maybank_test.git` 

2.  Open the project in your preferred IDE.
    
3.  Configure the following properties in the `application.properties` file located in the `src/main/resources` directory:


`# GitHub API token (replace with your own token)

    api.github.token=YOUR_GITHUB_API_TOKEN

# MySQL database connection settings

    spring.datasource.url=jdbc:mysql://localhost:3306/maybank_test?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&createDatabaseIfNotExist=true
    spring.datasource.username=YOUR_DB_USERNAME
    spring.datasource.password=YOUR_DB_PASSWORD` 


### Running the Application

To run the application, follow these steps:

1.  Open a terminal or command prompt.
    
2.  Navigate to the project directory.
    
3.  Build the application using Maven:
    
    Copy code
    
    `mvn clean install` 
    
4.  Run the application:
    
    `mvn spring-boot:run` 
    
    The application will start running on the configured port (default is 8080).
    

### Importing Postman Collection

To import the Postman collection and test the API endpoints, follow these steps:

1.  Open Postman.
    
2.  Click on "Import" in the top-left corner.
    
3.  Select the "Import From Link" tab.
    
4.  Enter the following link and click "Continue":
    
`https://raw.githubusercontent.com/audyafiary1104/maybank_test/master/postman_collection.json` 

    
5.  The collection will be imported into Postman. You can now explore the available API endpoints and send requests.
    

### Folder Structure and Purpose

The folder structure of the Maybank Test application is as follows:

-   `src/main/java`: Contains the Java source code for the application.
    -   `com.maybank.test.adapter`: Contains the adapter classes for interacting with external systems (e.g., GitHub API).
    -   `com.maybank.test.controller`: Contains the REST API controller classes.
    -   `com.maybank.test.dto`: Contains the DTO (Data Transfer Object) classes used for request and response payloads.
    -   `com.maybank.test.entity`: Contains the entity classes representing database tables.
    -   `com.maybank.test.exception`: Contains the exception classes used for custom error handling.
    -   `com.maybank.test.repository`: Contains the repository interfaces for database operations.
    -   `com.maybank.test.service`: Contains the service interfaces and implementations.
    -   `com.maybank.test.util`: Contains utility classes and constants.
-   `src/main/resources`: Contains the application configuration files.
-   `src/test/java`: Contains the unit tests for the application.
-   `postman_collection.json`: Contains the Postman collection for testing the API endpoints.

### Class Details


1.  `SearchDto` (DTO - Data Transfer Object):
    
    -   Represents the request payload for searching users in the GitHub API.
    -   Contains properties such as search query, page number, order, sort, and size.
2.  `GithubResponse` (DTO):
    
    -   Represents the response from the GitHub API when searching for users.
    -   Contains properties such as total count and a list of user items.
3.  `GithubClient` (Adapter):
    
    -   Acts as a client to interact with the GitHub API.
    -   Uses `RestTemplate` to send a GET request to the API endpoint.
    -   Constructs the URL with search parameters from the `SearchDto`.
    -   Handles the response and throws a custom exception if no records are found.
4.  `Report` (Entity):
    
    -   Represents the report entity with properties like ID, filename, and creation timestamp.
    -   Mapped to the database table for report records.
5.  `HistoryExport` (Entity):
    
    -   Represents the history export entity with properties like ID, report ID, and export timestamp.
    -   Mapped to the database table for history export records.
6.  `ReportRepository` (Repository):
    
    -   Provides the interface for interacting with the database for `Report` entities.
    -   Inherits from `JpaRepository` to perform CRUD operations and other database queries.
7.  `HistoryExportRepository` (Repository):
    
    -   Provides the interface for interacting with the database for `HistoryExport` entities.
    -   Inherits from `JpaRepository` to perform CRUD operations and other database queries.
8.  `ReportServiceImpl` (Service):
    
    -   Implements the business logic for managing reports.
    -   Uses the `ReportRepository` and `HistoryExportRepository` for database operations.
    -   Contains methods like `searchUsers`, `ListReport`, `downloadReport`, and `historyReport` to handle various operations.
9.  `ReportController` (Controller):
    
    -   Acts as the entry point for handling HTTP requests related to reports.
    -   Maps HTTP endpoints like `/`, `/all`, `/download/{id}`, and `/history` to specific methods.
    -   Uses the `ReportServiceImpl` to delegate the request processing and return the response.
10.  `CustomException` (Exception):
    
    -   Represents a custom exception for handling application-specific errors.
    -   Includes properties like response code and error message.
11.  `ResponseCode` (Utility) :
    -   provide information about specific statuses or conditions within the APIs.
12.  `MaybankTestApplication`:
    -   The main entry point of the application.
    -   Configures and bootstraps the Spring Boot application.


## Design Patterns

The following design patterns are utilized in this project:

### 1. Model-View-Controller (MVC) Pattern

The application follows the Model-View-Controller (MVC) architectural pattern. It separates the application into three main components:

-   **Model**: Represents the data and business logic. The models in this project are the entities and DTOs (Data Transfer Objects) used for data representation and transfer.
    
-   **View**: Handles the presentation layer and user interface. In this project, the views are not explicitly implemented as the application primarily serves as an API.
    
-   **Controller**: Manages the interaction between the model and the view. The controllers in this project handle the HTTP requests, perform business logic, and return appropriate responses.
    

### 2. Repository Pattern

The Repository Pattern is used for managing the persistence operations on entities in the database. The repositories in this project are responsible for accessing and manipulating data in the database. They provide an abstraction layer between the application and the underlying data source.

### 3. Data Transfer Object (DTO) Pattern

The Data Transfer Object (DTO) pattern is employed to transfer data between different layers of the application. DTOs are used to encapsulate data and transport it across the application boundaries. In this project, DTOs are used to send and receive data between the controller and service layers.

### 4. Dependency Injection (DI)

The Dependency Injection (DI) pattern is utilized for managing dependencies between components in the application. DI helps decouple the dependencies and allows for easier testing, flexibility, and maintainability. In this project, DI is implemented using the `@Autowired` annotation to inject dependencies into classes.


## API Endpoints

The Maybank Test application exposes the following API endpoints:

-   `GET /report/`: Searches for GitHub users based on the provided search criteria.
-   `GET /report/all`: Retrieves a list of all reports from the database, optionally filtered by report ID.
-   `GET /report/download/{id}`: Downloads a report file based on the provided ID.
-   `GET /report/history`: Retrieves the history of report exports.

### Naming Conventions

-   Package Names: The package name follows the reverse domain name pattern (`com.maybank.test`) to ensure uniqueness and prevent naming conflicts.
-   Class Names: Class names are written in `CamelCase` format, starting with an uppercase letter. For example, `ReportController`, `SearchDto`, and `GithubResponse`.
-   Method Names: Method names are written in `camelCase` format, starting with a lowercase letter. For example, `search`, `listReport`, and `download`.
-   Variable Names: Variable names are also written in `camelCase` format, starting with a lowercase letter. For example, `searchDto`, `reportService`, and `resource`.
-   Constant Names: Constant names are written in uppercase letters, with words separated by underscores (`_`) for readability. For example, `DEFAULT_URI`, `RESPONSE_NO_RECORD`, and `NO_RECORD_MESSAGE`.

These naming conventions help maintain code readability, consistency, and follow best practices for Java development.

Note: It's important to adhere to your team's coding guidelines and standards when it comes to naming conventions, as they may vary from project to project or organization to organization.

### Importing the Postman Collection

To import the Postman collection and test the API endpoints, follow these steps:

1.  Open Postman.
    
2.  Click on the **Import** button.
    
3.  Select the **Link** tab.
    
4.  Enter the following URL:

`https://raw.githubusercontent.com/audyafiary1104/maybank_test/main/postman_collection.json` 

5.  Click the **Import** button.
    
6.  The collection will be imported, and you can now explore and test the API endpoints.
    

### Running Unit Tests

To run the unit tests for the application, execute the following command:


`mvn test` 


## Additional Notes

-   The Jasper templates can be found in the `src/main/resources/templates` directory. You can add or modify the templates in this folder.

----------

Please make sure to replace the placeholders `YOUR_GITHUB_API_TOKEN`, `YOUR_DB_USERNAME`, and `YOUR_DB_PASSWORD` with your own values in the `application.properties` file.
