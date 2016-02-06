jQuery(document).ready(function() {
  window.history.forward(-1);
  /*
      Fullscreen background
  */
  $.backstretch("assets/img/backgrounds/1.jpg");

  /*
      Form validation
  */
  $('.login-form input[type="text"], .login-form input[type="password"], .login-form textarea').on('focus', function() {
    $(this).removeClass('input-error');
  });

  $('.login-form').on('submit', function(e) {
    var url = window.location.href;
    url = url.replace("index.html", "");
    var submit = false;
    var uname = $('#form-username').val();
    var pass = $('#form-password').val();
    e.preventDefault();
    if (uname == '') {
      $('#form-username').addClass('input-error');
    } else if (pass == '') {
      $('#form-password').addClass('input-error');
    } else {
      $.ajax({
        type: 'POST',
        url: url + "rest/user/login",
        headers: {
          "userName": uname,
          "password": pass
        },
        success: function(msg) {
          var data = JSON.parse(msg);
          var res = data.rData.userId;
          window.location.href = url + "assets/views/player.html?userId=" + res;
          //   return false;
        },
        error: function() {
          alert("Invalid Credentials");
        }
      });
    }
  });
});
