/**
 * Checks if Table Cell is present in the DOM
 *  and clicks the Url in the table cell if available.
 *
 * * @param {object} options - Arguments passed from Java.
 * * @param {function} callback - The Java callback that handles the result.
 *
 * Java example:
 * boolean result = executeJavascriptTest("TrPlusActionInMarketMessageTable", options);
 */
class TrPlusActionInMarketMessageTable extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 1000);
    }

    run(options, result) {
        result.status = 'FAILED';
        result.reason = 'Not executed';
        result.column = {"index": -1, "caption": options.column};

        let chosenRow = options.index - 1;
        let plusActionButton = $("list-plus-cell[list-key='MarketTransactionsOnAccount'] > div > a");

        if (plusActionButton.index() > -1) {
            plusActionButton[chosenRow].click();
            result.status = 'PASSED';
            result.reason = '';
        } else {
            result.reason = 'Market message button was not found.';
        }

        this.resolveCallback(result);
    }
}
