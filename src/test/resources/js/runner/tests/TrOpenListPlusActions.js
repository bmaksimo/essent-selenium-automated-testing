class TrOpenListPlusActions extends TestRunnerBase {

    /**
     *  TrOpenListPlusActions
     *  Clicks on Plus and opens Plus actions row at given row
     * * @param {object} options - Arguments passed from Java.
     * * @param {function} callback - The Java callback that handles the result.
     *
     * Java example:
     *  * Java example:
     * Map<String, String> options = new HashMap<>();
     * options.put("index", 1);
     * boolean result = executeJavascriptTest("TrOpenListPlusActions", options);
     */

    constructor(options, callback) {
        super(options, callback, 1000);
    }

    run(options, result) {
        result.status = 'PASSED';
        result.reason = '';
        let row = this.options.index;
        let plusCells = $('.list__content tr:not(".row__actions, .list__column-headers")  .cell__action a');
        let height = plusCells.size;
        if(row > height ) {
            result.status = 'FAILED';
            result.reason = `Row ${row} cannot be greater than table height ${height}`;
        } else {
            plusCells[row - 1].click();
        }
        this.resolveCallback(result);
    }
}
