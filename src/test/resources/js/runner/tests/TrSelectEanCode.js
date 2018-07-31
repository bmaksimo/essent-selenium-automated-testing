class TrSelectEanCode extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 1000);
    }

    run(options, result) {
        result.status = 'PASSED';
        result.reason = '';

        if($('#ean_c autocomplete li:first a b').length > 0) {

            let ean = $('#ean_c autocomplete li:first a b').text();

            let eanSplit = ean.split(' ');

            $('#ean-c-accounts-aos-quotes-aos-products-quotes-c-0-f-94-c-2-f-72-e-0-51-b-9-ce-93-58930799-ecf-1-field').val(eanSplit[0]);

            let meter = eanSplit[1].replace('(', '').replace(')', '');

            $('#meter-no-c-accounts-aos-quotes-aos-products-quotes-c-0-f-94-c-2-f-72-e-0-51-b-9-ce-93-58930799-ecf-1-field').val(meter);

        } else {

            result.status = 'FAILED';
            result.reason = `No ean found`;

        }

        this.resolveCallback(result);
    }
}
