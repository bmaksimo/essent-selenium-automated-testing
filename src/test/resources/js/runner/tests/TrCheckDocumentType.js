class TrCheckDocumentType extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 500);
    }

    run(options, result) {
        result.status = 'FAILED';
        result.reason = 'Document type ' + options.documentType + ' was not found';
        const length = $(".list__row").filter(function (i, node) {
            return node.children[1].textContent.trim() === options.documentType;
        }).length;
        if (length > 0) {
            result.status = 'PASSED';
            result.reason = '';
        }
        this.resolveCallback(result);
    }
}
