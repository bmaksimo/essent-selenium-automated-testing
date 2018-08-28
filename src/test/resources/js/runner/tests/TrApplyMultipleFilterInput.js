class TrApplyMultipleFilterInput extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 500);
    }

    run() {
        let result = this.result;
        result.status = 'UNDEFINED';
        result.reason = 'Not executed';
        const options = this.options;
        let searchInput = $('#search-input');
        const searchButton = $('input[type="submit"]');
        if (searchInput.length === 0 || searchButton.length === 0) {
            result.status = 'FAILED';
            result.reason = 'Product filter not available.';
        } else {
            searchInput.val(options.product);
            searchButton.click();
            let product = $('.multi-select__results input[type=checkbox]');
            if (product === 0 || product.length === 0) {
                result.status = 'FAILED';
                result.reason = 'Product filter not available.';
            } else {
                product.prop('checked', true);
                const submitModal = $('.modal__header > .button');
                submitModal.click();
                result.status = 'PASSED';
                result.reason = '';
            }
        }
        this.resolveCallback(result);
    }

}
