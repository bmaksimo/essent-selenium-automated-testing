class TrPlusActionFromList extends TestRunnerBase {

    constructor(options, callback) {
        super(options, callback, 1000);
    }

    run(options, result) {
        result.status = 'FAILED';
        result.reason = 'Not executed';
        const headerName = $(".list__header").children().filter(function (i, node) {
            return node.innerText.trim() === options.headerName;
        }).length;
        console.log(options.headerName);
        console.log(headerName);
        result.column = {"index": -1, "caption": ""};

        let row = parseInt(options.index) * 2 - 1;
        console.log("ROW: " + row);
        let index = $(".list__content th:contains('" + "')").index();
        console.log("INDEX: " + index);
        if (headerName > 0) {
            if (options.headerName == "Billing customer"){
                console.log(options.headerName);
                $('#d1baa825-bda0-93ac-1e01-5b4856e6ef71').trigger("click");
                result.status = 'PASSED';
                result.reason = '';
            } else if( options.headerName == "Contactpersons"){
                console.log(options.headerName);
                $('#ba17b5ef-5ac6-dc9e-d3e0-5b4856026911').trigger("click");
                result.status = 'PASSED';
                result.reason = '';
            }

        } else {
            result.status = 'FAILED';
            result.reason = 'Header name does not found.';
        }
        setTimeout(()=> {
            this.resolveCallback(result);
        }, 1000);
    }
}
