/**
 * Checks if a title is present in the DOM
 *
 * * @param {object} options - Arguments passed from Java.
 * * @param {function} callback - The Java callback that handles the result.
 */
class TrCheckFormHeader extends TestRunnerBase {

    constructor(options, callback) {
        let timeoutMillis = parseInt(options.schedule_seconds) * 1000;
        super(options, callback, timeoutMillis);
    }

    run(options, result) {
        result.status = 'FAILED';
        result.reason = 'After ' + options.schedule_seconds + ' seconds content page did not contain title: ' + options.header;
        const length = $(".form__header").children().filter(function (i, node) {
            return $(node).text().trim() === options.header;
        }).length;
        if (length > 0) {
            result.status = 'PASSED';
            result.reason = '';
        }
        this.resolveCallback(result);
    }
}
//# TrCheckFormHeader
