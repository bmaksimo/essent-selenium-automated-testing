class TrToggleCheckBox extends TestRunnerBase {

    /**
     *  TrToggleCheckBox toggles DWP check box selected by text
     *
     * * @param {object} options - Arguments passed from Java.
     * * @param {function} callback - The Java callback that handles the result.
     *
     * Java example:
     *  * Java example:
     * Map<String, String> options = new HashMap<>();
     * options.put("label", "Gas Fix B2C (TC1)");
     * options.put("state", "unchecked")
     * boolean result = executeJavascriptTest("TrToggleCheckBox", options);
     */

    constructor(options, callback) {
        super(options, callback, 1000);
    }

    run(options, result) {
        result.status = 'PASSED';
        result.reason = '';
        let label = this.options.label;
        let state = this.options.state;
        let selector = `.input__checkbox:contains('${label}') input`;
        let input = $(selector);
        if(input.size() == 0) {
            result.status = 'FAILED';
            result.reason = `Checkbox ${label} was not found`;
        } else {
            let checked = input.attr("checked");
            input = $(selector);
            if(checked !== state) {
                input.click();
            }
        }
        this.resolveCallback(result);
    }
}
