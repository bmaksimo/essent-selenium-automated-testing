class TrCheckViewListHeader extends TestRunnerBase {

    /**
     * Checks if a title is present in the DOM
     *
     * * @param {object} options - Arguments passed from Java.
     * * @param {function} callback - The Java callback that handles the result.
     */

    constructor(options, callback) {
        let timeoutMillis = parseInt(options.schedule_seconds) * 1000;
        super(options, callback, timeoutMillis);
    }

    run(options, result) {
        result.status = 'FAILED';
        result.reason = 'After ' + options.schedule_seconds + ' seconds content page did not contain title: ' + options.header;
        const length = $(".list__header").children().filter(function (i, node) {
            return node.innerText.trim() === options.header;
        }).length;
        if (length > 0) {
            result.status = 'PASSED';
            result.reason = '';
        }
        this.resolveCallback(result);
    }
}
