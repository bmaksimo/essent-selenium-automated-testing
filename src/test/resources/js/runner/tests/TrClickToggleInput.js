/**
 *  TrClickToggleInput toggles DWP toggle input located by label
 *
 * * @param {object} options - Arguments passed from Java.
 * * @param {function} callback - The Java callback that handles the result.
 *
 * Java example:
 *  * Java example:
 * Map<String, String> options = new HashMap<>();
 * options.put("label", "Is de meter geopend?");
 * boolean result = executeJavascriptTest("TrClickToggleInput", options);
 * Javascript:
 */
class TrClickToggleInput extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 1000);
    }
    run(options, result) {
        let label = options.label;
        let input = $(`label:contains(${label})`).parent().find(".input__toggle input");
        let inputSize = input.size();
        if (inputSize === 0) {
            result.status = 'FAILED';
            result.reason = 'Input element with id ' + options.id + ' not found';
        } else {
            input[0].click();
            result.status = 'PASSED';
            result.reason = '';
        }
        this.resolveCallback(result);
    }
}
