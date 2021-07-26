# Airosearch

> search engine for airport details

## Table of contents

- [Introduction](#introduction)
- [Requirements](#requirements)
- [Local Development Setup](#local-development-setup)
- [Setup Production](#setup-production-environment)
- [Search Engine](#search-engine-interface)

## Introduction

Airosearch is a project providing a simple web based search engine for airport information.

## Requirements
> required to build and run the application:

- JDK 11
- Maven 3
- MariaDB
- Docker (optional)

## Local development setup

> setup database for local development  
> running command will spin up mariadb container with `airosearch` database with username/password `build/build`

```
docker-compose up -d
```

When using an IDE, create a Run Configuration with `nl.tsai.airosearch.AirosearchApplication` as main class, and activate `local` spring profile to load additional configuration

When using maven, you can with the spring-boot maven plugin and run the application for the command line:  
`mvn spring-boot:run -Dspring-boot.run.profiles=local`

## Setup production environment

1. Setup Database  
   1.1 [Install MariaDB Server](https://mariadb.com/get-started-with-mariadb/)  
   1.2 Create user and database  
   1.3 Add details to [application.properties](src/main/resources/application-prod.properties)
   ```
    spring.datasource.url=
    spring.datasource.username=
    spring.datasource.password=
    ```
   
2. Build project, this will create a jar with the name `airosearch-<version>.jar` in target directory
```
mvn clean package
```

3. Deploy jar to any environment/OS with JDK 11 installed
`java -jar target/airosearch-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod`
   
## Search engine interface

The deployed the spring boot application will listen to port 8080 by default
The interface consist of a single page which is located at http://localhost:8080

- Here you can enter a country name or code and click 'search', and it will return 
  (exact code / exact name / partial name / fuzzy name) matches.
- In the list of countries you will be able to click on the country name to fetch its airports
   - if there are more than 20 (default) airports, there will be pagination available.

