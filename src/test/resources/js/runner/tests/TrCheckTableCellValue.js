/**
 * Checks if Table Cell is present in the DOM
 *  and clicks the Url in the table cell if available.
 *
 * * @param {object} options - Arguments passed from Java.
 * * @param {function} callback - The Java callback that handles the result.
 *
 * Java example:
 * boolean result = executeJavascriptTest("TrClickTableCellUrl", options);
 */
class TrCheckTableCellValue extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 1000);
    }

    run(options, result) {
        result.status = 'FAILED';
        result.reason = 'Not executed';
        result.column = {"index": -1, "caption": options.column};
        const caption = options.column;
        const row = parseInt(options.index) * 2 - 1;
        const data = options.data;

        let index = $(".list__content th:contains('" + caption + "')").index();
        if (index < 0) {
            result.status = 'FAILED';
            result.reason = 'Column ' + caption + ' was not found.';
        } else if ($("#rows tr:nth-child(" + row + ")") < 0) {
            result.status = 'FAILED';
            result.reason = 'Row ' +  caption + ' was not found.';
        } else {
            const query = "#rows tr:nth-child(" + row + ") td:nth-child(" + ++index + ") list-simple-two-liner-cell";
            const elem = $(query).text().indexOf(data);
            if(elem > -1) {
                result.status = 'PASSED';
                result.column.index = index;
                result.reason = '';
            } else {
                result.reason  = 'Input provided was not found: ' + data;
                result.column  = options.column;
            }
        }

        this.resolveCallback(result);
    }
}
