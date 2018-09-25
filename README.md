# README #

This README would normally document whatever steps are necessary to get your application up and running.

### What is this repository for? ###

* The repository is for clean (web front-end only) BDD tests of DWP front end
* Version is 2.53.0-SNAPSHOT
* [Learn Markdown](https://bitbucket.org/tutorials/markdowndemo)

### How do I get set up? ###

* Configuration
* Dependencies
  * nova-autocrat
* Database configuration
* How to run tests
  * Example:  
    mvn clean test -Dtest=DwpEssentRunnerTest -Denvironment=DEVINT01 -Dwebdriver.chrome.driver=/usr/local/bin/chromedriver "-Dchrome.user.data.path=/Users/vagrant/Work/Billinghouse/chrome_profiles/DWP Testing" -Dwebdriver.chrome.headless=headless -Dwebdriver.chrome.headless.window.size=1280x800 "-Dcucumber.options=--tags @SMOKE"

* Deployment instructions

### Jenkins jobs ###

  * Nightly: https://jenkins.nova.essent.be/job/Run_Selenium_Tests
  * Tag-driven: https://jenkins.nova.essent.be/job/Run_Selenium_Tests_Release/

### Contribution guidelines ###

* Writing Junit tests of project code ans smoke tests
  * Use TDD: Write tests in src/test/java/com/billinghouse and let them fail
  * Write Java code with your functionality in  src/test/java/com/billinghouse and test until the tets pass
* Writing Gherkin tests
  * Create the feature sub-dir in src/featurefiles
  * Create FeatureName.feature file there
  * Write your scenario in Gherkin language in this file
  * Use Autocompletion and code navigation when working on .feature file
  * Create Java code with step definitions in src/main/java/stepdefinitions/dwp
* Writing Page Objects
  * Smallest web elements such as Button, input, selection, chechbox, toggle button, date picker, label have to be wrapped (for example, ButtonImpl implements Button) 
    and not be used as WebElement.
   
### Conducting Code and Pull Requests Reviews ###
#### 1 Policy of approving pool requests ####
  *  1.1  We are not rejecting pool requests (PRs) unless the code is so obsolete 
    that it is nightmare to merge it in main branch.
    Instead, if we find issues in code, we give <span style="color:red">*"blocking"*</span> advice and
    don't approve the request until the findings are definitely resolved by the code author.
  * 1.2  If we find potential code smells, anti-patterns, or if we find the code not clean enough, we can give
     <span style="color:green">*"non-blocking"*</span> advice.  
     <span style="color:green">*"Non-blocking"*</span> advice is also bad sign, but we approve the code 
     because code authors have credits for improving the findings, and they improve the code later on before they merge the code in devel.
#### 2 Purpose of pool request review ####
  * 2.1 Purpose is rectification of obvious mistakes.
#### 3 Sufficient number of approvals and precedence of "blocking" issues over the number of approvals####
  * 3.1 If the request has two or more approvals, and there are no "blocking" issues, the developer may merge the branch 
  into develop.
#### 4 Direct commits to develop ####
  * 4.1 Only maintenance commits without PR are allowed, such as amendments of functional user Ids.

#### 5 Priorities and focus of PR reviews ####
##### 5.1 Hard-coded language-specific info in web element locators #####
  Hard-coded language specific info in locators is prohibited.
  They are <span style="color:red">*"blocking"*</span> issues and PR containing such locators will 
  not be approved.
##### 5.2 Quality of web element locators #####
  Locators extracted with Chrome or recording plugins, or locators relying 
  on assumptions about static HTML structure are not allowed.
  They are <span style="color:red">*"blocking"*</span> issues and PR containing such locators will 
  not be approved.
##### 5.3 Wrapping smallest web elements #####
  It is *recommended* that smallest angular web elements such as button, 
  input, selection, chechbox, toggle button, date picker, label have are wrapped (for example, ButtonImpl implements Button) and not be used as WebElement.
  If not, we give <span style="color:green">*"non-blocking"*</span> advice, and PR should be approved.
##### 5.4  Precedence of location strategies #####
  Precedence of location strategies is as follows:
  * By.id, 
  * By.name
  * By.cssSelector, 
  * JavascriptTestRunner should be used in DWP as table column/row  lookup strategy where HTML is extremely complex or
      changes dynamically or to work with timing issues of ajax
  * By.xpath
  If your PR has precedence of complex xpath locators over by.name for DWP,
  we give <span style="color:green">*"non-blocking"*</span> advice, and PR will be approved.
  Before merging your PR you may be asked to find out how to enable By.name location in DWP web application.
##### 5.5 Using Thread.sleep() #####
  Using Thread.sleep() is considered as test smell. It increases test execution time, 
  and it is based on wrong assumptions of application timing, disconnected 
  from the nature of asynchronous conversations between the front-end and server.
  If your PR has Thread.sleep() we will give <span style="color:green">*"non-blocking"*</span> advice, 
  and PR will be approved. Before merging your code in develop, you will need to replace Thread.sleep() 
  with FluentWaiter, Awaitility or any solution based on Javascript native interception points, such as promise or callback or specific methods depending on implementation
  of web app.

### Logging guidelines ###

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

* Repo owners and admins are: Dmitry Chebayewski, Jim van Dam
* Billinghouse and Levi9 Community: Branka Rakic, Chris Pasti, Dmitry Chebayewski, Jelena Jovic, Fernando Mano 
