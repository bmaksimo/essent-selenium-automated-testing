class TrSubmitButton extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 5000);
    }

    run (options, result) {
        result.status = 'UNDEFINED';
        result.reason = 'Not executed';
        let submit = $('.modal__header .button')[1];
        if(submit != 0) {
            submit.click();
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
