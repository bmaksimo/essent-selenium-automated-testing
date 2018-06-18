class TrContentPageContainsTitle extends TestRunnerBase {

    /**
     * Checks if a title is present in the DOM
     *
     * * @param {object} options - Arguments passed from Java.
     * * @param {function} callback - The Java callback that handles the result.
     */

    constructor(options, callback) {
        super(options, callback, 100);
    }

    run() {
        let result = this.result;
        console.log("current result: " + result);
        const options = this.options;
        console.log("current options: " + options);
        result.status = 'FAILED';
        result.reason = 'After ' + options.seconds + ' seconds content page did not contain title: ' + options.title;
        const length = $(".list__header").children().filter(function (i, node) {
            console.log(i + ': ' + node.innerText);
            return node.innerText.trim() === options.title;
        }).length;
        if (length > 0) {
            result.status = 'PASSED';
            result.reason = '';
        }
        this.resolveCallback(result);
    }

}
