class TrGetRandomValidAddress extends TestRunnerDwp {

    constructor(options, callback) {
        super(options, callback, 100);
    }

    run(options, result) {
        let location = {
                "street": "Vijgenstraat",
                "houseNr": "3",
                "houseNrAdd": "",
                "city": "Lokeren",
                "state": "",
                "postcode": "9160",
                "bus": ""};
        result.status = 'PASSED';
        result.reason = '';
        result.location = location;
        this.resolveCallback(result);
    }

}
