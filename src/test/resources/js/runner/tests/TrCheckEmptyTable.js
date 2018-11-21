class TrCheckEmptyTable extends TestRunnerBase {

    constructor(options, callback) {
        let timeoutMillis = parseInt(options.schedule_seconds) * 1000;
        super(options, callback, timeoutMillis);
    }

    run(options, result) {
        result.status = 'FAILED';
        result.reason = 'Table ' + options.table + ' not found in the view';
        let rows = $("body").find("h2:contains('"+options.table+"')").parent().parent().find('table').find("#rows .list__row").length;
        if(rows > 0) {
            result.status = 'PASSED';
            result.reason = '';
        }

        this.resolveCallback(result);

    }
}
