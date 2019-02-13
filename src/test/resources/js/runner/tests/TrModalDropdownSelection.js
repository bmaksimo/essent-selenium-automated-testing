/**
 *  TrOpenListPlusActionsInList
 *  Clicks on Plus and opens Plus actions row at given row in a given list
 */
class TrModalDropdownSelection extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 1000);
    }

    run(options, result) {
        result.status = 'PASSED';
        result.reason = '';
        let label = this.options.label;
        let value = this.options.value;

        let menu = $("label:contains("+label+")").parent().find('select');

        if(menu.find("option:contains('"+value+"')").text() == value) {
            $(menu).val('string:'+value).trigger("change");
            result.status = 'PASSED';
            result.reason = '';
        } else {
            result.status = 'FAILED';
            result.reason = 'Option ' +value+ ' does not exist is the menu ' +label+ '.';
        }

        this.resolveCallback(result);
    }
}
