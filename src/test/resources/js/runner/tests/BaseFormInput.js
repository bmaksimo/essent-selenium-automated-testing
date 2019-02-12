/**
 * Checks if Filter element is available in the DOM
 * Then initialises the filter value with given value
 *
 * * @param {object.name} options - Action name argument passed from Java.
 * * @param {function} callback - The Java callback that handles the result.
 *
 * Java example:
 * Map<String, String> options = new HashMap<>();
 * options.put("label", "Contract number");
 * boolean result = executeJavascriptTest("TrFormInput", options);
 */
class BaseFormInput extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 500);
    }

    run() {
        let result = this.result;
        result.status = 'UNDEFINED';
        result.reason = 'Not executed';
        let options = this.options;
        let label = options.label;
        let value = options.value;
        let xPath = `//div[@class='input' | @class='input label-inline' and label/text()='${label}']`;
        console.log('--XPATH: ' + xPath);
        let elements = this.evaluateXpath(xPath);
        if(elements.length >= 0) {
            let input = $(elements[0]).find("input, select");
            if(input.length == 1) {
                let success = this.applyInput(input, value);
                if(success) {
                    result.status = "PASSED";
                    result.reason = '';
                } else {
                    result.status = "FAILED";
                    result.reason = 'Input value was rejected';
                }
            } else {
                result.status = "FAILED";
                result.reason = 'Label ' + label + ' input undefined.';
            }
        } else {
                result.status = "FAILED";
                result.reason = 'label element ' + label + ' undefined.';
        }
        this.resolveCallback(result);
    }

    applyInput(input, value) {
        input.val(value).trigger("change");
        return true;
    }
}
