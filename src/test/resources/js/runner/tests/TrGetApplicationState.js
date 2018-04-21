class TrGetApplicationState extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 100);
    }

    run() {
        this.getState();

        this.result.status = 'PASSED';
        this.result.reason = '';
        this.result.data = this.state;

        this.resolveCallback(this.result);
    }

}
