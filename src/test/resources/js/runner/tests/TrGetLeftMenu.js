/**
 * Checks if a menu-item is present in the DOM
 * Then clicks on left menu item
 *
 * * @param {object.menu} options - kebab case menu label argument passed from Java.
 * * @param {function} callback - The Java callback that handles the result.
 *
 * Java example:
 * Map<String, String> options = new HashMap<>();
 * options.put("menu",kebabCaseLabel);
 * boolean result = executeJavascriptTest("TrGetLeftMenu", options);
 */
class TrGetLeftMenu extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 100);
    }

    run(options, result) {
        result.status = 'UNDEFINED';
        result.reason = 'Not executed';

        let targetNumber = 1;
        let matches = [];
        $('main-menu').children('main-menu-link').each(function(index, element) {
            if(options.menu != $(this).attr('name')) return;
            matches.push($('#' + $(this).find('a').attr('id')));
            if (matches.length == targetNumber)
                return false;
        });
        if(matches.length > 0) {
            matches[0].trigger('click');
            result.status = 'PASSED';
            result.reason = '';
        } else {
            result.status = 'FAILED';
            result.reason = 'Element ' + options.menu + ' not found';
        }
        setTimeout(()=> {
            this.resolveCallback(result);
        }, 5000);
    }
}
