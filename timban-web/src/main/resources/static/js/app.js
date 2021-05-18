
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
            callback(false, oninvalid());
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

// function cleanList (records){
//     var i;
//     var today = new Date().setHours(0, 0, 0, 0);
//
//     for (i = 0; i < records.length; i++){
//         var thatDay = new Date(records[i].timestamp).setHours(0, 0, 0, 0);
//
//         //remove timestamps from the array that are not from today
//         if(today !== thatDay){
//             records.splice(i)
//         }
//         //sort list - recent timestamp is first
//         records.sort();
//         records.reverse();
//     }
// }
//
// function timeWorked (){
//     cleanList(records);
//     var i;
//     var difference = 0;
//
//     if (records[0].startRecording === true) {
//         for (i = 1; i < records.length; i++){
//             difference = records[i+1].timestamp - records[i].timestamp;
//
//     }


// }





<!-- Check-In Button-->


function timeRecord (userId , startRecording) {
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

<!-- Settings -->


function putProfile (name, email, password) {


    $.ajax({
        type: "PUT",
        contentType: "application/json",
        headers: {
            "X-XSRF-TOKEN": getCookie("XSRF-TOKEN")
        },
        url: serviceEndpointURL + "/api/users",
        data: JSON.stringify({
            "userName": name,
            "email": email,
            "password": password
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

<!-- Start: Antonio - Create User -->

function postProfile(name, workload, email, password, isAdmin, callbackSuccess ) {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        headers: {
            "X-XSRF-TOKEN": getCookie("XSRF-TOKEN")
        },
        url: serviceEndpointURL + "/api/users",
        data: JSON.stringify({
            "userName": name,
            "workload": workload,
            "email": email,
            "password": password,
            "isAdmin": isAdmin
        }),
        success: function (data, textStatus, response) {
            callbackSuccess(true);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);

        }
    });
}

<!-- End: Antonio - Create User -->

<!-- Start: Antonio - Update User -->
function updateUserByAdmin(name, workload, email, isAdmin, id, callbackSuccess) {
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        headers: {
            "X-XSRF-TOKEN": getCookie("XSRF-TOKEN")
        },
        url: serviceEndpointURL + "/api/users/" + id,
        data: JSON.stringify({
            "userName": name,
            "weeklyHours": workload,
            "email": email,
            "admin": isAdmin
        }),
        success: function (data, textStatus, response) {
            callbackSuccess(true);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);

        }
    });
}

<!-- End: Antonio - Update User -->

<!-- Start: Antonio - Delete User -->

/*
function deleteUser(id, callback) {
    $.ajax({
        type: "DELETE",
        headers: {
            "X-XSRF-TOKEN": getCookie("XSRF-TOKEN")
        },
        url: serviceEndpointURL + "/api/users"
    });
}
 */

<!-- End: Antonio - Delete User -->