//Javascript libraries
load("https://cdnjs.cloudflare.com/ajax/libs/lodash.js/4.17.5/lodash.js");
//Imported Java methods
var Locale = Java.type('java.util.Locale');
//Global variables
var options;
var result;
/**
 * Function used as "constructor"
 */
function JGetUserLanguage(options) {
     print("Step: JGetUserLanguage");
     print(" - Options: options.options.languageKey = '" + options.options.languageKey + "'");
     this.options = options;
     return runTest(options);
}
/**
 * Functions used as "private"
 */
function runTest(options) {
     this.result = {
                      "userLanguage" : Locale.getDefault(),
                      "runTest": runTest,
                      "self": this
                    };
     print("Step: runTest");
     print(" - Result: result.userLanguage = '" + this.result.userLanguage + "'");
     return this.result;
};







