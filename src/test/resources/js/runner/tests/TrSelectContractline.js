class TrSelectContractline extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 1000);
    }

    run(options, result) {
        result.status = 'UNDEFINED';
        result.reason = 'Not found';
        let matches = [];
        if (matches >= 0) {
            $('.button-placeholder')[0].click();
            result.status = 'PASSED';
            result.reason = '';
        } else {
            result.status = 'FAILED';
            result.reason = 'Start new market scenario ' + options.element + 'not found';
        }
        setTimeout(()=> {
            this.resolveCallback(result);
        }, 1000);
    }
}
