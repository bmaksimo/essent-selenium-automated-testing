/**
 * Generates random user data.
 * Info: https://randomuser.me
 */
class TrGetRandomUser extends TestRunnerDwp {

    constructor(options, callback) {
        super(options, callback, 100);
    }

    run(options, result) {
        let user = [];
        $.ajax({
            url: 'https://randomuser.me/api/1.2/?inc=gender,dob,email,name,nat&nat=nl',
            dataType: 'json',
            success: function(data) {
                user = data.results;
                console.log(data);
            },
            async: false
        });
        if(user.length) {
            result.status = 'PASSED';
            result.reason = '';
            result.user = user[0];
        } else {
            result.status = 'FAILED';
            result.reason = 'Random user data was not retrieved';
        }

        this.resolveCallback(result);
    }

}
