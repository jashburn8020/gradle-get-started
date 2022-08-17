# Getting started with Gradle (with Java)

## Install Gradle

- See <https://docs.gradle.org/current/userguide/installation.html>

## Building Java Applications

### Create a project folder

- Gradle comes with a built-in task, called `init` that initialises a new Gradle project in an empty folder
  - the `init` task uses the (also built-in) `wrapper` task to create a Gradle wrapper script, `gradlew`
- Create a folder for the new project and change directory into it

### Run the init task

- Run the `init` task

```console
$ mkdir java_app

$ cd java_app

$ gradle init

Select type of project to generate:
  1: basic
  2: application
  3: library
  4: Gradle plugin
Enter selection (default: basic) [1..4] 2

Select implementation language:
  1: C++
  2: Groovy
  3: Java
  4: Kotlin
  5: Scala
  6: Swift
Enter selection (default: Java) [1..6] 3

Split functionality across multiple subprojects?:
  1: no - only one application project
  2: yes - application and library projects
Enter selection (default: no - only one application project) [1..2] 1

Select build script DSL:
  1: Groovy
  2: Kotlin
Enter selection (default: Groovy) [1..2] 1

Generate build using new APIs and behavior (some features may change in the next minor release)? (default: no) [yes, no] no

Select test framework:
  1: JUnit 4
  2: TestNG
  3: Spock
  4: JUnit Jupiter
Enter selection (default: JUnit Jupiter) [1..4] 4

Project name (default: java_app): 
Source package (default: java_app): com.jashburn.gradle.java_app

> Task :init
Get more help with your project: https://docs.gradle.org/7.5.1/samples/sample_building_java_applications.html

BUILD SUCCESSFUL in 30s
2 actionable tasks: 2 executed
```

- The `init` task generates the new project with the following structure:

```text
├── app
│     ├── build.gradle  # Build script of the project
│     └── src
│         ├── main
│         │     ├── java  # Default Java source folder
│         │     │     └── com
│         │     │         └── jashburn
│         │     │             └── gradle
│         │     │                 └── java_app
│         │     │                     └── App.java
│         │     └── resources
│         └── test
│             ├── java  # Default Java test source folder
│             │     └── com
│             │         └── jashburn
│             │             └── gradle
│             │                 └── java_app
│             │                     └── AppTest.java
│             └── resources
├── gradle  # Generated folder for wrapper files
│     └── wrapper
│         ├── gradle-wrapper.jar
│         └── gradle-wrapper.properties
├── gradlew  # Gradle wrapper start scripts
├── gradlew.bat
└── settings.gradle  # Settings file to define build name and subprojects
```

### Review the project files

- See [`java_app/settings.gradle`](java_app/settings.gradle)
- Our build contains one subproject called `app` that represents the Java application we are building
  - configured in [`app/build.gradle`](app/build.gradle)
- Generated class: [`App.java`](java_app/app/src/main/java/com/jashburn/gradle/java_app/App.java)
- Generated test: [`AppTest.java`](java_app/app/src/test/java/com/jashburn/gradle/java_app/AppTest.java)

### Run the application

- Due to the `application` plugin, you can run the application directly from the command line
- The `run` task tells Gradle to execute the `main` method in the class assigned to the `mainClass` property

```console
$ ./gradlew run
Downloading https://services.gradle.org/distributions/gradle-7.5.1-bin.zip
...........10%............20%...........30%............40%...........50%............60%...........70%............80%...........90%............100%

> Task :app:run
Hello World!

BUILD SUCCESSFUL in 16s
2 actionable tasks: 2 executed
```

- The first time you run `gradlew`, there is a delay while that version of gradle is downloaded and stored locally in your `~/.gradle/wrapper/dists` folder

### Bundle the application

- The `application` plugin also bundles the application, with all its dependencies
- The archive will also contain a script to start the application with a single command
- Gradle will produce the archive in 2 formats:
  - `app/build/distributions/app.tar`
  - `app/build/distributions/app.zip`

```console
$ ./gradlew build

BUILD SUCCESSFUL in 1s
7 actionable tasks: 7 executed

$ cd app/build/distributions/

$ unzip -l app.zip 
Archive:  app.zip
  Length      Date    Time    Name
---------  ---------- -----   ----
        0  2022-08-18 00:15   app/
        0  2022-08-18 00:15   app/lib/
     1272  2022-08-18 00:15   app/lib/app.jar
  2974216  2022-08-18 00:09   app/lib/guava-31.0.1-jre.jar
     4617  2022-08-18 00:09   app/lib/failureaccess-1.0.1.jar
     2199  2022-08-18 00:09   app/lib/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar
    19936  2022-08-18 00:09   app/lib/jsr305-3.0.2.jar
   208835  2022-08-18 00:09   app/lib/checker-qual-3.12.0.jar
    14835  2022-08-18 00:09   app/lib/error_prone_annotations-2.7.1.jar
     8781  2022-08-18 00:09   app/lib/j2objc-annotations-1.3.jar
        0  2022-08-18 00:15   app/bin/
     3054  2022-08-18 00:15   app/bin/app.bat
     8381  2022-08-18 00:15   app/bin/app
---------                     -------
  3246126                     13 files
```

### Publish a Build Scan

- The best way to learn more about what your build is doing behind the scenes, is to publish a [build scan](https://scans.gradle.com/?_ga=2.91919325.633168902.1660601693-211215057.1659872712)

```console
$ ./gradlew build --scan

BUILD SUCCESSFUL in 2s
7 actionable tasks: 7 up-to-date

Publishing a build scan to scans.gradle.com requires accepting the Gradle Terms of Service defined at https://gradle.com/terms-of-service. Do you accept these terms? [yes, no] yes

Gradle Terms of Service accepted.

Publishing build scan...
https://gradle.com/s/[random characters]
```

## Sources

- "Building Java Applications Sample." _Gradle.org_, 2021, [docs.gradle.org/current/samples/sample_building_java_applications.html](https://docs.gradle.org/current/samples/sample_building_java_applications.html). Accessed 18 August 2022.
