class TrPlusMenuClickItem extends TestRunnerDwp {

    /**
     * Checks if a menu-item is present in the DOM
     *
     * * @param {object} options - Arguments passed from Java.
     * * @param {function} callback - The Java callback that handles the result.
     */

    constructor(options, callback) {
        super(options, callback, 100);
    }

    run(options, result) {
        if(!$("div[class='top-actions'] > .icon-plus.is-active").size()) {
            $("div[class='top-actions'] > .icon-plus").trigger('click');
        }
        setTimeout(()=> {
            if($(".plus-menu.ng-hide").length === 0) {
                result.plusMenuOpen = true;
                result.reason = 'DEBUG';
            }
            this.resolveCallback(result);
        }, 200);
    }

}
