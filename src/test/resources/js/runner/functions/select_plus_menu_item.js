TestRunner.prototype.select_plus_menu_item
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
        let element = $("sidebar div.plus-menu.ng-scope menu-button menu-link li a span:contains('"+options.menu+"')");

        if(element.length == 0) {
            response.result.status = 'FAILED';
            response.result.reason = 'Object ' + option.menuItem + ' was not found';
        } else {
            self.getState();
            $("sidebar div.plus-menu.ng-scope menu-button menu-link li a span:contains('"+options.menu+"')").parent().trigger('click');
            response.state = self.state;
        }
        if (callback) {
            callback(response);
        }
    }, 5000);
}
