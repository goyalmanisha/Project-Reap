/* TODO:  Clearing the forms fields(Both register and login)    */
$(document).ready(function () {
    var registerFields = ["firstName", "lastName", "userNameSign", "registerEmail", "pwd", "userName", "loginpwd", "email-forgot"];

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

    $('#forgot-password-section').on('click', function (e) {
        resetRegisterFields()
    });


    if (document.getElementById("newer")) {
        $("#newer").autocomplete({
            source: "/users",
            minLength: 1,
            select: function (event, ui) {
                // console.log(ui);
                $("#appreciatedUser").val(ui.item.userId)
            }
        });
    }


    $('.update-user').on('click', function (e) {
        e.preventDefault();
        var $this = $(this);
        var id = $this.data('id');
        var role = $this.parent().parent().find('select').val();
        var status = $this.parent().parent().find($("input[name='status']:checked")).val();
        // $('.role' + id).val();
        console.log("id is", id);
        console.log("role is", role);
        console.log("user is", status);
        $.ajax({
            type: "post",
            url: "/admin/update-user",
            data: {
                userId: id,
                userRole: role,
                userStatus: status
            },
            success: function (res) {
                console.log("successfully update", res);
                // $('select').append($('<option>', {
                //     value: id,
                //
                // }));
            },
            error: function (err) {
                console.log("error")
            },
            complete: function () {

            }
        });
    });

            //
            // $("#refresh").click(
            //     function(){
            //         var assigned = $("#role1").val();
            //         $('#role select').val( assigned );
            //         console.log("oyeeee",assigned)
            //     }
            // );

    $('#karma').on('change', function () {
        if (this.value === 'Extra Miler') {
            $("#karma1").show();
            $("#selectKarma").hide();
        }
        else if (this.value === 'Knowledge Sharing') {
        $("#karma2").show();
        $("#karma1").hide();
        $("#selectKarma").hide();
    }
    else if (this.value === 'Mentorship') {
        $("#karma3").show();
        $("#karma2").hide();
        $("#karma1").hide();
        $("#selectKarma").hide();
    }
    else if (this.value === 'Pat on the back') {
        $("#karma4").show();
        $("#karma3").hide();
        $("#karma2").hide();
        $("#karma1").hide();
        $("#selectKarma").hide();
    }
    else if (this.value === 'Customer Delight') {
        $("#karma5").show();
        $("#karma4").hide();
        $("#karma3").hide();
        $("#karma2").hide();
        $("#karma1").hide();
        $("#selectKarma").hide();
    }
    else if (this.value === 'Continuous Learning and Improvement') {
        $("#core1").show();
        $("#karma5").hide();
        $("#karma4").hide();
        $("#karma3").hide();
        $("#karma2").hide();
        $("#karma1").hide();
        $("#selectKarma").hide();
    }
    else if (this.value === 'Contribution') {
        $("#core2").show();
        $("#core1").hide();
        $("#karma5").hide();
        $("#karma4").hide();
        $("#karma3").hide();
        $("#karma2").hide();
        $("#karma1").hide();
        $("#selectKarma").hide();
    }
    else if (this.value === 'Diligence') {
        $("#core3").show();
        $("#core2").hide();
        $("#core1").hide();
        $("#karma5").hide();
        $("#karma4").hide();
        $("#karma3").hide();
        $("#karma2").hide();
        $("#karma1").hide();
        $("#selectKarma").hide();
    }
    else if (this.value === 'Empathy') {
        $("#core4").show();
        $("#core3").hide();
        $("#core2").hide();
        $("#core1").hide();
        $("#karma5").hide();
        $("#karma4").hide();
        $("#karma3").hide();
        $("#karma2").hide();
        $("#karma1").hide();
        $("#selectKarma").hide();
    }
    else if (this.value === 'Openness to Feedback and Change') {
        $("#core5").show();
        $("#core4").hide();
        $("#core3").hide();
        $("#core2").hide();
        $("#core1").hide();
        $("#karma5").hide();
        $("#karma4").hide();
        $("#karma3").hide();
        $("#karma2").hide();
        $("#karma1").hide();
        $("#selectKarma").hide();
    }
    else if (this.value === 'Responsible Freedom') {
        $("#core6").show();
        $("#core5").hide();
        $("#core4").hide();
        $("#core3").hide();
        $("#core2").hide();
        $("#core1").hide();
        $("#karma5").hide();
        $("#karma4").hide();
        $("#karma3").hide();
        $("#karma2").hide();
        $("#karma1").hide();
        $("#selectKarma").hide();
    }
    else {
        $("#core6").hide();
        $("#core5").hide();
        $("#core4").hide();
        $("#core3").hide();
        $("#core2").hide();
        $("#core1").hide();
        $("#karma5").hide();
        $("#karma4").hide();
        $("#karma3").hide();
        $("#karma2").hide();
        $("#karma1").hide();
        $("#selectKarma").show();
    }
});

        $("input[type='checkbox']").click(function () {
            if (this.checked) {
                var indicative = $(this).attr("data-text");
                var htmlText = '<textarea id="textareaReason" name="reason" style="resize: both;height: 140px;width: 100%">' + indicative + '</textarea>';
                $('div .display').html(htmlText);
                $(".display").removeClass('displayStyle')
            }
        });
        $(".reason-checkbox").on('click', function () {
            comment();
        });

});








//     $(document).ready(function () {
//         $('#search').keyup(function (e) {
//             var filter = $('#search').val();
//             if (filter.length > 0) {
//                 loadTable(filter);
//             } else {
//                 $('tr[id*="tr_"]').remove();
//             }
//         });
//     });
//
//     function loadTable(filter) {
//         var url = "/admin/list" + filter;
//
//         $('#tbody').load(url, function (response, status, xhr) {
//             if (status == "error") {
//                 var msg = "Sorry but there was an error: ";
//                 $("#info").html(msg + xhr.status + " " + xhr.statusText);
//             }
//         });
//
//         return false;
//     }
// });

