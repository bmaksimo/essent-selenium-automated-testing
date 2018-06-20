class TrContentPageContainsTitle extends TestRunnerBase {

    /**
     * Checks if a title is present in the DOM
     *
     * * @param {object} options - Arguments passed from Java.
     * * @param {function} callback - The Java callback that handles the result.
     */

    constructor(options, callback) {
        let timeoutMillis = parseInt(options.seconds) * 1000;
        super(options, callback, timeoutMillis);
    }

    run(options, result) {
        result.status = 'FAILED';
        result.reason = 'After ' + options.seconds + ' seconds content page did not contain title: ' + options.title;
        const length = $(".list__header").children().filter(function (i, node) {
            return node.innerText.trim() === options.title;
        }).length;
        if (length > 0) {
            result.status = 'PASSED';
            result.reason = '';
        }
        this.resolveCallback(result);
    }
}
