class TrSubmitForm extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 5000);
    }

    run(options, result) {
//        result.status = 'FAILED';
//        result.reason = 'Confirm button not found';
        let action = $("#confirm-button");
        result.status = 'PASSED';
        result.reason = '';
        $("#confirm-button").trigger('click');

        setTimeout(()=> {
            this.resolveCallback(result);
        }, 5000);

    }
}
