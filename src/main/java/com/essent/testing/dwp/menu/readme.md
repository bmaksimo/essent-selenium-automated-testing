Package creation guidelines for DWP test automation project.
Might be reasonable to move the code to the separate project.

Packaging the Common Components 
- com.essent.testing.dwp - packaging the Java components, used by Cucumber steps

Developing bigger process in the separate area of DWP
- com.essent.testing.dwp.{testing-area}
For example, com.essent.testing.dwp.menu if you are developing the menu tests
- com.essent.testing.dwp.{testing-area}.model - Any Java interfaces, enumerations, beans that are defined for the
{testing-area}
- com.essent.testing.dwp.{testing-area}.service - Any Java services that you create, and that are intended for using 
 by Cucumber steps
- com.essent.testing.dwp.{testing-area}.service.impl - Any implementations of Any Java services that you create above



