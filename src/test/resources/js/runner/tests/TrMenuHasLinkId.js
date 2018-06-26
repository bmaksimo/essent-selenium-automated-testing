class TrMenuHasLinkId extends TestRunnerDwp {

    /**
     * Checks if a menu-item is present in the DOM
     *
     * * @param {object} options - Arguments passed from Java.
     * * @param {function} callback - The Java callback that handles the result.
     *
     * Java example:
     * assertThat(executeJsTest("new TrMenuHasLinkId({menu: 'mainMenu', linkId: 'sales-marketing-link', arguments[arguments.length - 1]);", link), is(true));
     */

    constructor(options, callback) {
        super(options, callback, 100);
    }

    run(options, result) {
        let state = this.getState();
        result.status = 'FAILED';
        result.reason = this.options.linkId + ' not found';
        let targetNumber = 1;
        let matches = [];
        state[this.options.menu].some(function(menuItem){
            if (menuItem['link-id'] === options.linkId) {
                result.data = {menuItem: menuItem};
                result.status = 'PASSED';
                result.reason = '';
                return true;
            }
        });
        this.resolveCallback(result);
    }

}
