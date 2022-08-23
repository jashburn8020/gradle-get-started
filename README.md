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
  - configured in [`build.gradle`](java_app/app/build.gradle)
- Generated class: [`App.java`](java_app/app/src/main/java/com/jashburn/gradle/java_app/App.java)
- Generated test: [`AppTest.java`](java_app/app/src/test/java/com/jashburn/gradle/java_app/AppTest.java)

### Run the application

- Due to the [`application` plugin](https://docs.gradle.org/current/userguide/application_plugin.html), you can run the application directly from the command line
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
https://gradle.com/s/uij5vmjih6ok6
```

## Building Java Applications with Libraries

- To prepare your software project for growth, you can organise a Gradle project into multiple subprojects to modularise the software you are building

### Create a project folder

- Create a project folder: [`java_app_with_lib`](java_app_with_lib)

### Run the init task

- Run the `init` task in the project folder: `gradle init`
  - `Select type of project to generate:`: `2` (application)
  - `Split functionality across multiple subprojects?:`: `2` (yes - application and library projects)

```console
$ gradle init
Starting a Gradle Daemon, 1 incompatible and 1 stopped Daemons could not be reused, use --status for details

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
Enter selection (default: no - only one application project) [1..2] 2

Select build script DSL:
  1: Groovy
  2: Kotlin
Enter selection (default: Groovy) [1..2] 1

Generate build using new APIs and behavior (some features may change in the next minor release)? (default: no) [yes, no] no

Project name (default: java_app_with_lib): 
Source package (default: java_app_with_lib): com.jashburn.gradle.java_app_with_lib

> Task :init
Get more help with your project: https://docs.gradle.org/7.5.1/samples/sample_building_java_applications_multi_project.html

BUILD SUCCESSFUL in 1m 38s
2 actionable tasks: 2 executed
```

- The `init` task generates the new project with the following structure:

```text
├── app
│   ├── build.gradle  # Build script of the `app` subproject (1st of 3)
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── jashburn
│       │   │           └── gradle
│       │   │               └── java_app_with_lib
│       │   │                   └── app
│       │   │                       ├── App.java
│       │   │                       └── MessageUtils.java
│       │   └── resources
│       └── test
│           ├── java
│           │   └── com
│           │       └── jashburn
│           │           └── gradle
│           │               └── java_app_with_lib
│           │                   └── app
│           │                       └── MessageUtilsTest.java
│           └── resources
├── buildSrc
│   ├── build.gradle  # Build script of `buildSrc` to configure dependencies of the build logic
│   └── src  # Source folder for convention plugins written in Groovy or Kotlin DSL
│       └── main
│           └── groovy
│               ├── com.jashburn.gradle.java_app_with_lib.java-application-conventions.gradle
│               ├── com.jashburn.gradle.java_app_with_lib.java-common-conventions.gradle
│               └── com.jashburn.gradle.java_app_with_lib.java-library-conventions.gradle
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── list
│   ├── build.gradle  # Build script of the `list` subproject (2nd of 3)
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── jashburn
│       │   │           └── gradle
│       │   │               └── java_app_with_lib
│       │   │                   └── list
│       │   │                       └── LinkedList.java
│       │   └── resources
│       └── test
│           ├── java
│           │   └── com
│           │       └── jashburn
│           │           └── gradle
│           │               └── java_app_with_lib
│           │                   └── list
│           │                       └── LinkedListTest.java
│           └── resources
├── settings.gradle
└── utilities
    ├── build.gradle  # Build script of the `utilities` subproject (3rd of 3)
    └── src
        ├── main
        │   ├── java
        │   │   └── com
        │   │       └── jashburn
        │   │           └── gradle
        │   │               └── java_app_with_lib
        │   │                   └── utilities
        │   │                       ├── JoinUtils.java
        │   │                       ├── SplitUtils.java
        │   │                       └── StringUtils.java
        │   └── resources
        └── test
            └── resources
```

### Review the project files

- See [`java_app_with_lib/settings.gradle`](java_app_with_lib/settings.gradle)
- Since our build consists of multiple-subprojects, we want to share build logic and configuration between them
  - utilise **convention plugins** that are located in the [`buildSrc`](java_app_with_lib/buildSrc) folder
  - an easy way to utilise Gradle’s plugin system to write reusable bits of build configuration
- The [`java-common-conventions`](java_app_with_lib/buildSrc/src/main/groovy/com.jashburn.gradle.java_app_with_lib.java-common-conventions.gradle) defines some configuration that should be shared by all our Java project
  - independent of whether they represent a library or the actual application
- Both [`java-library-conventions`](java_app_with_lib/buildSrc/src/main/groovy/com.jashburn.gradle.java_app_with_lib.java-library-conventions.gradle) and [`java-application-conventions`](java_app_with_lib/buildSrc/src/main/groovy/com.jashburn.gradle.java_app_with_lib.java-application-conventions.gradle) apply the `java-common-conventions` plugin
  - the configuration performed there is shared by library and application projects alike
- The `build.gradle` files in the subprojects: [`app/build.gradle`](java_app_with_lib/app/build.gradle), [`list/build.gradle`](java_app_with_lib/list/build.gradle), [`utilities/build.gradle`](java_app_with_lib/utilities/build.gradle)
  - include up to three blocks
    - every build script should have a `plugins {}` block to apply plugins
      - in a well-structured build, it may only apply one convention plugin as in this example
      - the convention plugin will take care of applying and configuring core Gradle plugins (like `application` or `java-library`), other convention plugin, or community plugins from the Plugin Portal
    - if the project has dependencies, a `dependencies {}` block should be added
      - dependencies can be external, such as the JUnit dependencies we add in `java-common-conventions`, or can point to other local subprojects
      - for local subprojects, the `project(...)` notation is used
        - the `utilities` library requires the `list` library
        - the `app` makes use of the `utilities` library
      - if local projects depend on each other, Gradle takes care of building dependent projects if (and only if) needed
        - see the documentation about [dependency management](https://docs.gradle.org/current/userguide/core_dependency_management.html)
    - there may be one or multiple configuration blocks for plugins
      - these should only be used in build scripts directly if they configure something specific for the one project
      - otherwise, such configurations also belong in a convention plugin
      - in this example, we use the `application {}` block, which is specific to the `application` plugin, to set the `mainClass` in our app project to `App`
- [`buildSrc/build.gradle`](java_app_with_lib/buildSrc/build.gradle) sets the stage to build the convention plugins themselves

### Run the tests

- Use `./gradlew check` to execute all tests in all subprojects
- To target only a specific subproject, you can use the full path to the task
  - `:app:check` will only execute the tests of the app project
  - the other subprojects will still be compiled in the case of this example, because `app` declares dependencies to them
- Gradle won't print more output to the console if all tests passed successfully
  - find the test reports in the `<subproject>/build/reports` folders

```console
$ ./gradlew check

BUILD SUCCESSFUL in 17s
18 actionable tasks: 18 executed

$ ./gradlew :app:check

BUILD SUCCESSFUL in 481ms
16 actionable tasks: 16 up-to-date
```

## Sources

- "Building Java Applications Sample." _Gradle.org_, 2021, [docs.gradle.org/current/samples/sample_building_java_applications.html](https://docs.gradle.org/current/samples/sample_building_java_applications.html). Accessed 18 August 2022.
- "Building Java Applications with Libraries Sample." _Gradle.org_, 2021, [docs.gradle.org/current/samples/sample_building_java_applications_multi_project.html](https://docs.gradle.org/current/samples/sample_building_java_applications_multi_project.html). Accessed 24 August 2022.
