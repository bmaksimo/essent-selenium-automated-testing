TestRunner.prototype.select_assignment_type = function (id, options, callback) {
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
            if(options.type != "Random" && $("#assignment-id-field option[value='string:"+options.type+"']").length == 0) {
                response.result.status = 'FAILED';
                response.result.reason = 'Element ' + options.type + ' not found';
            }

            if(options.type == 'Random') {
                let types = new Array();
                $("#assignment-id-field > option").each(function() {
                    types.push(this.label);
                });

                types.shift();

                let number = Math.floor(Math.random() * types.length);

                $("#assignment-id-field").val("string:" + types[1]);
                $("#assignment-id-field").trigger('change');
            } else {
                $("#assignment-id-field").val("string:" + options.type);
                $("#assignment-id-field").trigger('change');
            }
            if (callback) {
                callback(response.result);
            }
        }, 10000);
}
