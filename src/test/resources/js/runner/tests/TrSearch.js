class TrSearch extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 5000);
    }

    run (options, result) {
        result.status = 'UNDEFINED';
        result.reason = 'Not executed';
        let value = options.value;
        console.log(value);
        let search = $('.multi-select__search .ng-valid');
        let button = $('.multi-select__search .button');
        console.log(search)
        if(matches >= 0) {
            search.val(value).trigger("change");
            button.click();
            result.status = "PASSED";
            result.reason = '';
        } else {
            result.status = "FAILED";
            result.reason = 'TextArea element ' + label + ' undefined.';
        }
        setTimeout(()=> {
            this.resolveCallback(result);
        }, 1000);
    }
}
