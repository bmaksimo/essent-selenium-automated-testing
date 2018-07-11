class TrGetCockpitItem extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 100);
    }

    run() {
        let result = this.result;
        const options = this.options;
        result.status = 'UNDEFINED';
        result.reason = 'Not executed';
        let matches = [];
        $("dashboard-item[label='"+options.item+"']");
        if(matches.length > 0) {
            matches[0].find('a').trigger('click');
            result.status = 'PASSED';
            result.reason = '';
        } else {
            result.status = 'FAILED';
            result.reason = 'Element ' + options.label + ' not found';
        }
        setTimeout(()=> {
            this.resolveCallback(result);
        }, 5000);
    }
}
