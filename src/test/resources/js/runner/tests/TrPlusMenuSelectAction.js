class TrPlusMenuSelectAction extends TestRunnerBase {

    /**
     * Checks if a top action (such as Plus, filter) is available in the DOM
     * Then clicks on a top action label
     *
     * * @param {object.name} options - Action name argument passed from Java.
     * * @param {function} callback - The Java callback that handles the result.
     *
     * Java example:
     * Map<String, String> options = new HashMap<>();
     * options.put(path, "Contracting -> UP/TC2 - TO RENEW CONTRACTS");
     * boolean result = executeJavascriptTest("TTrGetTopAction", options);
     */

    constructor(options, callback) {
        super(options, callback, 100);
    }

    run() {
        const PATH_PATTERN = new RegExp('\\s*->\\s*');
        let result = this.result;
        const options = this.options;
        result.status = 'UNDEFINED';
        result.reason = 'Not executed';

        let items = options.path.split(PATH_PATTERN);
        let path = items.slice(0, items.length - 1);
        console.log('--PATH: ' + path);
        let actionPath = items[items.length - 1];
        console.log('--ACTION: ' + actionPath);
        let i = 0;
        let match;
        do {
            match = this.findMenu(undefined, path[i++]);
        }
        while (i < path.length && match != undefined);
        let action = this.findAction(match, actionPath);
        if(action == undefined) {
            result.status = 'FAILED';
            result.reason = "Menu path " + options.path + " was not found";
        } else {
            result.status = 'PASSED';
            result.reason = '';
            $(action).trigger('click');
        }
        setTimeout(()=> {
            this.resolveCallback(result);
        }, 500);
    }

    findAction(match, actionPath) {
        console.log('--MATCH: ' + match);
        console.log('--PATH: ' + actionPath);
        let resultAction = undefined;
        if (match != undefined) {
            match.find('menu-link').each((index, element) => {
                if ($(element).attr('label') === actionPath) {
                    resultAction = $(element).find('.accordion-button')[0];
                    console.log("-ELEMENT: " + element);
                    return false;
                }
            });
        }
        console.log('--ACTION:' + resultAction);
        return resultAction;
    }

    findMenu(match, menu) {
        console.log('--MATCH: ' + match);
        console.log('--MENU: ' + menu);
        let result = undefined;
        let context;
        if(match != undefined) {
            context = match.find('labeled-accordion-wrapper');
        } else {
            context = $('labeled-accordion-wrapper');
        }
        console.log('--CONTEXT: ' + context);
        context.each((index, element) => {
            if ($(element).attr('label') === menu) {
                $(element).find('a').trigger('click');
                result = $(element);
                return false;
            }
        });
        console.log('--RESULT: ' + result);
        return result;
    }
}
