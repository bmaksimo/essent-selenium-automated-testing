@DWP
@BASIC
@SMOKE
@REGRESSION
Feature: DWP Tool Framework: Annotations, Zephyre


    Scenario: We can work around the Cucumber limitation:
              "cucumber supports only two hooks (Before & After) which works at the start and the end of the test scenario."
              and work with proprietary annotation-based hooks at  the start and the end of the test step

        #@OutputParameter(name="contractor")
        Given I execute the scenario step that has different annotated output parameters

        Then I execute next scenario step and see input parameters initialized with output parameters from previous steps
