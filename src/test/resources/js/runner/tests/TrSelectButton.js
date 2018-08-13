class TrSelectButton extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 5000);
    }

    run (options, result) {
        result.status = 'UNDEFINED';
        result.reason = 'Not executed';
        let select = $('#confirm-button');
        if(select != 0) {
            select.click();
            result.status = "PASSED";
            result.reason = '';
        } else {
            result.status = "FAILED";
            result.reason = 'TextArea element ' + label + ' undefined.';
        }
        setTimeout(()=> {
            this.resolveCallback(result);
        }, 1000);
    }
}
