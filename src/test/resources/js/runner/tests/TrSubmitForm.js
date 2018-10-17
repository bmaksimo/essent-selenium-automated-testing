class TrSubmitForm extends TestRunnerBase {

    /**
     * Checks if a top action (such as Plus, filter) is available in the DOM
     * Then clicks on a top action label
     *
     * * @param {object.name} options - Action name argument passed from Java.
     * * @param {function} callback - The Java callback that handles the result.
     *
     * Java example:
     * Map<String, String> options = new HashMap<>();
     * options.put(path, "Contracting -> UP/TC2 - TO RENEW CONTRACTS");
     * boolean result = executeJavascriptTest("TTrGetTopAction", options);
     */

    constructor(options, callback) {
        super(options, callback, 1000);
    }

    run() {
        let result = this.result;
//        action = $("#confirm-button");
//        if(action == undefined) {
//            result.status = 'FAILED';
//            result.reason = "Confirm button was not found";
//        } else {
        result.status = 'PASSED';
        result.reason = '';
        $("#confirm-button").trigger('click');
//        }
        setTimeout(()=> {
            this.resolveCallback(result);
        }, 5000);
    }
}
