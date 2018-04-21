TestRunner.prototype.get_top_menu = function (id, options, callback) {
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
                case 'Plus Menu':
                    if($('top-actions a.icon-plus').length == 1) {
                        $('top-actions a.icon-plus').trigger('click');
                        response.result.status = 'PASSED';
                    } else {
                        response.result.status = 'FAILED';
                        response.result.reason = 'Element ' + options.menu + ' not found';
                    }
                    break;
                case 'Filters':
                    if($('top-actions a.icon-filters').length == 1) {
                        $('top-actions a.icon-filters').trigger('click');
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
