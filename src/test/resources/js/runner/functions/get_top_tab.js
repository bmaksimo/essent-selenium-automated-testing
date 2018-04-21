TestRunner.prototype.get_top_tab = function (id, options, callback) {
    var self = this;
    var response = {
        id: id,
        data: false,
        result: {
            status: 'UNDEFINED',
            reason: 'Not executed'
        }
    };
    setTimeout(
        function () {
            switch(options.menu) {
                case 'Tasks':
                    if($('#sales-marketing-quotation-tasks-link').length == 1) {
                        $('#sales-marketing-quotation-tasks-link').trigger('click');
                        response.result.status = 'PASSED';
                    } else {
                        response.result.status = 'FAILED';
                        response.result.reason = 'Element ' + options.menu + ' not found';
                    }
                    break;
                case 'Market Transations':
                    if($('#market-transactions-dashboard-link').length == 1) {
                        $('#market-transactions-dashboard-link').trigger('click');
                        response.result.status = 'PASSED';
                    } else {
                        response.result.status = 'FAILED';
                        response.result.reason = 'Element ' + options.menu + ' not found';
                    }
                    break;
                case 'Leads':
                    if($('#lead-list-link').length == 1) {
                        $('#lead-list-link').trigger('click');
                        response.result.status = 'PASSED';
                    } else {
                        response.result.status = 'FAILED';
                        response.result.reason = 'Element ' + options.menu + ' not found';
                    }
                    break;
                case 'My Accounts':
                    if($('#my-accounts-list-link').length == 1) {
                        $('#my-accounts-list-link').trigger('click');
                        response.result.status = 'PASSED';
                    } else {
                        response.result.status = 'FAILED';
                        response.result.reason = 'Element ' + options.menu + ' not found';
                    }
                    break;
                case 'Accounts':
                    if($('#accounts-list-link').length == 1) {
                        $('#accounts-list-link').trigger('click');
                        response.result.status = 'PASSED';
                    } else {
                        response.result.status = 'FAILED';
                        response.result.reason = 'Element ' + options.menu + ' not found';
                    }
                    break;
                case 'Quotes':
                    if($('#quotes-list-link').length == 1) {
                        $('#quotes-list-link').trigger('click');
                        response.result.status = 'PASSED';
                    } else {
                        response.result.status = 'FAILED';
                        response.result.reason = 'Element ' + options.menu + ' not found';
                    }
                    break;
                case 'Contracts':
                    if($('#contract-list-link').length == 1) {
                        $('#contract-list-link').trigger('click');
                        response.result.status = 'PASSED';
                    } else {
                        response.result.status = 'FAILED';
                        response.result.reason = 'Element ' + options.menu + ' not found';
                    }
                    break;
                case 'Pricing Tool':
                    if($('#pricing-tool-dashboard-link').length == 1) {
                        $('#pricing-tool-dashboard-link').trigger('click');
                        response.result.status = 'PASSED';
                    } else {
                        response.result.status = 'FAILED';
                        response.result.reason = 'Element ' + options.menu + ' not found';
                    }
                    break;
                case 'Campaigns':
                    if($('#campaign-list-link').length == 1) {
                        $('#campaign-list-link').trigger('click');
                        response.result.status = 'PASSED';
                    } else {
                        response.result.status = 'FAILED';
                        response.result.reason = 'Element ' + options.menu + ' not found';
                    }
                    break;
                case 'Euroccor Quotes':
                    if($('#euroccor-quotes-link').length == 1) {
                        $('#euroccor-quotes-link').trigger('click');
                        response.result.status = 'PASSED';
                    } else {
                        response.result.status = 'FAILED';
                        response.result.reason = 'Element ' + options.menu + ' not found';
                    }
                    break;
            }
            if (callback) {
                callback(response.result);
            }
        }, 5000);
}
