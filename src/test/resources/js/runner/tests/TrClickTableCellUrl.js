class TrClickTableCellUrl extends TestRunnerBase {

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

    constructor(options, callback) {
        super(options, callback, 1000);
    }

    run(options, result) {
        result.status = 'FAILED';
        result.reason = 'Not executed';
        result.column = {"index": -1, "caption": options.column};
        let caption = options.column;
        let row = parseInt(options.index) * 2 - 1;
        let index = $(".list__content th:contains('" + caption + "')").index();
        if (index < 0) {
            result.status = 'FAILED';
            result.reason = 'Column ' + caption + ' was not found.';
        } else if ($("#rows tr:nth-child(" + row + ")") < 0) {
            result.status = 'FAILED';
            result.reason = 'Row ' +  caption + ' was not found.';
        } else {
            let query = "#rows tr:nth-child(" + row + ") td:nth-child(" + ++index + ") div a";
            let elem = $(query);
            console.log("--QUERY: " + query);
            console.log("--CELL TEXT: " + elem.text());
            console.log("--ELEM index(): " + elem.index());
            if(elem.index()  > -1) {
                console.log(elem.text);
                elem.click();
                result.status = 'PASSED';
                result.column.index = index;
                result.reason = '';
            } else {
                result.reason  = 'Navigation, click on ' + elem.text();
                result.column  = options.column;
            }

        }
        this.resolveCallback(result);
    }
}
