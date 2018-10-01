/**
 * Checks if Arrow action (such as back, up) is available in the DOM
 * Then clicks on a top action label
 *
 * * @param {object.name} options - Action name argument passed from Java.
 * * @param {function} callback - The Java callback that handles the result.
 *
 * Java example:
 * Map<String, String> options = new HashMap<>();
 * options.put("arrow", "back");
 * boolean result = executeJavascriptTest("TrArrowAction", options);
 */
class TrArrowAction extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 500);
    }
    run() {
        let result = this.result;
        const options = this.options;
        result.status = 'UNDEFINED';
        result.reason = 'Not executed';
        let arrows  = new Map([['back', 0], ['up', 1]]);
        let index = arrows.get(options.arrow);
        if(index != undefined ) {
            $('.focus-content .top').children('a')[index].click();
            result.status = 'PASSED';
            result.reason = '';
        } else {
            result.status = 'FAILED';
            result.reason = 'Arrow ' + options.arrow + ' undefined';
        }
        this.resolveCallback(result);
    }

}
