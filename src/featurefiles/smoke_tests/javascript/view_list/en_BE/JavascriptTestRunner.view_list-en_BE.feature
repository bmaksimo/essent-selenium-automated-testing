@_SMOKE
Feature: Javascript DWP testing

    Background:
        Given   I logged in to DWP as d.chebayewski.billinghouse@essent.be
    Scenario:
        When Left menu is sales-marketing
        And Top menu item is Market Transactions
        And Top action is Filters
        #And Filter element "EAN" input is "541444625522734400"
        And "Module" selection is "CANCEL"
        #Then 1st List element has cell value Closed at column EC Status & Effective Date
<<<<<<< HEAD:src/featurefiles/smoke_tests/javascript/view_list/en_BE/JavascriptTestRunner.view_list-en_BE.feature
<<<<<<< HEAD:src/featurefiles/smoke_tests/javascript/view_list/JavascriptTestRunner.view_list.feature
        Then Select 3 List rows having cell value CANCEL By market at column Module & Label
        And  Store cell values of selected list rows at column "EC Status & Effective Date" as "ec_status"
        And Selected List rows have cell value "CANCEL By market" at column "Module & Label"
=======
=======
>>>>>>> 0b002274da20afc85c4b6d973a9fd61d4ac49de0:src/featurefiles/smoke_tests/javascript/view_list/JavascriptTestRunner.view_list.feature
        #Then 3 List rows having cell value CANCEL By market at column Module & Label are selected
        Then 3 List rows having cell value CANCEL By Essent (Secured) at column Module & Label are selected
        And  Selected list rows at column "EC Status & Effective Date" are put to global parameter "ec_status"
        And Selected List rows have cell value "CANCEL By Essent (Secured)" at column "Module & Label"


<<<<<<< HEAD:src/featurefiles/smoke_tests/javascript/view_list/en_BE/JavascriptTestRunner.view_list-en_BE.feature
>>>>>>> develop:src/featurefiles/smoke_tests/javascript/view_list/en_BE/JavascriptTestRunner.view_list-en_BE.feature
=======
>>>>>>> 0b002274da20afc85c4b6d973a9fd61d4ac49de0:src/featurefiles/smoke_tests/javascript/view_list/JavascriptTestRunner.view_list.feature
