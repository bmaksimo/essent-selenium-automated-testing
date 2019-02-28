/**
 * Checks if Filter element is available in the DOM
 * Then initialises the filter value with given value
 *
 * * @param {object.name} options - Action name argument passed from Java.
 * * @param {function} callback - The Java callback that handles the result.
 *
 * Java example:
 * Map<String, String> options = new HashMap<>();
 * options.put("label", "Type klant");
 * options.put("value", "Klant");
 * boolean result = executeJavascriptTest("TrFormSelection", options);
 */
class TrFormSelection extends BaseFormInput {
    constructor(options, callback) {
        super(options, callback, 500);
    }

    applyInput(input, value) {
        let option = input
            .find("option")
            .filter((i, e) => {
            return $(e).text() === value;
        });
        if(option.size() != 1)
            return false;
        let selection = option.attr("value");
        input.val(selection).trigger("change");
        return input.val() === selection;
    }
}
