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
            result.reason = 'Filter not available.';
        } else {
            console.log("options.searchInput");
            console.log(options.searchInput);

            // setTimeout(function() {
                searchInput.val(options.searchInput).trigger('change');
                // searchInput.val("\""+options.searchInput+"\"");
                console.log("new val: " + searchInput.val());
            // }, 5000);

            setTimeout(function() {
                searchButton.trigger('click');
                }, 5000);
            let searchInputTerm = $('.multi-select__results input[type=checkbox]');
            if (searchInputTerm === 0 || searchInputTerm.length === 0) {
                result.status = 'FAILED';
                result.reason = 'Filter not available.';
            } else {
                setTimeout(function() { searchInputTerm.prop('checked', true); }, 5000);
                const submitModal = $('.modal__header > [class=button]');
                submitModal.trigger('click');
                result.status = 'PASSED';
                result.reason = '';
            }
        }
        this.resolveCallback(result);
    }

}
