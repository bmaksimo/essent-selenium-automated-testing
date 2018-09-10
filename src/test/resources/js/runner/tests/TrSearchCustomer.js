class TrSearchCustomer extends TestRunnerBase {

    constructor(options, callback) {
    super(options,callback, 500);
    }

    run() {
        let result = this.result;
        const options = this.options;
        result.status = 'UNDEFINED';
        result.reason = 'Not executed';
        let search = $('.top-search .ng-pristine');
        let name = options.name;
        console.log(name);
        if(name != "") {
            search.val(name).trigger("change");
            result.status = 'PASSED';
            result.reason = '';
        } else {
            result.status = "FAILED";
            result.reason = 'Search element ' + label + ' undefined.';
        }
        setTimeout(()=> {
            this.resolveCallback(result);
        }, 500);
    }
}
