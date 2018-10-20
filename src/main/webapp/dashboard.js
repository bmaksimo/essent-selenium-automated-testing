var nrOfScenarios = 0;
var nrOfFailedScenarios = 0;
var scenariosPerArea = {
    payment : {
        tag : "@CORE",
        passedScenarios : [],
        failedScenarios : []
    },
    payment : {
        tag : "@PAYMENTS",
        passedScenarios : [],
        failedScenarios : []
    },
    contracting : {
        tag : "@CONTRACTING",
        passedScenarios : [],
        failedScenarios : []
    },
    service : {
        tag : "@SERVICING",
        passedScenarios : [],
        failedScenarios : []
    },
    unassigned : {
        passedScenarios : [],
        failedScenarios : []
    }
};
var cucumberLastModified;

function cucumberJsonURL() {
    var currentUrl = window.location.href;
    var baseUrl = currentUrl.substring(0,currentUrl.lastIndexOf("/")+1);
    return baseUrl+"cucumber.json";
}

function downloadCucumberJson() {
    console.log('getting the cucumber.json...');
    var json = (function () {
        var json = null;
        $.ajax({
            'async': false,
            'global': false,
            'url': cucumberJsonURL(),
            'dataType': "json",
            'success': function (data, textStatus, jqXHR) {
                json = data;
                cucumberLastModified = jqXHR.getResponseHeader("Last-Modified");
            }
        });
        return json;
    })();
    console.log('done');
    return json;
}

function aggregateIntoScenariosPerArea(json) {
    console.log('calculating...');
    for (var k = 0; k < json.length; k++) {
        var feature = json[k];
        console.log('-'+feature.name+'-');
        var tags = jsonPath(feature, "$.tags[*].name");
        var area = undefined;
        for (var anArea in scenariosPerArea) {
            if($.inArray(scenariosPerArea[anArea].tag, tags) > -1) {
                console.log(feature.name+' >>> '+scenariosPerArea[anArea].tag);
                area = scenariosPerArea[anArea];
            }
        }
        if(area == undefined) {
            console.log(feature.name+' >>> UNASSIGNED');
            area = scenariosPerArea.unassigned;
        }

        var scenarios = jsonPath(feature, "$..[?(@.type == 'scenario')]");

        for (var i = 0; i < scenarios.length; i++) {
            var scenario = scenarios[i];
            nrOfScenarios++;
            var passed = true;
            for (var j = 0; j < scenario.steps.length; j++) {
                var step = scenario.steps[j];
                if(step.result.status != 'passed') {
                    passed = false;
                    break;
                }
            }
            if(passed) {
                area.passedScenarios.push(scenario);
            } else {
                nrOfFailedScenarios++;
                area.failedScenarios.push(scenario);
                console.log('        '+scenario.name+' >>> FAILED');
            }
        }
    }
    console.log('done');
}

function updateHtml() {
    $('.nrall').text(nrOfScenarios);
    $('.nrfailed').text(nrOfFailedScenarios);

    for (var anArea in scenariosPerArea) {
        var nrFailedForArea = scenariosPerArea[anArea].failedScenarios.length;
        var nrAllForArea = scenariosPerArea[anArea].failedScenarios.length + scenariosPerArea[anArea].passedScenarios.length;
        if(nrFailedForArea > 0) {
            var fraction = nrFailedForArea/nrAllForArea;
            var percentage = fraction.toFixed(2)*100;
            $('#'+anArea).addClass('failure');
            $('#'+anArea).append('<div class="failure_text"><span class="nr_of_failures">'+percentage+'%</span><br/>failed</div>');
            $('#'+anArea).append('<div class="total_text">'+nrFailedForArea+' out of '+nrAllForArea+'</div>');
        } else {
            $('#'+anArea).append('<div><img class="ok_image" src="sun_strong_bold.png"/></div>');
            $('#'+anArea).append('<div class="total_text">'+nrAllForArea+' in total</div>');
            $('#'+anArea).addClass('success');
        }
    }

    if(scenariosPerArea.unassigned.failedScenarios.length == 0) {
        $('#unassigned').remove();
        $('.centered').css('margin-left', '-550px');
        $('.centered').css('width', '1150px');
    }

    $('#cucumber_last_modified').text(moment(cucumberLastModified).format("DD/MM/YYYY HH:mm"));
}

function process() {
    var json = downloadCucumberJson();
    aggregateIntoScenariosPerArea(json);
    updateHtml();
}

function refreshAt(hours, minutes, seconds) {
    var now = new Date();
    var then = new Date();

    if(now.getHours() > hours ||
        (now.getHours() == hours && now.getMinutes() > minutes) ||
        now.getHours() == hours && now.getMinutes() == minutes && now.getSeconds() >= seconds) {
        then.setDate(now.getDate() + 1);
    }
    then.setHours(hours);
    then.setMinutes(minutes);
    then.setSeconds(seconds);

    var timeout = (then.getTime() - now.getTime());
    setTimeout(function() { window.location.reload(true); }, timeout);
}
