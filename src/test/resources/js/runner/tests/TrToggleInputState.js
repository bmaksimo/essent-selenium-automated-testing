class TrToggleInputState extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 100);
    }
    run(options, result) {
        let $inputElements = $(options.id);
        let inputElementsSize = $inputElements.size();
        if (inputElementsSize === 0) {
            result.status = 'FAILED';
            result.reason = 'Input element with id ' + options.id + ' not found';
        } else {
            $inputElements[0].element.click();
            result.status = 'PASSED';
            result.reason = '';
        }
        this.resolveCallback(result);
    }
}
