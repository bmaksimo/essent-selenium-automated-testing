class TrPlusMenuClickItem extends TestRunnerBase {

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
        if(!$("div[class='top-actions'] > .icon-plus.is-active").size()) {
            $("div[class='top-actions'] > .icon-plus").trigger('click');
        }
        setTimeout(()=> {
            if($(".plus-menu.ng-hide").length === 0) {
                result.plusMenuOpen = true;
                result.reason = 'DEBUG';
                console.log(result);
                console.log("Plus - Menu clicked on.")
            }
            this.resolveCallback(result);
        }, 200);
    }
}
