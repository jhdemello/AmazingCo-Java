# Amazing Co Application (built from the Spring PetClinic Application) [![Build Status](https://github.com/spring-projects/spring-petclinic/actions/workflows/maven-build.yml/badge.svg)](https://github.com/spring-projects/spring-petclinic/actions/workflows/maven-build.yml)

## Understanding the Spring Petclinic application with a few diagrams<br/>
The AmazingCo implementation was folded into the Petclinic application, so the architecture presented here is still holistically relevent to the AmazingCo implementation.<br/><br/>
<a href="https://speakerdeck.com/michaelisvy/spring-petclinic-sample-application">See the presentation here</a>

## Running amazingco Using Docker

You can use the following command to build and run from a Docker image.

```
docker-compose -f docker-compose.dev.yml up --build [-d]
```

The '-d' parameter runs the AmazingCo container in a detached state. Running **_without_** the '-d' parameter is not detached and directs 'stdout' to the console from which 'amazingco' was launched; running **_with_** the '-d' parameter is detached and directs 'stdout' to the Docker client. This can be viewed under the 'Logs' tab under the 'amazingco-java_service_1' service. (See **_NOTE_** below under **_Running amazingco locally_**.)

## Running amazingco locally
AmazingCo [Petclinic] is derived from a [Spring Boot](https://spring.io/guides/gs/spring-boot) application built using [Maven](https://spring.io/guides/gs/maven/). You can build a jar file and run it from the command line (it should work just as well with Java 8, 11 or 17):


```
git clone https://github.com/jhdemello/AmazingCo-Java.git
cd AmazingCo-Java
./mvnw package
java -jar target/*.jar
```

You can then access amazingco here: http://localhost:8080/amazingco (which is a redirect to 'employee_directory.html' within).

> **_NOTE:_**<br/><br/>
> **_In this incarnation of AmazingCo, the employee database and the in-memory representation are disconnected and not synchronized. There is a database intended for data persistence and there is in-memory representation of the data, but the marriage between the two have not occurred. However, the employee directory page is loaded from data initialized in the database, and on initialization, the data in that database is loaded into an in-memory n-ary tree. From there, FIND and MOVE operations are performed using the n-ary tree in memory and are reflected in 'stdout' (see comment above about running in a detached state and where to find information sent to 'stdout')._**<br/><br/>

Or you can run it from Maven directly using the Spring Boot Maven plugin. If you do this it will pick up changes that you make in the project immediately (changes to Java source files require a compile as well - most people use an IDE for this):

```
./mvnw spring-boot:run
```

> NOTE: Windows users should set `git config core.autocrlf true` to avoid format assertions failing the build (use `--global` to set that flag globally).<br/>
> NOTE: If you prefer to use Gradle, you can build the app using `./gradlew build` and look for the jar file in `build/libs`.<br/><br/>

### AmazingCo: INITIALIZATION<br/><br/>

On initialization, the employee directory is pulled from the database and is loaded into both the GUI and into memory. The following two images reflect this. <br/><br/>

#### GUI: Employee Directory on Initialization<br/>

![employee_directory](https://user-images.githubusercontent.com/100491526/159077007-634da0e6-c628-41ec-91ce-8446316fcae8.jpg)<br/><br/>

#### STDOUT: Employee Directory on Initialization<br/>

![nary_tree_init](https://user-images.githubusercontent.com/100491526/159082538-12220707-59f4-4861-b4c5-f821d2593d3f.jpg)<br/><br/>

### AmazingCo: FIND Employee<br/>

The following reflects a search for Aurelius, with the search results posted back to the browser:<br/><br/>

![employee_found](https://user-images.githubusercontent.com/100491526/159083430-f8401eaa-a2ab-4b8e-93ee-9dfff3004a89.jpg)<br/>

The following reflects a search for Aurelius, with the search results directed to 'stdout', as observed thru Docker.

> **_NOTE: This image illustrates that the node and all subnodes are retrieved from the n-ary tree._**<br/><br/>

![employee_found_docker](https://user-images.githubusercontent.com/100491526/159083434-f4e1584b-81f4-4fe4-ac2f-b183233351b3.jpg)<br/><br/>

### AmazingCo: MOVE Employee<br/>

The following shows that Aurelius was moved from Tolstoy to Penn and that all of Aurelius's direct reports, Venus, Mars, Jupiter, and Saturn, were reassigned to Hank.<br/><br/>

![employee_moved](https://user-images.githubusercontent.com/100491526/159084067-e2368456-0665-4c06-8a1e-dedc8e2c7ee1.jpg)<br/><br/>

## In case you find a bug/suggested improvement for AmazingCo
Our issue tracker is available here: https://github.com/jhdemello/AmazingCo-Java/issues


## Database configuration

In its default configuration, Petclinic uses an in-memory database (H2) which
gets populated at startup with data. The h2 console is automatically exposed at `http://localhost:8080/h2-console`
and it is possible to inspect the content of the database using the `jdbc:h2:mem:testdb` url.
 
A similar setup is provided for MySQL and PostgreSQL in case a persistent database configuration is needed. Note that whenever the database type is changed, the app needs to be run with a different profile: `spring.profiles.active=mysql` for MySQL or `spring.profiles.active=postgres` for PostgreSQL.

You could start MySQL or PostgreSQL locally with whatever installer works for your OS, or with docker:

For AmazingCo (sans Petclinic):

```
docker run -e MYSQL_USER=amazingco -e MYSQL_PASSWORD=amazingco -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=amazingco -p 3306:3306 mysql:5.7.8
```

or

```
docker run -e POSTGRES_USER=amazingco -e POSTGRES_PASSWORD=amazingco -e POSTGRES_DB=amazingco -p 5432:5432 postgres:14.1
```

## Compiling the CSS

There is a `petclinic.css` in `src/main/resources/static/resources/css`. It was generated from the `petclinic.scss` source, combined with the [Bootstrap](https://getbootstrap.com/) library. If you make changes to the `scss`, or upgrade Bootstrap, you will need to re-compile the CSS resources using the Maven profile "css", i.e. `./mvnw package -P css`. There is no build profile for Gradle to compile the CSS.

This should also apply to the AmazingCo substructure within Petclinic.

## Working with AmazingCo in your IDE (!!!UNTESTED!!!)

### Prerequisites
The following items should be installed in your system:
* Java 8 or newer (full JDK not a JRE).
* git command line tool (https://help.github.com/articles/set-up-git)
* Your preferred IDE 
  * Eclipse with the m2e plugin. Note: when m2e is available, there is an m2 icon in `Help -> About` dialog. If m2e is
  not there, just follow the install process here: https://www.eclipse.org/m2e/
  * [Spring Tools Suite](https://spring.io/tools) (STS)
  * IntelliJ IDEA
  * [VS Code](https://code.visualstudio.com)

### Steps:

1) On the command line
    ```
    git clone https://github.com/jhdemello/AmazingCo-Java.git
    ```
2) Inside Eclipse or STS
    ```
    File -> Import -> Maven -> Existing Maven project
    ```

    Then either build on the command line `./mvnw generate-resources` or using the Eclipse launcher (right click on project and `Run As -> Maven install`) to generate the css. Run the application main method by right clicking on it and choosing `Run As -> Java Application`.

3) Inside IntelliJ IDEA
    In the main menu, choose `File -> Open` and select the AmazingCo [pom.xml](pom.xml). Click on the `Open` button.

    CSS files are generated from the Maven build. You can either build them on the command line `./mvnw generate-resources` or right click on the `amazingco` project then `Maven -> Generates sources and Update Folders`.

    A run configuration named `PetClinicApplication` should have been created for you if you're using a recent Ultimate version. Otherwise, run the application by right clicking on the `PetClinicApplication` main class and choosing `Run 'PetClinicApplication'`.

4) Navigate to Petclinic

    Visit [http://localhost:8080](http://localhost:8080) in your browser.


## Looking for something in particular?

|Spring Boot Configuration | Class or Java property files  |
|--------------------------|---|
|The Main Class | [PetClinicApplication](https://github.com/spring-projects/amazingco/blob/main/src/main/java/org/springframework/samples/petclinic/PetClinicApplication.java) |
|Properties Files | [application.properties](https://github.com/spring-projects/amazingco/blob/main/src/main/resources) |
|Caching | [CacheConfiguration](https://github.com/spring-projects/amazingco/blob/main/src/main/java/org/springframework/samples/petclinic/system/CacheConfiguration.java) |

## Interesting Spring Petclinic branches and forks

The Spring Petclinic "main" branch in the [spring-projects](https://github.com/spring-projects/amazingco)
GitHub org is the "canonical" implementation, currently based on Spring Boot and Thymeleaf. There are
[quite a few forks](https://amazingco.github.io/docs/forks.html) in a special GitHub org
[petclinic](https://github.com/petclinic). If you have a special interest in a different technology stack
that could be used to implement the Pet Clinic then please join the community there.


## Interaction with other open source projects

One of the best parts about working on the Spring Petclinic application is that we have the opportunity to work in direct contact with many Open Source projects. We found some bugs/suggested improvements on various topics such as Spring, Spring Data, Bean Validation and even Eclipse! In many cases, they've been fixed/implemented in just a few days.
Here is a list of them:

| Name | Issue |
|------|-------|
| Spring JDBC: simplify usage of NamedParameterJdbcTemplate | [SPR-10256](https://jira.springsource.org/browse/SPR-10256) and [SPR-10257](https://jira.springsource.org/browse/SPR-10257) |
| Bean Validation / Hibernate Validator: simplify Maven dependencies and backward compatibility |[HV-790](https://hibernate.atlassian.net/browse/HV-790) and [HV-792](https://hibernate.atlassian.net/browse/HV-792) |
| Spring Data: provide more flexibility when working with JPQL queries | [DATAJPA-292](https://jira.springsource.org/browse/DATAJPA-292) |


# Contributing

The [issue tracker](https://github.com/spring-projects/amazingco/issues) is the preferred channel for bug reports, features requests and submitting pull requests.

For pull requests, editor preferences are available in the [editor config](.editorconfig) for easy use in common text editors. Read more and download plugins at <https://editorconfig.org>. If you have not previously done so, please fill out and submit the [Contributor License Agreement](https://cla.pivotal.io/sign/spring).

# License

The Spring PetClinic sample application is released under version 2.0 of the [Apache License](https://www.apache.org/licenses/LICENSE-2.0).

[spring-petclinic]: https://github.com/spring-projects/spring-petclinic
[spring-framework-petclinic]: https://github.com/spring-petclinic/spring-framework-petclinic
[spring-petclinic-angularjs]: https://github.com/spring-petclinic/spring-petclinic-angularjs 
[javaconfig branch]: https://github.com/spring-petclinic/spring-framework-petclinic/tree/javaconfig
[spring-petclinic-angular]: https://github.com/spring-petclinic/spring-petclinic-angular
[spring-petclinic-microservices]: https://github.com/spring-petclinic/spring-petclinic-microservices
[spring-petclinic-reactjs]: https://github.com/spring-petclinic/spring-petclinic-reactjs
[spring-petclinic-graphql]: https://github.com/spring-petclinic/spring-petclinic-graphql
[spring-petclinic-kotlin]: https://github.com/spring-petclinic/spring-petclinic-kotlin
[spring-petclinic-rest]: https://github.com/spring-petclinic/spring-petclinic-rest
