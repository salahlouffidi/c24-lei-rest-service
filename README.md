# c24-lei-rest-service

This project demonstrates the use of C24 SDO technology with Hazelcast, Spring and Docker

# Get the project

The project is available from the C24 public Git repository.

To clone it:

``` git clone https://github.com/C24-Technologies/c24-lei-rest-service ```

# Build the project with Maven

To build the project execute the following maven command from the project root directory:

``` mvn clean install ```

This will build the jar file that you can then deploy to docker hub

# Running the project as a standalone service with Spring Boot

You an deploy the service as a rest service without docker by executing the maven command:

``` java  -DPROPERTY_FILE_LOCATION=<Path to your properties file> -DXmx2048MB -jar target/c24-lei-rest-service-1.0.0.jar```

See src/main/resources/sample_properties.properties for an example of properties to set

This will deploy the service on the default port 8080

There is a sample LEI file with a subset of the full LEI data in src/main/resources/data

You can use this file to test the data ingest