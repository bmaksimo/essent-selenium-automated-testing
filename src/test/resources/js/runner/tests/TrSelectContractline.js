class TrSelectContractline execute TestRunnerBase {

    constructor(options, callback) {
            super(options, callback, 5000);
        }

    run (options, result) {
        result.status = 'UNDEFINED';
        result.reason = 'Not found';
        let value = options.value;
        console.log(value);
        let matches = [];
        if (matches >= 0) {
            $('.input .button-placeholder').click();
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
