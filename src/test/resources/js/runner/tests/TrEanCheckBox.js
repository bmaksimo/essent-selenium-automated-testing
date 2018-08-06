class TrEanCheckBox extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 5000);
    }

    run (options, result) {
        result.status = 'UNDEFINED';
        result.reason = 'Not executed';
        let checkBox = $('.input__checkbox');
        let submit = $('.modal__header .button')[1];
        if(checkBox != 0) {
            checkBox.click();
//            submit.click();
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
