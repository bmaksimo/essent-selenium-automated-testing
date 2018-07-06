class TrFetchDataSelection extends TestRunnerBase {

    /**
     *  TrFetchDataSelection fetches the DWP table model and returns to Java as JSon object
     *
     * * @param {object} options - Arguments passed from Java.
     * * @param {function} callback - The Java callback that handles the result.
     *
     * Java example:
     *  * Java example:
     * Map<String, Object> options = new HashMap<>();
     * options.put("include_selection", true);
     * boolean result = executeJavascriptTest("TrFetchDataSelection", options);
     */

    constructor(options, callback) {
        super(options, callback, 1000);
    }

    run(options, result) {
        result.status = 'PASSED';
        result.reason = '';
        result.column_names = [];
        result.rows = [];
        let includeSelection = options.include_selection;
        $('.list__content th').filter((i, e)=>{result.column_names.push($(e).text()); return true;});
        let rows = $('.list__content tr:not(".row__actions, .list__column-headers")');
        if(includeSelection == true) {
            rows = rows.filter((i, e) => $(e).find('td [type=checkbox]').is(":checked"));
        }
        result.rows = $(rows).map((i, e) => {
            return  $(e).find('td').map((ii, ee) => {
                return $(ee).text().trim();
            });
        });
        this.resolveCallback(result);
    }
}
