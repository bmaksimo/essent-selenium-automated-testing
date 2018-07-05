class TrPlusMenuHasItem extends TestRunnerBase {
    /**
     * Checks if plus menu contains item at given position
     *
     * * @param {object} options - Arguments passed from Java.
     * * @param {function} callback - The Java callback that handles the result.
     *
     * Java example:
     * assertThat(executeJsTest("new TrPlusMenuHasItem({item: 'Menu Item Label', position: 1,  arguments[arguments.length - 1]);", link), is(true));
     */

    constructor(options, callback) {
        super(options, callback, 100);
    }
    run(options, result) {
        result.status = 'FAILED';
        result.reason = `Plus Item  ${options.item} was not found at position ${options.position}`;
        if (!$("div[class='top-actions'] > .icon-plus.is-active").size()) {
            $("div[class='top-actions'] > .icon-plus").trigger('click');
        }
        setTimeout(()=> {
            let itemsCount = 1;
            $('a.icon-arrow-down').filter(function (a, b) {
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
