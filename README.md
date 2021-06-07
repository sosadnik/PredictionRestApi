# PredictionRestApi

This application connects to an external api, retrieves data from it and stores it in the PostgresSQL database.
On the basis of these data, it determines the effectiveness of prediction for given teams and leagues,
later, based on this statistic, it selects the most likely predictions for the day.
Created with Spring, RestAPI, database PostgreSQL, hibernate, mockito, junit, lombok in Java15.
I made this project to learn and practice technologies mentioned above.

## Requirements
- jdk 15
- maven
- postgresql

### How to build
```bash
mvn clean install
```
### How to run
```bash
mvn spring-boot:run
```

### Configuration example
```bash
spring: 
  datasource: 
    username: "USERNAME" 
    password: "PASSWORD"  
    url: "JDBC_URL" 
    driverclass: "DRIVER"
```
