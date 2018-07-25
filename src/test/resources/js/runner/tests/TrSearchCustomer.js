class TrSearchCustomer extends TestRunnerBase {

    constructor(options, callback) {
    super(options,callback, 2500);
    }

    run() {
        let result = this.result;
        const options = this.options;
        result.status = 'UNDEFINED';
        result.reason = 'Not executed';
        let xPath = `//div[@class='top-search' | @class='input[@type='search']']`;
        console.log('--XPATH: ' + xPath);
        let elements = this.evaluateXpath(xPath);
        if(elements.length >= 0) {
            let input = $(elements[0]).find("input, select");
//            $('.top-form .top-search').click();
//            console.log($('.top-form .top-search'));
            input.val(options.name).trigger("change");
            result.status = 'PASSED';
            result.reason = '';
        } else {
            result.status = "FAILED";
            result.reason = 'Search element ' + label + ' undefined.';
        }
        setTimeout(()=> {
            this.resolveCallback(result);
        }, 1000);
    }
}
