class TrSelectContractline extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 1000);
    }

    run(options, result) {
        result.status = 'UNDEFINED';
        result.reason = 'Not found';
        let placeholder = $('.button-placeholder')[0];
        if (placeholder != 0) {
            placeholder.click();
            result.status = 'PASSED';
            result.reason = '';
        } else {
            result.status = 'FAILED';
            result.reason = 'Start new market scenario ' + options.element + 'not found';
        }
        this.resolveCallback(result);
    }
}
