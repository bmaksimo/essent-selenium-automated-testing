class TrCheckFirstRowByOption extends TestRunnerBase {

    constructor(options, callback) {
        let timeoutMillis = parseInt(options.schedule_seconds) * 5000;
        super(options, callback, timeoutMillis);
    }

    run(options, result) {
        result.status = 'FAILED';
        result.reason = 'The text ' + options.text + 'cannot be found in the column ' + options.column + ' for the row selected by ' + options.option;

        let list = $("h2:contains('" + options.list + "')").closest("list").get(0);
        $(list).find("th").each(function(index) {
            if($(this).text() === options.column) {
                let row = $(list).find("tr:has(td:contains('"+options.option+"'))").get(0);

                // The following line strips out the string ' €' from the content of the td.
                // TODO Find a way to render the € sign in the .feature file, so this can be removed. At the moment, passing the € sign from the feature file, it returns a question mark (?).
                if($(row).children('td').eq(index).get(0).textContent.trim().replace(' \u20AC', '') === options.text) {
                    result.status = 'PASSED';
                    result.reason = '';
                } else {
                    result.reason = 'The string in the column '+options.column+' does not match the value ' + options.text;
                }
            } else {
                result.reason = 'The column ' + options.column + ' does not exist';
            }
        });

        this.resolveCallback(result);

    }
}
