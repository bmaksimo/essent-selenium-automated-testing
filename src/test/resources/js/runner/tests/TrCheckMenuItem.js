class TrCheckMenuItem extends TestRunnerBase {

    /**
     * Checks if a menu-item is present in the DOM
     *
     * * @param {object} options - Arguments passed from Java.
     * * @param {function} callback - The Java callback that handles the result.
     *
     * Java example:
     * assertThat(executeJsTest("new TrCheckMenuItem({menu: 'mainMenu', linkId: 'sales-marketing-link', arguments[arguments.length - 1]);", link), is(true));
     */

    constructor(options, callback) {
        super(options, callback, 100);
    }

    run(options, result) {
        result.status = 'FAILED';
        result.reason = this.options.item + ' not found among: ';
        let type = new Map([
            ['left', 'main-menu-link'],
            ['top', 'sub-menu-link']]).
        get(this.options.menu);
        if(!type) {
            result.reason = this.options.menu + ' not found';
        } else {
            $(type).filter((i, e)=> result.reason = result.reason.concat($(e).text().trim().concat(', ')))
            if($(type).filter((i, e) => $(e).text().trim() === options.item).size()) {
               result.status = 'PASSED';
               result.reason = '';
            }
        }
        this.resolveCallback(result);
    }
}
