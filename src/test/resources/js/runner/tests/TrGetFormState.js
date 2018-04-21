class TrGetFormState extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 100);
    }

    run() {
        let $form = $(this.options.selector);

        if($form.size()) {
            this.result.status = 'PASSED';
            this.result.reason = '';
            this.result.data = {
                formElements: this.getFormData($form)
            }
        } else {
            this.result.reason = 'The form with selector ' + this.options.selector + ' is not available in the DOM';
        }

        this.resolveCallback(this.result);
    }

}
