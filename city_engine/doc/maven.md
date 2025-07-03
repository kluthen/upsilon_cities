# TECH DOC MAVEN

*To Be filled in...* This is some sort of cross between a project assistant, dependency manager and builder.

## Uses

### POM.XML

this file is directly tied to maven, it allow defining dependencies of the project. 

```xml
<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-redis</artifactId>
		</dependency>
</dependencies>
```

**Dependency** can support a few a few more tags, among those **scope** will assist with compilation, for example scoped with *runtime* will only be added to the jar that has a *main.* Those scoped with *test* will only be used when testing and compiling tests.

It is to be noted that it has also some abilities to trigger pre/post compilation scripts. 

See also (Lombok)[lombok.md]

### Commands

**mvn** or **mvnw** can be used (the later has been provided by *Spring Initializr,* it's mostly the same just focussed on the current project)

A few commands are of note:

* **# mvn compile** : will trigger a compilation of the project: note, it will compile only the *runtime* scope.
* **# mvn test** : will trigger a compilation of the project and start the tests (it will also compile the tests.)
* **# mvn dependency:resolve** will check *pom.xml* and download/purge dependencies as seen fit.