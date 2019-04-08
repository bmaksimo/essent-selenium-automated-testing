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
 * @deprecated Use of JavascriptTestRunner is deprecated.
 * @link NonEditable.java uses similar xPath location approach
 */
class BaseFormInput extends TestRunnerBase {


    constructor(options, callback) {
        super(options, callback, 2000);
    }

    run() {
        let result = this.result;
        result.status = 'UNDEFINED';
        result.reason = 'Not executed';
        const options = this.options;
        const label = options.label;
        const value = options.value;
        const XPATH_INPUT = `//div[label[normalize-space(text())='${label}']]`;
        let   xpathQuery = XPATH_INPUT;
        const card = options.card;
        if(card) {
            xpathQuery = `//div[div[normalize-space(h2/text())='${card}']]`  + XPATH_INPUT;
        }
        console.log('--XPATH: ' + xpathQuery);
        const elements = this.evaluateXpath(xpathQuery);
        if(elements.length >= 0) {
            let input = $(elements[0]).find("input, select");
            if(input.length > 0) {
                this.applyInput(input, value);
                const success = this.applyInput(input, value);
                if(success) {
                    result.status = "PASSED";
                    result.reason = '';
                } else {
                    result.status = "FAILED";
                    result.reason = 'Input value  rejected or wasn\'t set';
                }
            } else {
                result.status = "FAILED";
                result.reason = 'Input or select element, labelled ' + label + ', undefined.';
            }
        } else {
                result.status = "FAILED";
                result.reason = 'label element ' + label + ' undefined.';
        }
        this.resolveCallback(result);
    }

    applyInput(input, value) {
        input.val(value).trigger("change");
        return input.val() === value;
    }
}
