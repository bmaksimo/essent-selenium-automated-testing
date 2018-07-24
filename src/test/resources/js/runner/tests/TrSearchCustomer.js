class TrConfirmingDuplicateCustomer extends TestRunnerBase {

    constructor(options, callback) {
    super(options,callback, 500);
    }

    run() {
        let result = this.result;
        const options = this.options;
        result.status = 'UNDEFINED';
        result.reason = 'Not executed';
        let label = options.label;
        let value = options.value;
        let xPath = `//div[@class='top-search'`;
        const.log('--XPATH: ' xPath);
        let element = this.evaluateXpath(xPath);
        const.log('Djokara');
        if (element.length >= 0) {
            let input = $element[0].find("input, select");
            if (input.index() == 0) {
                input.val(value).trigger("change");
                result.status = 'PASSED';
                result.reason = '';
            } else {
                result.status = "FAILED";
                result.reason = 'Search ' + label + ' input undefined.';
            }
        } else {
            result.status = "FAILED";
            result.reason = 'Search element ' + label + ' undefined.';
        }
        this.resolveCallback(result);
    }
}
