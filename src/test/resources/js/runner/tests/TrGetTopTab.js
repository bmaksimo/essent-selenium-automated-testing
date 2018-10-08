/**
 * Checks if a top tab (top menu item) is present in the DOM
 * Then clicks on it, if it is available
 *
 * * @param {object.name}  - menu item argument passed from Java.
 * * @param {function} callback - The Java callback that handles the result.
 *
 * Java example:
 * Map<String, String> options = new HashMap<>();
 * options.put("label", "Contracts");
 * boolean result = executeJavascriptTest("TrGetTopTab", options);
 */
class TrGetTopTab extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 2000);
    }

    run() {
        let result = this.result;
        const options = this.options;
        result.status = 'UNDEFINED';
        result.reason = 'Not executed';
        let matches = [];
        $('.top-menu sub-menu').children('sub-menu-link').each(function(index, element) {
            if(options.label != $(this).attr('label')) {
                return;
            } else {
                matches.push($(this));
                return false;
            }
        });
        if(matches.length > 0) {
            matches[0].find('a').trigger('click');
            result.status = 'PASSED';
            result.reason = '';
        } else {
            result.status = 'FAILED';
            result.reason = 'Element ' + options.label + ' not found';
        }

        this.resolveCallback(result);
    }
}
