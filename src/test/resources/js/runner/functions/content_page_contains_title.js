TestRunner.prototype.content_page_contains_title = function (id, options, callback) {
    var self = this;
    var response = {
        id: id,
        data: false,
        result: {
            status: 'UNDEFINED',
            reason: 'Not executed'
        }
    };
    var test = function () {
        self.getState();
        response.state = self.state;
        response.result.status = 'FAILED';
        response.result.reason = 'After ' + options.seconds + ' seconds content page did not contain title: ' + options.title;
        var length = $(".list__header").children().filter(function (i, node) {
            console.log(i + ': ' + node.innerText);
            return node.innerText.trim() === options.title;
        }).length;
        if (length > 0) {
            response.result.status = 'PASSED';
            response.result.reason = '';
        }
        if (callback) {
            console.log(response.result);
            callback(response.result);
        }
    }
    setTimeout(test, options.seconds * 1000);
};
