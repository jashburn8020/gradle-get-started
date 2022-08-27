# Getting started with Gradle (with Java)

<!-- TOC -->
* [Getting started with Gradle (with Java)](#getting-started-with-gradle--with-java-)
  * [Install Gradle](#install-gradle)
  * [Building Java Applications](#building-java-applications)
    * [Create a project folder](#create-a-project-folder)
    * [Run the init task](#run-the-init-task)
    * [Review the project files](#review-the-project-files)
    * [Run the application](#run-the-application)
    * [Bundle the application](#bundle-the-application)
    * [Publish a Build Scan](#publish-a-build-scan)
  * [Building Java Applications with Libraries](#building-java-applications-with-libraries)
    * [Create a project folder](#create-a-project-folder)
    * [Run the init task](#run-the-init-task)
    * [Review the project files](#review-the-project-files)
    * [Run the tests](#run-the-tests)
  * [Groovy Build Script Primer](#groovy-build-script-primer)
    * [The `Project` object](#the-project-object)
    * [Properties](#properties)
      * [Properties in the API documentation](#properties-in-the-api-documentation)
    * [Methods](#methods)
    * [Blocks](#blocks)
      * [Block method signatures](#block-method-signatures)
      * [Delegation](#delegation)
    * [Local variables](#local-variables)
  * [Sources](#sources)
<!-- TOC -->

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

## Groovy Build Script Primer

- Ideally, a Groovy build script looks mostly like configuration
  - setting some properties of the project, configuring dependencies, declaring tasks, etc.
  - based on Groovy language constructs

### The `Project` object

- As Groovy is an object-oriented language based on Java, its properties and methods apply to objects
- In some cases, the object is implicit
  - particularly at the top level of a build script, i.e. not nested inside a `{}` block
- Every Groovy build script is backed by an implicit instance of [`org.gradle.api.Project`](https://docs.gradle.org/current/dsl/org.gradle.api.Project.html)
  - if you see an unqualified element, check the `Project` API documentation to see if that's where it's coming from
- Both `version` and `configurations {}` below are part of `Project`

```groovy
// Unqualified property
version = '1.0.0.GA'

// Unqualified block
configurations {
    ...
}
```

### Properties

```groovy
<obj>.<name>                // Get a property value
<obj>.<name> = <value>      // Set a property to a new value
"$<name>"                   // Embed a property value in a string
"${<obj>.<name>}"           // Same as previous (embedded value)
```

- Examples

```groovy
version = '1.0.1'
myCopyTask.description = 'Copies some files'

file("$buildDir/classes")
println "Destination: ${myCopyTask.destinationDir}"
```

- If the name is unqualified, then it may be one of the following:
  - a task instance with that name
  - a property on `Project`
  - an extra property defined elsewhere in the project
  - a property of an implicit object within a block
  - a local variable defined earlier in the build script
- Note that plugins can add their own properties to the `Project` object
  - the [API documentation](https://docs.gradle.org/current/dsl/org.gradle.api.Project.html#N14E9A) lists all the properties added by core plugins
  - if you're struggling to find where a property comes from, check the documentation for the plugins that the build uses
- When referencing a project property in your build script that is added by a non-core plugin, consider prefixing it with `project.`
  - it's clear then that the property belongs to the `project` object

#### Properties in the API documentation

- The [Groovy DSL reference](https://docs.gradle.org/current/dsl/) shows properties as they are used in your build scripts, but the Javadocs only display methods
  - properties are implemented as methods behind the scenes
  - a property can be read if there is a method named `get<PropertyName>` with zero arguments that returns the same type as the property
  - a property can be modified if there is a method named `set<PropertyName>` with one argument that has the same type as the property and a return type of `void`
- Property names usually start with a lower-case letter, but that letter is upper case in the method names
  - the getter method `getProjectVersion()` corresponds to the property `projectVersion`
  - this convention does not apply when the name begins with at least two upper-case letters, in which case there is not change in case
    - `getRAM()` corresponds to the property `RAM`
- Examples

```groovy
project.getVersion()
project.version

project.setVersion('1.0.1')
project.version = '1.0.1'
```

### Methods

```groovy
<obj>.<name>()              // Method call with no arguments
<obj>.<name>(<arg>, <arg>)  // Method call with multiple arguments
<obj>.<name> <arg>, <arg>   // Method call with multiple args (no parentheses)
```

- Examples

```groovy
myCopyTask.include '**/*.xml', '**/*.properties'

ext.resourceSpec = copySpec()   // `copySpec()` comes from `Project`

file('src/main/java')
println 'Hello, World!'
```

- A method represents some behavior of an object, although Gradle often uses methods to configure the state of objects as well
- Methods are identifiable by their arguments or empty parentheses
  - parentheses are sometimes required, such as when a method has zero arguments
  - you may find it simplest to always use parentheses
- Gradle has a convention whereby if a method has the same name as a collection-based property, then the method appends its values to that collection

### Blocks

- Blocks are also methods, just with specific types for the last argument

```groovy
<obj>.<name> {
    ...
}

<obj>.<name>(<arg>, <arg>) {
    ...
}
```

- Examples

```groovy
plugins {
    id 'java-library'
}

configurations {
    assets
}

sourceSets {
    main {
        java {
            srcDirs = ['src']
        }
    }
}

dependencies {
    implementation project(':util')
}
```

- Blocks are a mechanism for configuring multiple aspects of a build element in one go
- They also provide a way to nest configuration, leading to a form of structured data
- 2 important aspects:
  - implemented as methods with specific signatures
  - can change the target ("delegate") of unqualified methods and properties

#### Block method signatures

- You can identify a method as a block's implementation by its signature - its argument types
  - if a method corresponds to a block:
    - it must have at least one argument 
    - the last argument must be of type `groovy.lang.Closure` or `org.gradle.api.Action`
- E.g., [`Project.copy(Action)`](https://docs.gradle.org/current/dsl/org.gradle.api.Project.html#org.gradle.api.Project:copy(org.gradle.api.Action)):
  - `WorkResult copy(Action<? super CopySpec> action)`

```groovy
copy {
    into "$buildDir/tmp"
    from 'custom-resources'
}
```

#### Delegation

- The section on [properties](#properties) lists where unqualified properties might be found
  - the `Project` object
  - for those unqualified properties and methods inside a block: the block's delegate object
- In the above example,
  - [`copy()`](https://docs.gradle.org/current/dsl/org.gradle.api.Project.html#org.gradle.api.Project:copy(org.gradle.api.Action)) and [`buildDir`](https://docs.gradle.org/current/dsl/org.gradle.api.Project.html#N14E9A) are from the `Project` object
  - `into()` and `from()` are resolved against the delegate of the `copy {}` block
- 2 ways to determine the delegate type, depending on the signature of the block method:
  - `Action` arguments
    - look at the type's parameter: `Action<? super CopySpec> action`
    - therefore, the delegate type: `CopySpec`
  - `Closure` arguments
    - the documentation will explicitly say what type is being configured or what type the delegate is (different terminology for the same thing)
    - e.g., [`WorkResult copy(Closure closure)`](https://docs.gradle.org/current/dsl/org.gradle.api.Project.html#org.gradle.api.Project:copy(groovy.lang.Closure))
      - its documentation: "The given closure is used to configure a `CopySpec`, which is then used to copy the files"
- `into "$buildDir/tmp"`
  - in `CopySpec`: [`CopySpec into(Object destPath)`](https://docs.gradle.org/current/javadoc/org/gradle/api/file/CopySpec.html#into-java.lang.Object-)
- `from 'custom-resources'`
  - in `CopySpec`: [`CopySpec from(Object... sourcePaths)`](https://docs.gradle.org/current/javadoc/org/gradle/api/file/CopySpec.html#from-java.lang.Object...-)
- Both `into()` and `from()` have variants that take an `Action` as their last argument
  - you can use block syntax with them
- All new Gradle APIs declare an `Action` argument type rather than `Closure`
  - easy to pick out the delegate type
- Older APIs have an `Action` variant in addition to the old `Closure` one

### Local variables

```groovy
def <name> = <value>        // Untyped variable
<type> <name> = <value>     // Typed variable
```

- Examples

```groovy
def i = 1
String errorMsg = 'Failed, because reasons'
```

- Local variables are a Groovy constructthat can be used to share values within a build script
- Avoid using local variables in the root of the project, i.e. as pseudo project properties
  - they cannot be read outside of the build script and Gradle has no knowledge of them
- Within a narrower context such as configuring a task, local variables can occasionally be helpful

## Sources

- "Building Java Applications Sample." _Gradle.org_, 2021, [docs.gradle.org/current/samples/sample_building_java_applications.html](https://docs.gradle.org/current/samples/sample_building_java_applications.html). Accessed 18 August 2022.
- "Building Java Applications with Libraries Sample." _Gradle.org_, 2021, [docs.gradle.org/current/samples/sample_building_java_applications_multi_project.html](https://docs.gradle.org/current/samples/sample_building_java_applications_multi_project.html). Accessed 24 August 2022.
