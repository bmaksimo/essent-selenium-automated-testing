class TrOdooClickTopMenu extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 100);
    }

    run() {
        let result = this.result;
        const options = this.options;
        result.status = 'UNDEFINED';
        result.reason = 'Not executed';
        let matches = [];
        console.log("TESTESTETSTSTSTSE");
//        $('.top-menu sub-menu').children('sub-menu-link').each(function(index, element) {
//            if(options.label != $(this).attr('label')) {
//                return;
//            } else {
//                matches.push($(this));
//                return false;
//            }
//        });
//        if(matches.length > 0) {
//            matches[0].find('a').trigger('click');
//            result.status = 'PASSED';
//            result.reason = '';
//        } else {
//            result.status = 'FAILED';
//            result.reason = 'Element ' + options.label + ' not found';
//        }
        setTimeout(()=> {
            this.resolveCallback(result);
        }, 5000);
    }
}
