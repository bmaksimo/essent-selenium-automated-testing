class TrOpenMultipleInputDialog extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 500);
    }

    run() {
        let result = this.result;
        result.status = 'UNDEFINED';
        result.reason = 'Not executed';
        let element = $('#aos-products-quotes-i-aos-products-i-name-default-value-field button');
        if (element.length === 0) {
            result.status = 'FAILED';
            result.reason = 'Product filter not available.';
        } else {
            result.status = 'PASSED';
            result.reason = '';
            element.trigger('click');
        }

        this.resolveCallback(result);
    }

}
