class TrSelectListRow extends TestRunnerBase {

    /**
     *  TrSelectListRow DWP table model and returns to Java as JSon object
     *
     * * @param {object} options - Arguments passed from Java.
     * * @param {function} callback - The Java callback that handles the result.
     *
     * Java example:
     *  * Java example:
     * Map<String, String> options = new HashMap<>();
     * options.put("index", 1);
     * boolean result = executeJavascriptTest("TrSelectListRow", options);
     */

    constructor(options, callback) {
        super(options, callback, 1000);
    }

    run(options, result) {
        result.status = 'PASSED';
        result.reason = '';
        let row = this.options.index;
        let checkboxes = $('.list__content tr:not(".row__actions, .list__column-headers")  td [type=checkbox]');
        let width = checkboxes.size;
        if(row > width ) {
            result.status = 'FAILED';
            result.reason = `Row ${row} cannot be greater than table width ${width}`;
        } else {
            checkboxes[row -1].click();
        }
        this.resolveCallback(result);
    }
}
