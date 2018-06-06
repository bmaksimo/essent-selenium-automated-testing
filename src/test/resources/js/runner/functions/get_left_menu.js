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
            let a = false;
            $('main-menu').children('main-menu-link').each(function(index, element) {
                if(options.menu != $(this).attr('name')) return;
                e = $('#' + $(this).find('a').attr('id'));
                if(e.length > 0) a = true;
            });

            if(a == true) {
                e.trigger('click');
                response.result.status = 'PASSED';
            } else {
                response.result.status = 'FAILED';
                response.result.reason = 'Element ' + options.menu + ' not found';
            }

            if (callback) {
                callback(response.result);
            }
        }, 5000);
}
