/**
 *   Checks whether List Action button is available in DOM
 *   and clicks on it if available.
 *
 * * @param {object} options - Arguments passed from Java.
 * * @param {function} callback - The Java callback that handles the result.
 *
 * Java example:
 * boolean result = executeJavascriptTest("TrGetListAction", options);
 */
class TrGetListAction extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 5000);
    }

    run() {
        let result = this.result;
        const options = this.options;
        result.status = 'UNDEFINED';
        result.reason = 'Not executed';
        let matches = $(`.list__actions [name='${options.name}']`);
        if(matches.length > 0) {
            matches[0].click();
            result.status = 'PASSED';
            result.reason = '';
        } else {
            result.status = 'FAILED';
            result.reason = `Element name = '${options.name}' not found`;
        }
        this.resolveCallback(result);
    }
}
