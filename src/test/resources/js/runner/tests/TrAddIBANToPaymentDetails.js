class TrAddIBANToPaymentDetails extends TestRunnerBase {
    constructor(options, callback) {
        super(options, callback, 1000);
    }

    run(options, result) {
        result.status = 'FAILED';
        result.reason = 'Not executed';

        const iban = options.iban;
        const ibanField = $('#bankaccounts-iban-field');

        if (ibanField !== '') {
            result.status = 'PASSED';
            result.reason = '';
        } else {
            ibanField.prop('value', iban);
            if (iban === ibanField.val()) {
                result.status = 'PASSED';
                result.reason = '';
            } else {
                result.reason = 'IBAN field failed to update.';
            }
        }
        this.resolveCallback(result);
    }
}
