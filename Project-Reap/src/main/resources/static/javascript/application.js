/* TODO:  Clearing the forms fields(Both register and login)    */
$(document).ready(function () {
    var registerFields = ["firstName", "lastName", "userNameSign", "registerEmail", "pwd", "userName", "loginpwd"];

    function resetRegisterFields() {

        registerFields.forEach(function (i) {
            console.log("#" + i);
            $('#' + i).val('');

        });
    }
    $('#registerButton').on('click', function (e) {
        resetRegisterFields();
    });
    $('#login-section').on('click', function (e) {
        resetRegisterFields();

    });
});

/*TODO: Function for the logout section..*/
function logout() {
    console.log("checking Logout......");
    $.ajax({
        type: 'Post',
        cache: false,
        url: '/logout',
        success: function (data) {
            // $('#serverTime').html(data)
            console.log("logout successful",data);
        }
    });
}

//TODO:Not working.Tried Ajax for validations with modal
function SaveForm() {
    var data = $("#registeration").serialize();
    $('#register-message').html('Loading....');
    $.ajax({
        type: "post",
        data: data,
        url: "/index",
        success: function (response) {
            if (response.success === true) {
                $('#register-message').html("success");
            } else {
                response.errors.forEach(function (error) {
                    $('#register-message').html(error);
                });
            }
            console.log("request from server", data);
        }
    })
}

//TODO:Not working.Tried Ajax for validations with modal
function loginForm() {
    var data = $("#signIn").serialize();
    $('#login-alerts').html('Loading....');
    $.ajax({
        type: "post",
        data: data,
        url: "/login",
        success: function (response) {
            if (response.success === true) {
                $('#login-alerts').html("success");
            } else {
                // response.errors.forEach(function (error) {
                    $('#login-alerts').html("error");
                // });
            }
            console.log("request from server", data);
        }
    })
}



/* Holding the modal for login section */
//TODO:This is nor working.Showing error in fetching requested url
function myLogin() {
    var data = $("#signIn").serialize();
    $("form").on("submit", function (event) {
        event.preventDefault();
        $.ajax({
            url: "/index",
            type: "POST",
            data: data,
            success: function (result) {
                console.log(result)
            }
        });
    })
}