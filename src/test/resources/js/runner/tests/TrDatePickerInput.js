class TrDatePickerInput extends BaseFormInput {

    /**
     * Checks if date picker element is available in the DOM
     * Then initialises the date picker with given value
     *
     * * @param {object.name} options - Action name argument passed from Java.
     * * @param {function} callback - The Java callback that handles the result.
     *
     * Java example:
     * Map<String, String> options = new HashMap<>();
     * options.put("label", "Contract start date");
     * options.put("value", "Contract start date");
     * boolean result = executeJavascriptTest("TrDatePickerInput", options);
     */

    constructor(options, callback) {
        super(options, callback, 1000);
    }

    applyInput(input, value) {
        $(input[0]).val(value).trigger("change").trigger("keyup");
        return $(input[0]).val() === value;
    }
}
