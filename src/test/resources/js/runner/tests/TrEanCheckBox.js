class TrEanCheckBox extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 5000);
    }

    run (options, result) {
        result.status = 'UNDEFINED';
        result.reason = 'Not executed';
        let checkBox = $('.input .input__checkbox')[1];
        let submit = $('.modal__header .button')[1];
        let select = $('#confirm-button')
        if(checkBox != 0) {
            checkBox.click();
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
}
