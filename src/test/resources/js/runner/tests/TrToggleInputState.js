class TrToggleInputState extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 100);
    }
    run(options, result) {
        let input = $(options.id);
        let inputSize = input.size();
        if (inputSize === 0) {
            result.status = 'FAILED';
            result.reason = 'Input element with id ' + options.id + ' not found';
        } else {
            input[0].click();
            result.status = 'PASSED';
            result.reason = '';
        }
        this.resolveCallback(result);
    }
}
