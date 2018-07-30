class TrClickOnElement extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 1000);
    }

    run(options, result) {
        result.status = 'UNDEFINED';
        result.reason = 'Not found';
        let map = new Map(['.list__actions .icon-plus']);
        let query = map.get(options.element);
        let matches = $(query);
        if (matches.length >= 0) {
            $('.list__actions .icon-plus').click();
            result.status = 'PASSED';
            result.reason = '';
        } else {
            result.status = 'FAILED';
            result.reason = 'Start new market scenario ' + options.element + 'not found';
        }
        setTimeout(()=> {
            this.resolveCallback(result);
        }, 1000);
    }
}

//dwp-app/div[3]/focus-mode/focus-mode-content[@title='Van Hauwaert Steven']
//div[@class='col-1-1']//gridlr[@class='']//list/div/div[@class='list__actions']/div[2]/a[2]
