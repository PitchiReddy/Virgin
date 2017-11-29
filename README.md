# README #

Quick steps to setup the local project and contribution guidelines.

### What is this repository for? ###

Integration API is a parent project that includes 3 microservices 
  1. Sailor API
     Provides light weight orchestration services around Seaware,Salesforce and Customer-360.
  2. Crossreference API
     Provides light weight orchestration services around CrossReference Data between different SORs.
  3. Recommendation API
     Recommendations API provides light weight orchestration services around the Recommendations Engine.
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
   *APPLICATION_PROPERTIES_PATH=file:///<%project_root_dir%>/config/integration-api-config/src/main/resources/integration-api.yml
   *SAILOR_SERVICE_PROPERTIES_PATH=file:///<%project_root_dir%>/config/integration-api-config/src/main/resources/local/sailor-api.yml
   *CROSSREFERENCE_SERVICE_PROPERTIES_PATH=file:///<%project_root_dir%>/config/integration-api-config/src/main/resources/local/crossreference-api.yml
7. Open a command prompt and run <code>./gradlew build</code> from the project root folder.  (This should download gradle AND build the project)
8. Go to any of the application root folder run application
	*Sailor API - cd /applications/sailor-api/
    *CrossReference API - cd /applications/crossreference-api/
    *Recommendation API - cd /applications/recommendation-api/
9. Run project with <code>./gradlew bootRun</code>
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
  * Sailor-API : Salesforce,C-360 and Seaware
  * Crossreference-API : MySQL DB
  * Recommendation-API : HBase
* Database configuration: (Refer "Local Setup" #4,#5)
* How to run tests: 
  <code>
  * ./gradlew test  (Default � only unit tests)
  * ./gradlew integrationTests  (Only integration tests)
  * ./gradlew functionalTests (Only functional tests)
  * ./gradlew testAll (All tests)
  </code>
* Deployment instructions: CI/CD pipeline is setup in Jenkins+Spinnaker (Documentation TBD)

### Contribution guidelines ###

* Writing tests: Combination of Junit, Mockito, Hamcrest and Rest Assured for validating behavior of the implemented component(s)
* Code review:  Setup pull requests for peer review.
* Other guidelines

### Who do I talk to? ###

* Repo owner: Murtaza Goga