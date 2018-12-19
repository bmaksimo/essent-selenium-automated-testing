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
class TrClickTableCellUrl extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 1000);
    }

    run(options, result) {
        result.status = 'FAILED';
        result.reason = 'Not executed';
        result.column = {"index": -1, "caption": options.column};
        let caption = options.column;
        let row = parseInt(options.index) * 2 - 1;

        let viewListName = options.view_list_name;
        if ("Plus Action" === caption && null !== viewListName) {
            if ("Billing customer" === viewListName) {
                $("list-plus-cell[list-key='BillingCustomerOnaccount'] > div > a").trigger("click");
                result.status = 'PASSED';
                result.reason = '';
            } else if ("Marktberichten" === viewListName) {
                let chosenRow = options.index - 1;
                $("list-plus-cell[list-key='MarketTransactionsOnAccount'] > div > a")[chosenRow].click();
                result.status = 'PASSED';
                result.reason = '';
            } else {
                result.status = 'FAILED';
                result.reason = 'Unknown view list ' + viewListName;
            }
        }
        else {
            let index = $(".list__content th:contains('" + caption + "')").index();
            console.log("ROW: " + row);
            console.log("INDEX: " + index);
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
        }

        this.resolveCallback(result);
    }
}
