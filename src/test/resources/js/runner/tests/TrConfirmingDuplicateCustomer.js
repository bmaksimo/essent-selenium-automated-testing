class TrConfirmingDuplicateCustomer extends TestRunnerBase {

    constructor(options, callback) {
    super(options,callback, 5000);
    }

    run() {
        let result = this.result;
        const options = this.options;
        result.status = 'UNDEFINED';
        result.reason = 'Not executed';
        let matches = [];
        $(".button-group modal__actions .button");
        if (matches.length > 1) {
            matches[1].find('a').trigger('click');
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
