TestRunner.prototype.get_form_state = function (id, options, callback) {
    var self = this;
    var response = {
        id: id,
        data: {}
    };
    setTimeout(function () {
        self.getState();
        response.state = self.state;
        response.data.formElements = self.getFormData($(options.selector).find('ng-form'));
        if (callback) {
            console.log(response);
            callback(response);
        }
    }, 500);
};
