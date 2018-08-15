class TrPlusActionFromList extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 1000);
    }

    run(options, result) {
            result.status = 'FAILED';
            result.reason = 'After ' + options.schedule_seconds + ' seconds content page did not contain title: ' + options.header;
            const headerName = $(".list__header").children().filter(function (i, node) {
                return node.innerText.trim() === options.headerName;
            }).length;
            console.log(headerName);
            if (headerName > 0) {
                result.status = 'PASSED';
                result.reason = '';
            }
            this.resolveCallback(result);
        }
}
