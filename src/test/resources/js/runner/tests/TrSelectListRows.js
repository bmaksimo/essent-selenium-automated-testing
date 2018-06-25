class TrSelectListRows extends TestRunnerBase {

    /**
     *  TrSelectListRow DWP table model and returns to Java as JSon object
     *
     * * @param {object} options - Arguments passed from Java.
     * * @param {function} callback - The Java callback that handles the result.
     *
     * Java example:
     *  * Java example:
     * Map<String, Object> options = new HashMap<>();
     * options.put("indices", new ArrayList<>());
     * boolean result = executeJavascriptTest("TrSelectListRows", options);
     */

    constructor(options, callback) {
        super(options, callback, 1000);
    }

    run(options, result) {
        result.status = 'PASSED';
        result.reason = '';
        let indices = this.options.indices;
        console.log(indices);
        let checkboxes = $('.list__content tr:not(".row__actions, .list__column-headers")  td [type=checkbox]');
        let heightI = indices.length;
        let height = checkboxes.size;
        if(heightI > height ) {
            result.status = 'FAILED';
            result.reason = `Number of indices ${heightI} cannot be greater than table height ${height}`;
        } else {
            console.log('--for()');
            let idx;
            console.log("---indices.length: " + heightI);
            for(idx = 0; idx < heightI; idx++) {
                let row = indices[idx];
                console.log('--row: ' + row);
                if(row >= height) {
                    result.status = 'FAILED';
                    result.reason = `Row ${row} cannot be greater than table height ${height}`;
                    break;
                } else {
                    checkboxes[row -1].click();
                }
            }
        }
        this.resolveCallback(result);
    }
}
