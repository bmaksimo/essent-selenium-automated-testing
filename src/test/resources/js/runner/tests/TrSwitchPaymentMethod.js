class TrSwitchPaymentMethod extends TestRunnerBase {
    constructor(options, callback) {
        super(options, callback, 1000);
    }

    run(options, result) {
        result.status = 'FAILED';
        result.reason = 'Not executed';
        result.paymentMethod = 'Unknown payment method';

        const bankTransferPaymentMethod = "string:OV";
        const directDebitPaymentMethod = "string:DOM";
        const currentPaymentMethod = $("#payment-methods-valid-to-is-null-payment-method-field").prop('value');

        if (bankTransferPaymentMethod === currentPaymentMethod) {
            $("#payment-methods-valid-to-is-null-payment-method-field").prop('value', directDebitPaymentMethod);
            $("#payment-methods-valid-to-is-null-payment-method-field").change();
            result.status = 'PASSED';
            result.reason = '';
            result.paymentMethod = directDebitPaymentMethod;
        } else if (directDebitPaymentMethod === currentPaymentMethod) {
            $("#payment-methods-valid-to-is-null-payment-method-field").prop('value', bankTransferPaymentMethod);
            $("#payment-methods-valid-to-is-null-payment-method-field").change();
            result.status = 'PASSED';
            result.reason = '';
            result.paymentMethod = bankTransferPaymentMethod;
        } else {
            result.reason = 'Unknown payment method.';
        }

        this.resolveCallback(result);
    }
}
