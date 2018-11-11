/* TODO:  Clearing the forms fields(Both register and login)    */
$(document).ready(function () {
    var registerFields = ["firstName", "lastName", "userNameSign", "registerEmail", "pwd", "userName", "loginpwd","email-forgot"];

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

    $('#forgot-password-section').on('click',function (e) {
        resetRegisterFields()
    });


});

$(document).ready(function() {
    // $('#newer').autocomplete({
    //     serviceUrl: '/users',
    //     paramName: "firstName",
    //     type:"post",
    //     delimiter: ",",
    //     source: function(request,response) {
    //         $.getJSON("/users",request,function (result) {
    //             response($.map(result,function (item) {
    //                 return{
    //                     label:item.name + "(" + item.firstName + ")",
    //                     value:item.name
    //                 }
    //             }))
    //         })
    //     }
    //
    // });
    //******************************************************************************************************************

    var $searchedUsers = $('#searched-users');

    $('#newer').on('keyup', function() {
        clearTimeout($.data(this, 'timer'));
        $(this).data('timer', setTimeout(searchUser, 500));
    });

    function searchUser() {
        var searchValue = $('#newer').val();
        if (searchValue.length === 0) {
            $searchedUsers.html('');
            $searchedUsers.hide();
        }

       var users = ['ad1', 'aad2', 'ad3', 'ad44'];
        var users=[$.ajax({
            type:"POST",
            contentType:"application/json",
            url:"/users",
            data: JSON.stringify,
            dataType: 'json',
            cache: false,
            success:function(data) {
                        JSON.stringify(data,null);
                        console.log("success",data);
                        }

        })];

        if (users.length === 0) {
            $searchedUsers.append('<div class="search-user">no user found</div>');
            $searchedUsers.show();
            return;
        }

        users.forEach(function (user) {
            console.log(user);
            $searchedUsers.append('<div class="search-user">'+user+'</div>');
        });
        $searchedUsers.show();

    }
})

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