TestRunner.prototype.get_column_index_list = function (id, options, callback) {
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
            let index = $(".list__content th:contains('" + options.column + "')").index();
            if (index < 0) {
                response.result.status = 'FAILED';
                response.result.reason = 'Column ' + options.column + ' was not found.';
            } else if ($("#rows tr:nth-child(1)") < 0) {
                response.result.status = 'FAILED';
                response.result.reason = 'Row was not found.';
            } else {
                response.result.status = 'PASSED';
                response.result.column.index = ++index;
                response.result.column.caption = options.column;
                $("#rows tr:nth-child(1) td:nth-child(" + index + ") div a").trigger('click');
            }
            if (callback) {
                callback(response.result);
            }
        }, 2000);
}
