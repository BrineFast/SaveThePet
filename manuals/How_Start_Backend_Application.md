## How Run it

1) You must have mySql server and intellij IDEA with jdk 13 and the lombok pluggin.
2) Start mySql server and create a clear database.
3) Now in `src/main/resource/application.properties` enter the properties of your database in the following lines:
```java
spring.datasource.url = your url
spring.datasourse.username = your username
spring.datasource.password = your password
```
4) Create a .jar or run the application using Gradle from IDE Using the following commands in the terminal:
```
./gradlew build
./gradlew bootRun
```
5) If the application is launched successfully, you will see the following line in the terminal: `Started SaveThePetApplication in ...`