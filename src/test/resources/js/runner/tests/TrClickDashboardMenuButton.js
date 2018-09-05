/**
 * Checks if Overview Menu Item is present in the DOM
 * Then clicks on it, if it is available
 *
 * * @param {object.name}  - menu item argument passed from Java.
 * * @param {function} callback - The Java callback that handles the result.
 *
 * Java example:
 * Map<String, String> options = new HashMap<>();
 * options.put("menu", "Sales");
 * boolean result = executeJavascriptTest("TrClickDashboardMenuButton.js", options);
 */
class TrClickDashboardMenuButton extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 500);
    }

    run() {
        let result = this.result;
        const options = this.options;
        result.status = 'UNDEFINED';
        result.reason = 'Not executed';
        let element = $('.blue-sidebar .icon-nav a small').filter(function() {return $(this).text() ==  options.menu;}).parent();
        if(element.length == 0) {
            result.status = 'FAILED';
            result.reason = 'Overview Menu ' + options.menu + ' not available.';
        } else {
            result.status = 'PASSED';
            result.reason = '';
            element.trigger('click');
        }

        this.resolveCallback(result);

    }
}
