TestRunner.prototype.plus_menu_has_item = function (id, options, callback) {
    var self = this;
    var response = {
        id: id,
        data: false,
        result: {
            status: 'UNDEFINED',
            reason: 'Not executed'
        }
    };
    if (!$("div[class='top-actions'] > .icon-plus.is-active").size()) {
        $("div[class='top-actions'] > .icon-plus").trigger('click');
    }
    setTimeout(function () {
        self.getState();
        response.state = self.state;
        response.result.status = 'FAILED';
        response.result.reason = options.item + ' plus menu item was not found at position ' + options.position;
        var itemsCount = 1;
        $('a.icon-arrow-down').each(function (a, b) {
            if (b.innerText.trim().length > 0) {
                if (b.innerText.trim() === options.item &&
                    itemsCount === options.position) {
                    response.result.status = 'PASSED';
                    response.result.reason = '';
                    console.log(itemsCount + ": " + b.innerText);
                    return false;
                }
                itemsCount++;
            }
        });
        $("div[class='top-actions'] > .icon-plus").trigger('click');
        if (callback) {
            console.log(response.result);
            callback(response.result);
        }
    }, 500);
};
