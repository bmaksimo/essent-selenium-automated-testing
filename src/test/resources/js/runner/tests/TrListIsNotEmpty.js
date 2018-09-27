class TrListIsNotEmpty extends TestRunnerBase {
    constructor(options, callback) {
        super(options, callback, 1000);
    }

    run(options, result) {
        result.status = 'UNDEFINED';
        result.reason = '';
        let rows = $('.rows');

        if (rows.length == 0) {
            result.status = 'PASSED';
            result.reason = '';
        } else {
            result.status = 'FAILED';
            result.reason = 'List is empty';
        }
        this.resolveCallback(result);
    }
}
