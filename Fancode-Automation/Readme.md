# FanCode User Todo Completion Checker

This project is a Spring Boot application that checks if all users from the city "FanCode" have more than half of their todo tasks completed. The city "FanCode" can be identified by users having latitude between (-40 to 5) and longitude between (5 to 100) in the users API.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Technologies Used](#technologies-used)

## Introduction

The application fetches users and their todos from an external API (`jsonplaceholder.typicode.com`) and checks if the users from the city "FanCode" have completed more than 50% of their todo tasks.

## Features

- Fetch users and todos from an external API.
- Identify users belonging to the city "FanCode".
- Check if users have completed more than 50% of their todo tasks.
- Asynchronous processing to improve performance.


### Prerequisites

- Java 17 or higher
- Maven 3.6.0 or higher

### Installation

1. **Clone the repository**
    ```sh
    git https://github.com/Vishal-Sakpal-code/Fancode.git
    cd Fancode-Automation
    ```

2. **Build the project**
    ```sh
    mvn clean install
    ```

3. **Run the application**
    ```sh
    mvn spring-boot:run
    ```

## Running the Application

Once the application is running, you can access the API endpoint to check users' todo completion status.

### API Endpoints

- **GET /checkUsersCompletion**

  This endpoint checks if all users from the city "FanCode" have more than 50% of their todo tasks completed.

  **Response:**
    ```json
    {
        "1": true,
        "2": false,
        "3": true,
    }
    ```
  Each key in the response is a user ID, and the value is a boolean indicating whether the user's completed task percentage is greater than 50%.


## Technologies Used

- Spring Boot
- Spring Web
- Spring Async
- Lombok
- JUnit
- Mockito
- Maven

## Additional Notes

- **Lombok:** This project uses Lombok to reduce boilerplate code for model classes. Ensure your IDE supports Lombok.
- **Asynchronous Processing:** The application uses `CompletableFuture` for asynchronous processing to improve performance.
