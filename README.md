# README #

This README would normally document whatever steps are necessary to get your application up and running.

### What is this repository for? ###

* The repository is for clean (web front-end only) Cucumber-JVM and Selenium tests
* Version 0.9
* [Learn Markdown](https://bitbucket.org/tutorials/markdowndemo)

### How do I get set up? ###

* Summary of set up
* Configuration
* Dependencies
  * nova-autocrat
* Database configuration
* How to run tests
  * Example:  
    mvn clean test -Dtest=DwpEssentRunnerTest -Denvironment=DEVINT01 -Dwebdriver.chrome.driver=/usr/local/bin/chromedriver "-Dchrome.user.data.path=/Users/vagrant/Work/Billinghouse/chrome_profiles/DWP Testing" -Dwebdriver.chrome.headless=headless -Dwebdriver.chrome.headless.window.size=1280x800 "-Dcucumber.options=--tags @SMOKE"

* Deployment instructions

### Contribution guidelines ###

* Writing Junit tests of project
  * Use TDD: Write tests in src/test/java/com/billinghouse and let them fail
  * Write Java code with your functionality in  src/test/java/com/billinghouse and test until the tets pass
* Writing Gherkin tests
  * Create the feature sub-dir in src/featurefiles
  * Create FeatureName.feature file there
  * Write your scenario in Gherkin language in this file
  * Use Autocompletion and code navigation when working on .feature file
  * Create Java code with step definitions in src/main/java/stepdefinitions/dwp
   
* Code review
* Other guidelines
  * Logging guidelines
  
    * Context
    
    Provide reasonable description of context of the event (action) and result (reaction) you are logging. 
  
    * Formatting
    
    Formatting structures the logs. 
    Structuring helps both machines and humans read the data more efficiently.
    In this context, the most commonly used formatting methods are JSON and KVP (key=value pairs). 
    
    Example:
    Both formats will help you achieve the same purpose — making the logs human readable and enable more efficient parsing and analysis, but which one you choose to use will depend on the analysis tool you want to use. If it’s the ELK Stack, for example, JSON is the format you will want to use.
    
    * Example Logging Automated Tests Results
    
    //Workflow tier
    
    INFO - STEP:
    
    //Workflow tier or element
    
    INFO -  - ACTION: TYPING
    
    //Workflow tier or element
    
    INFO -  - ELEMENT: DELIVERY_ADDR_HOUSE_ADD
    
    /Event
    
    INFO -  - ELEMENT QUERY: [XPATH] [//validation-wrapper/div[label[text() = 'Delivery address']]//input[@id='address-addition-field']] [null]
    
    //Result
    
    INFO - Found 1 elements: DELIVERY_ADDR_HOUSE_ADD
    
    //Event
    
    INFO - Target: [DELIVERY_ADDR_HOUSE_ADD] --> <input>
    
    //Result
    
    ERROR - Failure: org.openqa.selenium.WebDriverException: unknown error: keys should be a string
    
  

### Who do I talk to? ###

* Repo owner or admin: Dmitry Chebayewski, Jim van Dam
* Other community or team contact: Bob Stoute, Chris Pasti, Dmitry Chebayewski, Pascal Huisman
