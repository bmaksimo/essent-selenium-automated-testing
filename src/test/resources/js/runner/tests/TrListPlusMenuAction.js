class TrListPlusMenuAction extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 1000);
    }

    run() {
        let result = this.result;
        const options = this.options;
        result.status = 'UNDEFINED';
        result.reason = 'Not executed';
        let matches = [];

        $('list-row-actions[grid-key="action-bar"] div').children('list-row-action').each(function(index, element) {
            if(options.item != $(this).attr('label')) {
                return;
            } else {
                matches.push($(this));
                return false;
            }
        });

        if(matches.length > 0) {
            let link = matches[0].find('a');
            let disabled = link.attr("disabled");
            result.reason = 'Element ' + options.item + ' disabled';
            if(!disabled) {
                 link.trigger('click');
                 result.status = 'PASSED';
                 result.reason = '';
            }

        } else {
            result.status = 'FAILED';
            result.reason = 'Element ' + options.item + ' not found';
        }

        this.resolveCallback(result);
    }
}
