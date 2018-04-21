TestRunner.prototype.get_left_menu = function (id, options, callback) {
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
                case 'Sales marketing':
                    if($('#sales-marketing-link').length == 1) {
                        $('#sales-marketing-link').trigger('click');
                        response.result.status = 'PASSED';
                    } else {
                        response.result.status = 'FAILED';
                        response.result.reason = 'Element ' + options.menu + ' not found';
                    }
                    break;
                case 'Contracting switching':
                    if($('#contracting-switching-link').length == 1) {
                        $('#contracting-switching-link').trigger('click');
                        response.result.status = 'PASSED';
                    } else {
                        response.result.status = 'FAILED';
                        response.result.reason = 'Element ' + options.menu + ' not found';
                    }
                    break;
                case 'Billing':
                    if($('#billing-link').length == 1) {
                        $('#billing-link').trigger('click');
                        response.result.status = 'PASSED';
                    } else {
                        response.result.status = 'FAILED';
                        response.result.reason = 'Element ' + options.menu + ' not found';
                    }
                    break;
                case 'Credit Management':
                    if($('#credit-collection-link').length == 1) {
                        $('#credit-collection-link').trigger('click');
                        response.result.status = 'PASSED';
                    } else {
                        response.result.status = 'FAILED';
                        response.result.reason = 'Element ' + options.menu + ' not found';
                    }
                    break;
                case 'Finance':
                    if($('#finance-link').length == 1) {
                        $('#finance-link').trigger('click');
                        response.result.status = 'PASSED';
                    } else {
                        response.result.status = 'FAILED';
                        response.result.reason = 'Element ' + options.menu + ' not found';
                    }
                    break;
                case 'Service':
                    if($('#service-complaint-link').length == 1) {
                        $('#service-complaint-link').trigger('click');
                        response.result.status = 'PASSED';
                    } else {
                        response.result.status = 'FAILED';
                        response.result.reason = 'Element ' + options.menu + ' not found';
                    }
                    break;
                case 'Admin':
                    if($('#admin-link').length == 1) {
                        $('#admin-link').trigger('click');
                        response.result.status = 'PASSED';
                    } else {
                        response.result.status = 'FAILED';
                        response.result.reason = 'Element ' + options.menu + ' not found';
                    }
                    break;
                case 'Test':
                    if($('#test-link').length == 1) {
                        $('#test-link').trigger('click');
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
