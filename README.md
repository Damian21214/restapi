# RestApi EDU
This is a very simple project for educational purposes. 
For education import project as gradle project to your favorite IDE (IntelliJ of course ;) )

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

### How to build application (Tested only on Linux)
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

### How to run application (Tested only on Linux)
- change directory to deploy
- execute:

```
> java -jar restapi-0.1-SNAPSHOT.jar
```

- application serve documentation under [http://localhost:8080/doc/auto_doc.html](http://localhost:8080/doc/auto_doc.html)
- application serve documentation for service endpoins under [http://localhost:8080/docs](http://localhost:8080/docs)
- use your favorite client for example [Postman](https://www.getpostman.com/) and test on port 8080 