class TrToggleInputState extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 100);
    }
    run() {
        let result = this.result;
        let $inputElements = $(this.options.id);
        let inputElementsSize = $inputElements.size();
        if (inputElementsSize === 0) {
            result.status = 'FAILED';
            result.reason = 'Input element with id ' + this.options.id + ' not found';
        } else {
            const self = this;
            $inputElements[0].element.click();
            result.status = 'PASSED';
            result.reason = '';
        }
        this.resolveCallback(result);
    }
}
