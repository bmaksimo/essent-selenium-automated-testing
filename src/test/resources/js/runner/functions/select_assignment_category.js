TestRunner.prototype.select_assignment_category = function (id, options, callback) {
    var self = this;
    var response = {
        id: id,
        data: false,
        result: {
            status: 'UNDEFINED',
            reason: 'Not executed',
            column: {
                caption: '',
                index: -1
            }
        }
    };
    setTimeout(
        function () {
            if(options.category != "Random" && $("#dwp-assignment-type-group-field option[value='string:"+options.category+"']").length == 0) {
                response.result.status = 'FAILED';
                response.result.reason = 'Element ' + options.menu + ' not found';
            }

            if(options.category == 'Random') {
                let categories = new Array();
                $("#dwp-assignment-type-group-field > option").each(function() {
                    categories.push(this.label);
                });

                categories.shift();

                let number = Math.floor(Math.random() * categories.length);

                $("#dwp-assignment-type-group-field").val("string:" + categories[number]);
                $("#dwp-assignment-type-group-field").trigger('change');
            } else {
                $("#dwp-assignment-type-group-field").val("string:" + options.category);
                $("#dwp-assignment-type-group-field").trigger('change');
            }
            if (callback) {
                callback(response.result);
            }
        }, 5000);
}
