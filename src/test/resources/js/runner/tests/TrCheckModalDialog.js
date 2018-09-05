class TrCheckModalDialog extends TestRunnerBase {
    constructor(options, callback) {
        super(options, callback, 1000);
    }

    run(options, result) {
        result.status = 'FAILED';
        result.reason = 'Not executed';

        const headerText = options.headerText;
        const modalDialogHeader = $("h5:contains('" + headerText + "')")

        if (modalDialogHeader.index() > -1) {
            result.status = 'PASSED';
            result.reason = '';
        } else {
            result.reason = 'Modal dialog is not open.' ;
        }

        this.resolveCallback(result);
    }
}
