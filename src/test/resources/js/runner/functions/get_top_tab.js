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
            let a = false;
            $('.top-menu sub-menu').children('sub-menu-link').each(function(index, element) {
                if(options.menu != $(this).attr('label')) {
                    return;
                } else {
                     e = $(this);
                     a = true;
                }
            });

            console.log(e);

            if(a == true) {
                e.find('a').trigger('click');
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
