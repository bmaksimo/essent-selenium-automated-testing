class TrGetUserLanguage extends TestRunnerDwp {

    /**
     * Checks and returns the user language value
     *
     * @param {object} options - Arguments passed from Java.
     * @param {function} callback - The Java callback that handles the result.
     *
     * Java example:
     *    TrGetUserLanguageOptions options = new TrGetUserLanguageOptions();
     *    options.setLanguageKey("NG_TRANSLATE_LANG_KEY");
     *    Map result = executeJavascriptMethod("TrGetUserLanguage", options);
     *    String userLanguage = (String) result.get("userLanguage");
     */

    constructor(options, callback) {
        super(options, callback, 100);
    }

    run() {
        let result = this.result;
        let options = this.options;
        let userLanguage = window.localStorage.getItem(options.languageKey);
        if (userLanguage) {
            result.status = 'PASSED';
            result.reason = '';
        }
        result.userLanguage = userLanguage;
        this.resolveCallback(result);
    }

}
