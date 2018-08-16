class TrPlusActionFromList extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 1000);
    }

    run(options, result) {
            console.log("Parameter header name is " + options.headerName);
            result.status = 'FAILED';
            result.reason = 'After ' + options.schedule_seconds + ' seconds content page did not contain title: ' + options.header;
            const headerName = $(".list__header").children().filter(function (i, node) {
                return node.innerText.trim() === options.headerName;
            }).length;
            let plusBtn = $('.list__content .cell__action')[headerName];
            console.log("Header names is: " + headerName);
            console.log("Plus button is: " + plusBtn);
            if (headerName > 0) {
                plusBtn.click();
                result.status = 'PASSED';
                result.reason = '';
            }
            this.resolveCallback(result);
        }
}
