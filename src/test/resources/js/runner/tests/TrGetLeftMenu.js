class TrGetLeftMenu extends TestRunnerBase {

    /**
     * Checks if a menu-item is present in the DOM
     *
     * * @param {object} options - Arguments passed from Java.
     * * @param {function} callback - The Java callback that handles the result.
     *
     * Java example:
     * assertThat(executeJsTest("new TrGetColumnIndexList({menu: 'mainMenu', linkId: 'sales-marketing-link', arguments[arguments.length - 1]);", link), is(true));
     */

    constructor(options, callback) {
        super(options, callback, 100);
    }

    run() {
        let result = this.result;
        const options = this.options;
        result.status = 'UNDEFINED';
        result.reason = 'Not executed';
        setTimeout(()=> {
            switch(options.menu) {
                case 'Sales marketing':
                    if($('#sales-marketing-link').length == 1) {
                        $('#sales-marketing-link').trigger('click');
                        result.status = 'PASSED';
                    } else {
                        result.status = 'FAILED';
                        result.reason = 'Element ' + options.menu + ' not found';
                    }
                    break;
                case 'Contracting switching':
                    if($('#contracting-switching-link').length == 1) {
                        $('#contracting-switching-link').trigger('click');
                        result.status = 'PASSED';
                    } else {
                        result.status = 'FAILED';
                        result.reason = 'Element ' + options.menu + ' not found';
                    }
                    break;
                case 'Billing':
                    if($('#billing-link').length == 1) {
                        $('#billing-link').trigger('click');
                        result.status = 'PASSED';
                    } else {
                        result.status = 'FAILED';
                        result.reason = 'Element ' + options.menu + ' not found';
                    }
                    break;
                case 'Credit Management':
                    if($('#credit-collection-link').length == 1) {
                        $('#credit-collection-link').trigger('click');
                        result.status = 'PASSED';
                    } else {
                        result.status = 'FAILED';
                        result.reason = 'Element ' + options.menu + ' not found';
                    }
                    break;
                case 'Finance':
                    if($('#finance-link').length == 1) {
                        $('#finance-link').trigger('click');
                        result.status = 'PASSED';
                    } else {
                        result.status = 'FAILED';
                        result.reason = 'Element ' + options.menu + ' not found';
                    }
                    break;
                case 'Service':
                    if($('#service-complaint-link').length == 1) {
                        $('#service-complaint-link').trigger('click');
                        result.status = 'PASSED';
                    } else {
                        result.status = 'FAILED';
                        result.reason = 'Element ' + options.menu + ' not found';
                    }
                    break;
                case 'Admin':
                    if($('#admin-link').length == 1) {
                        $('#admin-link').trigger('click');
                        result.status = 'PASSED';
                    } else {
                        result.status = 'FAILED';
                        result.reason = 'Element ' + options.menu + ' not found';
                    }
                    break;
                case 'Test':
                    if($('#test-link').length == 1) {
                        $('#test-link').trigger('click');
                        result.status = 'PASSED';
                    } else {
                        result.status = 'FAILED';
                        result.reason = 'Element ' + options.menu + ' not found';
                    }
                    break;
            }
            this.resolveCallback(result);
        }, 5000);
    }

}
