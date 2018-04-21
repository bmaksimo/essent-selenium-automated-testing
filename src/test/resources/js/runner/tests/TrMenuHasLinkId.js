class TrMenuHasLinkId extends TestRunnerBase {

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

    run() {
        let result = this.result;
        let options = this.options;

        this.getState();
        result.status = 'FAILED';
        result.reason = this.options.linkId + ' not found';
        this.state[this.options.menu].forEach(function(menuItem){
            if (menuItem['link-id'] === options.linkId) {
                result.data = {menuItem: menuItem};
                result.status = 'PASSED';
                result.reason = '';
            }
        });

        this.resolveCallback(result);
    }

}
