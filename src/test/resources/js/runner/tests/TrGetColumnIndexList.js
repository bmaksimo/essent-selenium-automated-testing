class TrGetColumnIndexList extends TestRunnerBase {

    /**
     * Checks if a menu-item is present in the DOM
     *
     * * @param {object} options - Arguments passed from Java.
     * * @param {function} callback - The Java callback that handles the result.
     *
     * Java example:
     * assertThat(executeJsTest("new TrGetColumnIndexList({menu: 'mainMenu', linkId: 'sales-marketing-link', arguments[arguments.length - 1]);", link), is(true));
     */

    constructor(options, callback) {
        super(options, callback, 200);
    }

    run() {
        setTimeout(()=> {
            let result = this.result;
            let options = this.options;
            result.status = 'FAILED';
            result.reason = 'Not executed';
            result.column = {"index": -1, "caption": options.column};
            let index = $(".list__content th:contains('" + options.column + "')").index();
            if (index < 0) {
                result.status = 'FAILED';
                result.reason = 'Column ' + options.column + ' was not found.';
                console.log("index: " + index);
            } else if ($("#rows tr:nth-child(1)") < 0) {
                result.status = 'FAILED';
                result.reason = 'Row was not found.';
                console.log(result);
            } else {
                result.status = 'PASSED';
                result.column.index = ++index;
                let elem = $("#rows tr:nth-child(1) td:nth-child(" + index + ") div a");
                result.reason = 'Navigation, click on ' + elem.text();
                result.column.caption = options.column;
                console.log(result);
                elem.click();
            }
            this.resolveCallback(result);
        }, 5000);
    }

}
