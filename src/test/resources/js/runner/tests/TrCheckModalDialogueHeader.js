/**
 * Checks the header of a DWP modal dialog
 *
 * @param {object} options - Arguments passed from Java.
 * @param {function} callback - The Java callback that handles the result.
 *
 * Java example:
 * Map<String, String> options = new HashMap<>();
 * options.put("header", "Sign quote");
 * boolean result = executeJavascriptTest("TrCheckModalDialogueHeader", options);
 * JS example:
 * new TrCheckModalDialogueHeader({"header": "Sign quote"}, ()=>{});
 */
class TrCheckModalDialogueHeader extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 5000);
    }

    run (options, result) {
        result.status = 'UNDEFINED';
        result.reason = 'Not executed';
        let headerResult  = $(".view__modal .modal__header");
        let header = options.header;
        if(headerResult.size() > 0 && headerResult.text().trim() === header) {
            result.status = "PASSED";
            result.reason = '';
        } else {
            result.status = "FAILED";
            result.reason = `Header ${header} was not found `;
        }
        this.resolveCallback(result);
    }
}
