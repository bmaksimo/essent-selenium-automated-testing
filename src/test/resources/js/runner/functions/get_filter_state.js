TestRunner.prototype.get_filter_state = function (id, options, callback) {
    var self = this;
    var response = {
        id: id,
        data: {}
    };
    if (!$('.icon-filters.is-active').size()) {
        $('.icon-filters').trigger('click');
    }
    setTimeout(function () {
        self.getState();
        response.state = self.state;
        response.data.formElements = self.getFormData($('div[ui-view="filters"]').find('ng-form'));
        if (callback) {
            console.log(response);
            callback(response);
        }
    }, 500);
};
