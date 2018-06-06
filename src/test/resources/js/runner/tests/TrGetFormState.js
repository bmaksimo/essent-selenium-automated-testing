class TrGetFormState extends TestRunnerDwp {

    constructor(options, callback) {
        super(options, callback, 100);
    }

    run(options, result) {
        let $form = $(options.selector);

        if($form.size()) {
            result.status = 'PASSED';
            result.reason = '';
            result.data = this.getFormData($form, true);
        } else {
            result.reason = 'The form with selector ' + options.selector + ' is not available in the DOM';
        }

        this.resolveCallback(result);
    }

}
