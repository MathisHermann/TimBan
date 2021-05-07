
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

//
//
// function timeWorked (records) {
//     var today = new Date();
//     var i;
//     var x;
//     var timeWorked = 0;
//
//
//     for (x = 0; x < records.length; x++)
//     {
//         for (i = 0; i < records.length; i++)
//         {
//             if (records.timestamp.getDate() == today)
//             {
//                 var timestamp[i+1] = records.timestamp.getTime();
//                 continue;
//             }
//         }
//
//         if (timestamp1 != null && timestamp2 != null){
//             var difference = timestamp2 - timestamp1;
//             timeWorked + difference;
//         }
//         return timeWorked;
//     }

// }


<!-- Check-In Button-->
function timeRecord () {
    var xhttp = new XMLHttpRequest();
    document.getElementById("check-in-button")
    xhttp.open("POST", "/api/records", true);
    xhttp.send();
    console.log("hello");
}




<!-- END PART LARS-->