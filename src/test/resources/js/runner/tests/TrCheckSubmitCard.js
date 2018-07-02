class TrCheckSubmitCard extends TestRunnerBase {

    constructor(options, callback) {
        let timeoutMillis = parseInt(options.schedule_seconds) * 1000;
        super(options, callback, timeoutMillis);
    }

    run(options, result) {
        result.status = 'FAILED';
        result.reason = 'After ' + options.schedule_seconds + ' seconds, the submit card did not contain title: ' + options.item;
        const length = $(".card__content .form__header h2").length;
        if (length > 0) {
            if($(".card__content .form__header h2").text().toUpperCase() == options.item) {
                result.status = 'PASSED';
                result.reason = '';
            }
        }
        this.resolveCallback(result);
    }
}
