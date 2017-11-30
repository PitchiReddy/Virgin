# README #

Quick steps to setup the local project and contribution guidelines.

### What is this repository for? ###

* Integration API is a parent project that includes 3 microservices: 
    + **Sailor API:** Provides light weight orchestration services around Seaware,Salesforce and Customer-360.
    + **Crossreference API:** Provides light weight services around CrossReference Data between different SORs.
    + **Recommendation API:** Recommendations API provides light weight services around the Recommendations Engine.
* Version 0.0.1
* [Learn Markdown](https://bitbucket.org/tutorials/markdowndemo)

### How do I get set up? ###

#### Local Setup ####
1. Install JDK 1.8.0_131 
2. Install GIT Client 2.12.2
3. Clone the code from the master branch of integration-api
4. Setup MySQL Instance (https://dev.mysql.com/doc/refman/5.7/en/installing.html)
5. Create database with name "XREF" in MySQL
6. Setup environment variables as below to access externalized config files: 
    * APPLICATION_PROPERTIES_PATH=_file:///<%project_root_dir%>/config/integration-api-config/src/main/resources/integration-api.yml_
    * SAILOR_SERVICE_PROPERTIES_PATH=_file:///<%project_root_dir%>/config/integration-api-config/src/main/resources/local/sailor-api.yml_
    * CROSSREFERENCE_SERVICE_PROPERTIES_PATH=_file:///<%project_root_dir%>/config/integration-api-config/src/main/resources/local/crossreference-api.yml_
7. Open a command prompt and run _./gradlew build -xtest_ from the project root folder.  (This should download gradle AND build the project)
8. Go to any of the application libs folder and run application
	* Sailor API - _cd /applications/sailor-api/build/libs/_
    * CrossReference API - _cd /applications/crossreference-api/build/libs/_
    * Recommendation API - _cd /applications/recommendation-api/build/libs/_
9.  Run application with _java -jar <%application_jar_name%>_
10. Test applications launching:
    * Sailor API - http://localhost:8432/sailor-api/v1
    * Crossreference API - http://localhost:8435/crossreference-api/v1
    * Recommendation API - http://localhost:8438/recommendation-api/v1

#### Local IDE Setup ####
##### Spring Tools Suite #####
1. Install STS 3.8.4
2. Install Lombok Plugin https://projectlombok.org/setup/eclipse
3. Install Gradle Plugin https://marketplace.eclipse.org/content/buildship-gradle-integration#group-external-install-button
4. Import Gradle Project
5. Run SpringBoot application SailorServicesApplication/CrossReferenceServicesApplication/RecommendationServicesApplication

##### IntelliJ IDEA #####
1. Install Lombok Plugin https://projectlombok.org/setup/intellij
2. Open Gradle Project
3. Run SpringBoot application SailorServicesApplication/CrossReferenceServicesApplication/RecommendationServicesApplication

##### Application Configuration #####
* Configuration: All configuration is externalized for this project. (Refer "Local Setup" #6)
* Dependencies: 
    + Sailor-API : Salesforce,C-360 and Seaware
    + Crossreference-API : MySQL DB
    + Recommendation-API : HBase
* Database configuration: (Refer "Local Setup" #4,#5)
* How to run tests: 
    + _./gradlew test_   (Default- only unit tests)
    + _./gradlew integrationTests_  (Only integration tests)
    + _./gradlew functionalTests_ (Only functional tests)
    + _./gradlew testAll_ (All tests)
* Deployment instructions: CI/CD pipeline is setup in Jenkins+Spinnaker (Documentation TBD)

### Contribution guidelines ###

* Writing tests: Combination of Junit, Mockito, Hamcrest and Rest Assured for validating behavior of the implemented component(s)
* Code review:  Setup pull requests for peer review.
* Other guidelines

### Who do I talk to? ###

* Repo owners:
    - Murtaza Goga
    - Rashmi Praveen