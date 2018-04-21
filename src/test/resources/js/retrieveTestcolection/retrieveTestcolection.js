/*************************************************************************************
 *** Download Chrome Add-on "Scratch JS"
 *** Login on DWP
 *** open control panel
 *** navigate to 'Scratch JS' tab
 *** pased the code in this view and press "RUN"
 *************************************************************************************/
retrieveTestcolection();

function retrieveTestcolection(){

    var HOST = location.protocol + '//' + window.location.hostname;
    var objAdminMenu = getJsonMenuItems()
    var intInvalidJsonString = 0;
    var currentUser;
    var objReturn = new Object()

    $.get(HOST+"/nova-crm/Api/V8_Custom/user/current", function(data, status){
        currentUser = data.data

        var today = new Date();
        var options = { year: "numeric", month: "numeric", day: "numeric" };

        objReturn.user = new Object();

        objReturn.user.user_name = currentUser.user_name;
        objReturn.user.is_admin = currentUser.is_admin;
        objReturn.user.status = currentUser.status;
        objReturn.user.language = currentUser.preferred_language;
        objReturn.date = today.toLocaleDateString("en-US",options);

        setTimeout(function () {

            loadUrl(0,0);
        }, 2000);
    });

    function IsJsonString(str) {
        try {
            JSON.parse(str);
        } catch (e) {
            return false;
        }
        return true;
    }

    function loadUrl(intMainCurrent,intSubCurrent){

        var intMainMax = Object.keys(objAdminMenu).length -1;
        if(intMainCurrent > intMainMax){
            return false
        }
        var strMain = Object.keys(objAdminMenu)[intMainCurrent];
        var objMain = objAdminMenu[strMain];

        var intSubMax = Object.keys(objMain).length -1;
        if(intSubCurrent > intSubMax ){
            intMainCurrent++;
            loadUrl(intMainCurrent,0)
            return true;
        }
        var strSub = Object.keys(objMain)[intSubCurrent]
        var objSub = objMain[strSub]

        objReturn.pageUrl = objSub.url
        objReturn.pageApi = objSub.filter

        var filename = strMain + "-" + strSub + ".json";
        console.log(intMainCurrent + "." + intSubCurrent + ") " + filename + "\n"+ objSub.filter );

        $.get(objSub.filter, function(data, status){

            if(!IsJsonString(data)){
                if(intInvalidJsonString < 5){
                    intInvalidJsonString++;
                    console.log('tray again json validation ')
                    loadUrl(intMainCurrent,intSubCurrent)
                }else{
                    console.log('ERROR maximum attempts of json validation')
                }
                return false;
            }else{
                intInvalidJsonString = 0;
            }

            var objData = JSON.parse(data);
            var filters = objData.data.fieldGroups;

            objReturn.tests = buildFilterObj(filters)
            var strData = JSON.stringify( objReturn);

            download(filename,strData);

            setTimeout(function () {
                loadUrl(intMainCurrent,intSubCurrent+1)
            }, 1000);

            return false;
        });
    }


    function buildFilterObj(obj){
        var objReturn  = new Object();

        $.each(obj, function(mainNum, objmain) {
            $.each(objmain, function(subNum, objsub) {

                if(typeof objsub === 'object'){
                    $.each(objsub, function(num, fields) {

                        var Ftype = fields.type;
                        if(typeof objReturn[Ftype] === 'object'){
                        }else{
                            objReturn[Ftype] = new Object();
                        }
                        var number = _.size(objReturn[Ftype])
                        objReturn[Ftype][number] = fields
                    })
                }
            })
        })
        return objReturn;
    }


    function download(filename, text) {
        var element = document.createElement('a');
        element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
        element.setAttribute('download', filename);
        element.style.display = 'none';
        document.body.appendChild(element);
        element.click();
        document.body.removeChild(element);
    }



    function getJsonMenuItems(){

        var objMenuItems = new Object();
        var objTestCaseExists = new Object();

        var main = 'sales-marketing';
        objMenuItems[main] = new Object();

        var sub = 'sales-marketing';
        objMenuItems[main][sub] = new Object();
        objMenuItems[main][sub]['url'] = HOST+"/dwp/#/sales-marketing/dashboard/sales-marketing/";
        objMenuItems[main][sub]['filter'] = HOST+"/nova-crm/Api/V8_Custom/Filter/task_list/GroupQuotationTaskList";

        sub = 'MarketTransactions-Dashboard';
        objMenuItems[main][sub] = new Object();
        objMenuItems[main][sub]['url'] = HOST+'/dwp/#/sales-marketing/dashboard/MarketTransactions_Dashboard/';
        objMenuItems[main][sub]['filter'] = HOST+'/nova-crm/Api/V8_Custom/Filter/MarketTransaction_Filter/MarketTransactions_DynamicList';

        sub = 'my-accounts-list';
        objMenuItems[main][sub] = new Object();
        objMenuItems[main][sub]['url'] = HOST+'/dwp/#/sales-marketing/dashboard/my_accounts_list/';
        objMenuItems[main][sub]['filter'] = HOST+'/nova-crm/Api/V8_Custom/Filter/accounts_filters_big/My%20Accounts';

        sub = 'lead-list';
        objMenuItems[main][sub] = new Object();
        objMenuItems[main][sub]['url'] = HOST+'/dwp/#/sales-marketing/dashboard/lead_list/';
        objMenuItems[main][sub]['filter'] = HOST+'/nova-crm/Api/V8_Custom/Filter/all_leads/Leads';

        sub = 'accounts-list';
        objMenuItems[main][sub] = new Object();
        objMenuItems[main][sub]['url'] = HOST+'/dwp/#/sales-marketing/dashboard/accounts_list/';
        objMenuItems[main][sub]['filter'] = HOST+'/nova-crm/Api/V8_Custom/Filter/accounts_filters_big/Accounts';

        sub = 'quotes-list';
        objMenuItems[main][sub] = new Object();
        objMenuItems[main][sub]['url'] = HOST+'/dwp/#/sales-marketing/dashboard/quotes_list/';
        objMenuItems[main][sub]['filter'] = HOST+'/nova-crm/Api/V8_Custom/Filter/all_quotes_filter/Quotes';

        sub = 'contract-list';
        objMenuItems[main][sub] = new Object();
        objMenuItems[main][sub]['url'] = HOST+'/dwp/#/sales-marketing/dashboard/contract_list/';
        objMenuItems[main][sub]['filter'] = HOST+'/nova-crm/Api/V8_Custom/Filter/all_contracts_filter/Contracts';

        sub = 'cases';
        objMenuItems[main][sub] = new Object();
        objMenuItems[main][sub]['url'] = HOST+'/dwp/#/sales-marketing/dashboard/cases/';
        objMenuItems[main][sub]['filter'] = HOST+'/nova-crm/Api/V8_Custom/Filter/all_cases_filter/Cases'

        sub = 'Euroccor-Quotes';
        objMenuItems[main][sub] = new Object();
        objMenuItems[main][sub]['url'] = HOST+'/dwp/#/sales-marketing/dashboard/Euroccor_Quotes/';
        objMenuItems[main][sub]['filter'] = HOST+'/nova-crm/Api/V8_Custom/Filter/all_quotes_filter/EurocorrQuotes';

        return objMenuItems;

    }

}