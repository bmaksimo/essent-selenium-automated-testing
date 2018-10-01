/**
 * Checks if input element is available in the DOM
 * Then initialises the input with given value
 *
 * * @param {object.name} options - Action name argument passed from Java.
 * * @param {function} callback - The Java callback that handles the result.
 *
 * Java example:
 * Map<String, String> options = new HashMap<>();
 * options.put("selector", "");
 * boolean result = executeJavascriptTest("TrApplyFormInput", options);
 */
class TrApplyFormInput extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 500);
    }
    run() {
        let result = this.result;
        result.status = 'UNDEFINED';
        result.reason = 'Not executed';
        let options = this.options;
        let selector = options.selector;
        let value = options.value;
        let input = $(selector);
        if(input.index() == 0)  {
            input.val(value).trigger("change");
            result.status = "PASSED";
            result.reason = '';
        } else {
            result.status = "FAILED";
            result.reason = `Id ${selector} undefined.`
        }
        this.resolveCallback(result);
    }
}
