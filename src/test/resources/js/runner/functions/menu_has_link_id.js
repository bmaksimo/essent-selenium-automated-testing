TestRunner.prototype.menu_has_link_id = function (id, options, callback) {
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
        self.getState();
        response.state = self.state;
        response.result.status = 'FAILED';
        response.result.reason = options.linkId + ' not found';
        self.state[options.menu].forEach(function (menuItem) {
            if (menuItem['link-id'] === options.linkId) {
                response.data = {menuItem: menuItem};
                response.result.status = 'PASSED';
                response.result.reason = '';
            }
        });
        if (callback) {
            callback(response.result);
        }
    }, 100);
};
