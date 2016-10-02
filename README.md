# RestApi EDU
This is a very simple project for educational purposes. 
For education import the project as a gradle project to your favorite IDE (IntelliJ of course ;) )

I've used in memory database (production configuration) for simplicity, so all data are lost between runs.
The real application should be configured for a real database (production), and in memory database (for testing).

### Possible improvements
* caching (for example ehcache with Spring Cache Abstraction)
* security (for example JWT with Spring Security)
* HATEOS
* ...

Only employee endpoints are documented!!!
Office endpoins are:
/api/office [post] -> for add new office
/api/offices [get] -> for list all offices

### Technologies used
* Spring Boot
* Spring Core
* Spring Data (JPA, Hibernate)
* Spring Web
* ModelMapper
* Jackson
* Spring Test
* jUnit
* dbUnit + dbUnit Spring integration
* Spring RestDocs
* AsciiDoctor
* Gradle build system

### How to build the application (Tested only on Linux)
- clone repository
- inside restapi directory execute gradle wrapper ( *gradlew.bat* on Windows or *gradlew* on Linux) with task deploy

**Windows**
```
> gradlew.bat deploy
```

**Linux**
```
> gradlew deploy
```

### How to run the application (Tested only on Linux)
- change directory to deploy
- execute:

```
> java -jar restapi-0.1-SNAPSHOT.jar
```

- the application serves the documentation under [http://localhost:8080/doc/auto_doc.html](http://localhost:8080/doc/auto_doc.html)
- the application serves the documentation for service endpoins under [http://localhost:8080/docs](http://localhost:8080/docs)
- use your favorite client for example [Postman](https://www.getpostman.com/) and test on port 8080 