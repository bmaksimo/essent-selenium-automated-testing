class TrCheckGeneratedConsumption extends TestRunnerBase {
    constructor(options, callback) {
        super(options, callback, 1000);
    }

    run(options, result) {
        result.status = 'FAILED';
        result.reason = 'Not executed';

       
        this.resolveCallback(result);
    }
}
