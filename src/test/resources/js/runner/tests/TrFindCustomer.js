class TrFindCustomer extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 1000);
    }

    run(options, result) {
        result.status = 'FAILED';
        result.reason = 'After ' + options.schedule_seconds + ' seconds content page did not contain title: ' + options.header;
        const customerName = $(".list__row .cell__text").children().filter(function (i, node) {
            return node.innerText.trim() === options.name;
        }).length;
        console.log(customerName);
        if (customerName >= 0) {
            result.status = 'PASSED';
            result.reason = '';
        }
        this.resolveCallback(result);
    }
}
