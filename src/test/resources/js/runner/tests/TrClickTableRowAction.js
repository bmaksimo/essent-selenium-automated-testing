class TrClickTableRowAction extends TestRunnerBase {
    constructor(options, callback) {
        super(options, callback, 1000);
    }

    run(options, result) {
        result.status = 'FAILED';
        result.reason = 'Not executed';

        const rowAction = options.rowAction;
        const rowActionElement = $("list-row-action[label='" + rowAction + "'] > a");

        if (rowActionElement.index() > -1) {
            rowActionElement.click();
            result.status = 'PASSED';
            result.reason = '';
        } else {
            result.reason = 'Action ' + rowAction + ' is not present' ;
        }

        this.resolveCallback(result);
    }
}
