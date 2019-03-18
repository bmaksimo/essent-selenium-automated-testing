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
        const label = options.label;
        const input = $(`label:contains(${label})`).parent().find(".input__toggle input");
        let inputSize = input.size();

        if (inputSize !== 0) {
            if(options.verb) {
                switch(options.verb) {
                    case "are": {
                        for(let i=0; i< inputSize; i++) {
                            input[i].click();
                        }
                        break;
                    }
                    case "is": {
                        input[0].click();
                        break;
                    }
                }

                result.status = 'PASSED';
                result.reason = '';
            } else {
                result.status = 'FAILED';
                result.reason = 'Verb must be either "is" or "are"';
            }
            else {
            result.status = 'FAILED';
            result.reason = 'Input element with id ' + options.id + ' not found';
        }
        this.resolveCallback(result);
    }
}
