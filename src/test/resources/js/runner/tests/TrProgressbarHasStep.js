class TrProgressbarHasStep extends TestRunnerDwp {

    constructor(options, callback) {
        super(options, callback, 100);
    }

    run(options, result) {
        let $progressbarLinks = $('progress-bar ul li a');
        let progressbarSize = $progressbarLinks.size();
        if (progressbarSize === 0) {
            result.status = 'FAILED';
            result.reason = 'Progressbar is not active';
        } else {
            result.status = 'FAILED';
            result.reason = 'step ' + options.step + ' with label ' + options.label + ' not found';
            $progressbarLinks.each(function(i) {
                if ( i+1 === options.step && $(this).text().toUpperCase().match(options.label.toUpperCase())) {
                    result.status = 'PASSED';
                    result.reason = '';
                }
            })
        }

        this.resolveCallback(result);
    }

}
