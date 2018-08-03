class TrSelectEanCode extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 1000);
    }

    run(options, result) {
        result.status = 'PASSED';
        result.reason = '';
        let ean_anchor = $('#ean_c autocomplete li:first a');

        if(ean_anchor.length > 0) {
            ean_anchor.click();
        } else {
            result.status = 'FAILED';
            result.reason = `No ean found`;
        }
        this.resolveCallback(result);
    }
}
