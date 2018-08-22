class TrDatePickerInput extends TbrFormInput {

    /**
     * Checks if date picker element is available in the DOM
     * Then initialises the date picker with given value
     *
     * * @param {object.name} options - Action name argument passed from Java.
     * * @param {function} callback - The Java callback that handles the result.
     *
     * Java example:
     * Map<String, String> options = new HashMap<>();
     * options.put("label", "Contract number");
     * boolean result = executeJavascriptTest("TrDatePickerInput", options);
     */

    constructor(options, callback) {
        super(options, callback, 500);
    }

    applyInput(input, value) {
        $(input[0]).val(value).trigger("change").trigger("keyup");
    }
}
