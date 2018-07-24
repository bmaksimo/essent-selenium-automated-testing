class TrConfirmingDuplicateCustomer extends TestRunnerBase {

    constructor(options, callback) {
    super(options,callback, 500);
    }

    run() {
        let result = this.result;
        const options = this.options;
        result.status = 'UNDEFINED';
        result.reason = 'Not executed';
        let matches = $(".form__footer button:contains('" + options.value + "')");
        let matches = this.evaluateXpath(xPath);
        if (matches.length === 0) {
            matches[0].click();
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
