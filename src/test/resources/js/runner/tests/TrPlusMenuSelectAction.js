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
        let actionPath = items[items.length - 1];
        let match = this.findMenu(undefined, path);
        let action = this.findAction(match, actionPath);
        if(action == undefined) {
            result.status = 'FAILED';
            result.reason = "Menu path " + options.path + " was not found";
        } else {
            result.status = 'PASSED';
            result.reason = '';
            $(action).trigger('click');
        }
        this.resolveCallback(result);

    }

    findAction(accordion, actionLabel) {
        if (accordion != undefined) {
            let resultAction = $(accordion).find("[label='" + actionLabel + "']").find('.accordion-button');
            if(resultAction.index() == 0) {
                return resultAction;
            }
        } 
        return undefined;
    }

    findMenu(item, menu) {
        if(menu == undefined) {
            return undefined;
        }
        let menuItem = menu.shift();
        if(menuItem == undefined) {
            return item;
        }
        let context;
        if(item != undefined) {
            context = $(item).find('labeled-accordion-wrapper');
        } else {
            context = $('labeled-accordion-wrapper');
        }
        context = context.filter((i, e)=>{
            return $(e).attr('label') === menuItem;
        });
        if (context.index() >= 0) {
            $(context[0]).find('a')[0].click();
            return this.findMenu(context[0], menu);
        }
        return undefined;
    }
}
