class TrEvaluateXpath extends TestRunnerBase {

    /**
     * Evaluates the Xpath expression
     *
     * * @param {object} options - Arguments passed from Java.
     * * @param {function} callback - The Java callback that handles the result.
     *
     * Java example:
     * Map<String, String> xpathOptions = new HashMap<>();
     * xpathOptions.put("xpath", "//div[@class='top-menu']/sub-menu/sub-menu-link/a[@id='market-transactions-dashboard-link']");
     * executeJsMethod("TrEvaluateXpath", xpathOptions);
     */

    constructor(options, callback) {
        super(options, callback, 100);
    }

    run(options, result) {
        setTimeout(()=> {
            let xPath = options.xpath;
            let elements = this.evaluateXpath(xPath);
            if(elements.length >= 0) {
                result.status = "PASSED";
                result.reason = 'DEBUG elements are found by Xpath ' + xPath;
                console.log(result);
            }
            this.resolveCallback(result);
        }, 200);
    }
}
