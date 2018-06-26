class TrGetTopAction extends TestRunnerBase {

    /**
     * Checks if a top action (such as Plus, filter) is available in the DOM
     * Then clicks on a top action label
     *
     * * @param {object.name} options - Action name argument passed from Java.
     * * @param {function} callback - The Java callback that handles the result.
     *
     * Java example:
     * Map<String, String> options = new HashMap<>();
     * options.put("Plus Menu", name);
     * boolean result = executeJavascriptTest("TTrGetTopAction", options);
     */

    constructor(options, callback) {
        super(options, callback, 100);
    }

    run() {
        let result = this.result;
        const options = this.options;
        result.status = 'UNDEFINED';
        result.reason = 'Not executed';

        let matches = [];
        $('.top-actions').children('a').each(function(index, element) {
            if(options.name != $(this).attr('name')) {
                return;
            } else {
                matches.push($(this));
                return false;
            }
        });
        if(matches.length > 0) {
            matches[0].trigger('click');
            result.status = 'PASSED';
            result.reason = '';
        } else {
            result.status = 'FAILED';
            result.reason = 'Top action  ' + options.name + ' not found';
        }

        setTimeout(()=> {
            this.resolveCallback(result);
        }, 1000);
    }

}
