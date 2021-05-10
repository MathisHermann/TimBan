
<!-- START PART LARS-->

<!-- COPIED & ADJUSTED FROM INTERNET TECHNOLOGY PROJECT -->

serviceEndpointURL = window.location.protocol + "//" + window.location.host

function login(email, password, callback) {

    $.ajax({
        type: "POST",
        contentType: "application/json",
        headers: {
            "X-XSRF-TOKEN": getCookie("XSRF-TOKEN")
        },
        url: serviceEndpointURL + "/login",
        data: JSON.stringify({
            "email": email,
            "password": password,
            "remember": false
        }),
        success: function (data, textStatus, response) {
            callback(true);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            callback(false);
        }
    });

}

function validateLogin(callback) {
    $.ajax({
        type: "HEAD",
        url: serviceEndpointURL + "/validate",
        success: function (data, textStatus, response) {
            callback(true);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            callback(false);
        }
    });
}

function preventDefault(){

}

function currentTime () {
    var today = new Date();
    var time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
    return currentTime;
}

function getCookie(name) {
    var match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
    if (match) return match[2];
}


<!-- TIME AND DATE-->



// function timeWorked() {
//     var today = new Date();
//     var i;
//     var x;
//     var timeWorked = 0;
//
//
//             for (i = 0; i < record.length; i++)
//         {
//             if (record.timestamp.getDate() == today)
//             {
//                 console.log(record.timestamp.toString());
//                 var timestamp[i] = record.timestamp.getTime();
//                 continue;
//             }
//         }
//
//             for (x = 0; i < record.length; i++) {
//
//                 if (timestamp[x] != null && timestamp[x+1] != null){
//                      var difference = timestamp2 - timestamp1;
//                      timeWorked + difference;
//
//                     return timeWorked.showCalculatedTime();
//         }
//     }
// }
//
// function showCalculatedTime (timeWorked){
//
// }



<!-- Check-In Button-->

function timeRecord (userId , startRecording) {
    // var xhttp = new XMLHttpRequest();
    // xhttp.open("POST", "/api/records", true);
    // xhttp.send("{'userId':12, 'startRecording': true, 'timestamp': 1619532050}");
    var timestamp = new Date();


    $.ajax({
        type: "POST",
        contentType: "application/json",
        headers: {
            "X-XSRF-TOKEN": getCookie("XSRF-TOKEN")
        },
        url: serviceEndpointURL + "/api/records",
        data: JSON.stringify({
            "userId": userId,
            "startRecording": startRecording,
            "timestamp": timestamp
        }),
        success: function (data, textStatus, response) {
            // callback(true);
            location.reload();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            callback(false);
        }
    });
}




<!-- END PART LARS-->