/**
 *  TrIsNextButtonEnabled toggles DWP toggle input located by label
 *
 * * @param {object} options - Arguments passed from Java.
 * * @param {function} callback - The Java callback that handles the result.
 *
 * Java example:
 *  * Java example:
 * Map<String, String> options = new HashMap<>();
 * Map result = executeJavascriptMethod("TrIsNextButtonEnabled", options);
 * Javascript:
 */
class TrIsNextButtonEnabled extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 100);
    }
    run(options, result) {
       let button = $(".top-actions #primaryButton");
       if (button.size() == 0) {
           result.enabled = 'undefined';
       } else if ($(button[0]).attr("disabled")) {
           result.enabled = 'false';
       } else {
           result.enabled = 'true';
       }
       this.resolveCallback(result);
    }
}
