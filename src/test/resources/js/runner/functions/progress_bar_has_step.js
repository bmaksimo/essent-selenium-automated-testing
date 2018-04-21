TestRunner.prototype.progress_bar_has_step = function (id, options, callback) {

    let test = () => {
        let result = this.result;

        let progressBarSize = $('progress-bar ul li a').size();
        if (progressBarSize === 0) {
            result.status = 'FAILED';
            result.reason = 'Progressbar is not active';
        } else {
            result.status = 'FAILED';
            result.reason = 'step ' + options.step + ' with label ' + options.label + ' not found';
            $('progress-bar ul li a').each(function (i) {
                if (i + 1 === options.step && $(this).text().toUpperCase().match(options.label.toUpperCase())) {
                    result.status = 'PASSED';
                    result.reason = '';
                }
            })
        }
        if (callback) {
            callback(result);
        }
        console.log('test-result: ' + id, result);
    };

    setTimeout(test, 100);
};
