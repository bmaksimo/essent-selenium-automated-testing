class TrGetApplicationState extends TestRunnerDwp {

    constructor(options, callback) {
        super(options, callback, 100);
    }

    run(options, result) {

        result.status = 'PASSED';
        result.reason = '';
        result.data = this.getState();

        this.resolveCallback(result);
    }

}
