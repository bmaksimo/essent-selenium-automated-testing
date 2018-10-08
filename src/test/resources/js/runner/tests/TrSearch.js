class TrSearch extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 5000);
    }

    run (options, result) {
        result.status = 'UNDEFINED';
        result.reason = 'Not executed';
        let value = options.value;
        console.log(value);
        let search = $('.ng-pristine');
        let button = $('.button-dark');
        console.log(search)
        if(value != 'undefined') {
//            $('.ng-pristine').val(valid).trigger("change")
            search.val(value).trigger("change");
            button.click();
            result.status = "PASSED";
            result.reason = '';
        } else {
            result.status = "FAILED";
            result.reason = 'TextArea element ' + label + ' undefined.';
        }

        this.resolveCallback(result);

    }
}
