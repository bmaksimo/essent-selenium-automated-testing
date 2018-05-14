class TrProgressbarHasStep extends TestRunnerDwp {

    constructor(options, callback) {
        super(options, callback, 100);
    }

    run() {
        let result = this.result;

        let $progressbarLinks = $('progress-bar ul li a');
        let progressbarSize = $progressbarLinks.size();
        if (progressbarSize === 0) {
            result.status = 'FAILED';
            result.reason = 'Progressbar is not active';
        } else {
            const self = this;
            result.status = 'FAILED';
            result.reason = 'step ' + this.options.step + ' with label ' + this.options.label + ' not found';
            $progressbarLinks.each(function(i) {
                if ( i+1 === self.options.step && $(this).text().toUpperCase().match(self.options.label.toUpperCase())) {
                    result.status = 'PASSED';
                    result.reason = '';
                }
            })
        }

        this.resolveCallback(result);
    }

}
