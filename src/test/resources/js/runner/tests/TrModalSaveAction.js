class TrModalSaveAction extends TestRunnerBase {
    constructor(options, callback) {
        super(options, callback, 1000);
    }

    run(options, result) {
        result.status = 'FAILED';
        result.reason = 'Not executed';

        const modalSaveButton = $('#confirm-button');

        if (modalSaveButton.index() > -1) {
            modalSaveButton.trigger('click');
            result.status = 'PASSED';
            result.reason = '';
        } else {
            result.reason = 'Save button is not present.' ;
        }

        this.resolveCallback(result);
    }
}
