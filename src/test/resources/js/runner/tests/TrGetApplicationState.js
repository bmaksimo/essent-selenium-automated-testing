class TrGetApplicationState extends TestRunnerDwp {

    constructor(options, callback) {
        super(options, callback, 100);
    }

    run() {

        this.result.status = 'PASSED';
        this.result.reason = '';
        this.result.data = this.getState();

        this.resolveCallback(this.result);
    }

}
