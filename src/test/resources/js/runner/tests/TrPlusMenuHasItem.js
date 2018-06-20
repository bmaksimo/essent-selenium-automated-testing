class TrPlusMenuHasItem extends TestRunnerDwp {
    /**
     * Checks if plus menu contains item
     *
     * * @param {object} options - Arguments passed from Java.
     * * @param {function} callback - The Java callback that handles the result.
     *
     * Java example:
     * assertThat(executeJsTest("new TrGetColumnIndexList({menu: 'mainMenu', linkId: 'sales-marketing-link', arguments[arguments.length - 1]);", link), is(true));
     */

    constructor(options, callback) {
        super(options, callback, 100);
    }
    run(options, result) {
        result.status = 'FAILED';
        result.reason = options.item + ' plus menu item was not found at position ' + options.position;
        if (!$("div[class='top-actions'] > .icon-plus.is-active").size()) {
            $("div[class='top-actions'] > .icon-plus").trigger('click');
        }
        setTimeout(()=> {
            let itemsCount = 1;
            $('a.icon-arrow-down').some(function (a, b) {
                if (b.innerText.trim().length > 0) {
                    if (b.innerText.trim() === options.item &&
                        itemsCount === options.position) {
                        result.status = 'PASSED';
                        result.reason = '';
                        return false;
                    }
                    itemsCount++;
                }
            });
            $("div[class='top-actions'] > .icon-plus").trigger('click');
            this.resolveCallback(result);
        }, 500);
    }
}
