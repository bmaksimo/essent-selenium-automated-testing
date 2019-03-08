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
        super(options, callback, 3000);
    }
    run(options, result) {
        let label = options.label;
        console.log("label: ");
        console.log(label);

        let input = $(`label:contains(${label})`).parent().find(".input__toggle input");
        console.log("input: ");
        console.log(input);

        let inputSize = input.size();
        console.log("inputSize: ");
        console.log(inputSize);


        if (inputSize === 0) {
            result.status = 'FAILED';
            result.reason = 'Input element with id ' + options.id + ' not found';
        } else {
            for(i=0; i< inputSize; i++) {
                input[i].click();
            }
            result.status = 'PASSED';
            result.reason = '';
        }
        this.resolveCallback(result);
    }
}
