TestRunner.prototype.get_top_action = function (id, options, callback) {
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
            $('.top-actions').children('a').each(function(index, element) {
                console.log($(this));
                if(options.menu != $(this).attr('name')) {
                    return;
                } else {
                    e = $(this);
                    a = true;
                }
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
