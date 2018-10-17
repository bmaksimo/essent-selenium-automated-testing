/**
 *   Fetches DWP table model and returns to Java as JSon object
 *
 * * @param {object} options - Arguments passed from Java.
 * * @param {function} callback - The Java callback that handles the result.
 *
 * Java example:
 * boolean result = executeJavascriptTest("TrGetTableModel", options);
 */
class TrGetTableModel extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 5000);
    }

    run(options, result) {
        result.status = 'PASSED';
        result.reason = '';
        result.column_names = [];
        result.rows = [];
        $('.list__content th').filter((i, e)=>{result.column_names.push($(e).text()); return true;});
        let rows = $('.list__content tr:not(".row__actions, .list__column-headers")');
        result.rows = $(rows).map((i, e) => {
            return  $(e).find('td').map((ii, ee) => {
                return $(ee).text().trim();
            });
        });
        this.resolveCallback(result);
    }
}
