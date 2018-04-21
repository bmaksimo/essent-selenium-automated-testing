TestRunner.prototype.get_overview_menu_index
    = function (id, options, callback) {
    var self = this;
    var response = {
        id: id,
        data: false,
        result: {
            status: 'UNDEFINED',
            reason: 'Not executed'
        }
    };
    setTimeout(function () {
        let element = $('.blue-sidebar .icon-nav a small:contains(' + options.menu + ')').parent();
        console.log(element.length);
        if(element.length == 0) {
            response.result.status = 'FAILED';
            response.result.reason = 'Object ' + option.menu + ' was not found';
        } else {
            $('.blue-sidebar .icon-nav a small:contains(' + options.menu + ')').parent().trigger('click');
        }
        if (callback) {
            callback(response);
        }
        console.log(options);
    }, 5000);
}
