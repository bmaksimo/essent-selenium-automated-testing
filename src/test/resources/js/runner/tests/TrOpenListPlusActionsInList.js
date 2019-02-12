/**
 *  TrOpenListPlusActionsInList
 *  Clicks on Plus and opens Plus actions row at given row in a given list
 */
class TrOpenListPlusActionsInList extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 1000);
    }

    run(options, result) {
        result.status = 'PASSED';
        result.reason = '';
        let index = this.options.index - 1;
        let list = this.options.list;

        let action = $("h2:contains('"+list+"')").closest("list").find('tbody').find('tr').eq(index).find('.icon-plus');

        if(action.length > 0) {
            action.trigger('click');
            result.status = 'PASSED';
            result.reason = '';
        } else {
            result.status = 'FAILED';
            result.reason = 'No rows with index ' + index + ' found in the ' + list + ' list or no plus icon found.'
        }

        this.resolveCallback(result);
    }
}
