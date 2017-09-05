# README #

Quick steps to setup the local project and contribution guidelines.

### What is this repository for? ###

* Sailor API provides light weight orchestration services around Customer-360 and Seaware.
* Version 0.0.1
* [Learn Markdown](https://bitbucket.org/tutorials/markdowndemo)

### How do I get set up? ###



#### Local Setup ####
1. Install JDK 1.8.0_131
1. Install GIT Client 2.12.2
1. Clone the code from the master branch of sailor-api
1. Open a command prompt and run <code>./gradlew build</code> from the project root folder.  (This should download gradle AND build the project)
1. Run project with <code>./gradlew bootRun</code>
1. Test application launching:
 * http://localhost:8432/v1
 * http://localhost:8432/v1/sailors
 * http://localhost:8432/v1/sailors/123
 * http://localhost:8432/v1/health
1. Shutdown application and continue to IDE setup:

#### Local IDE Setup ####
##### Spring Tools Suite #####
1. Install STS 3.8.4
1. Install Lombok Plugin https://projectlombok.org/setup/eclipse
1. Install Gradle Plugin https://marketplace.eclipse.org/content/buildship-gradle-integration#group-external-install-button
1. Import Gradle Project
1. Run SpringBoot application SailorServicesApplication

##### IntelliJ IDEA #####
1. Install Lombok Plugin https://projectlombok.org/setup/intellij
1. Open Gradle Project
1. Run SpringBoot application SailorServicesApplication

##### Application Configuration #####
* Configuration: All configuration is hosted by this project.  Environment configuration will be externalized in a future Sprint.
* Dependencies: C-360 and Seaware
* Database configuration: NA
* How to run tests: <code>./gradlew test</code>
* Deployment instructions: Manual deployment into Kubernetes.  When CI/CD pipeline is in place we will set the integration up.

### Contribution guidelines ###

* Writing tests: Combination of Junit, Mockito, Hamcrest and Rest Assured for validating behavior of the implemented component(s)
* Code review:  Setup pull requests for peer review.
* Other guidelines

### Who do I talk to? ###

* Repo owner: Murtaza Goga