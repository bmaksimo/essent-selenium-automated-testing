class TrConfirmingDuplicateCustomer extends TestRunnerBase {

    constructor(options, callback) {
    super(options,callback, 1000);
    }

    run() {
        let result = this.result;
        const options = this.options;
        result.status = 'UNDEFINED';
        result.reason = 'Not executed';
        let matches = [];
        if (matches >= 0) {
            $('#confirm-button').click();
            result.status = 'PASSED';
            result.reason = '';
        } else {
            result.status = 'FAILED';
            result.reason = 'Element ' + options.name + ' not found';
        }
        setTimeout(()=> {
            this.resolveCallback(result);
        }, 1000);
    }
}
