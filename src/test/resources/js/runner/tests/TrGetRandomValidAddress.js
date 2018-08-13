class TrGetRandomValidAddress extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 100);
    }

    run(options, result) {
        let addressStrip = options.address.replace('["','');
        addressStrip = addressStrip.replace('"]','');
        let address = addressStrip.split(",");
        console.log(address);
        let location = {
            "street": address[4],
            "houseNr": address[5],
            "houseNrAdd": "",
            "city": address[7],
            "state": "",
            "postcode": address[6],
            "bus": ""
        };
        result.status = 'PASSED';
        result.reason = '';
        result.location = location;
        this.resolveCallback(result);
    }

}
