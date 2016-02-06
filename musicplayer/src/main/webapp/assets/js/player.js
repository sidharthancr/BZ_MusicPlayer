var firstTime = true;
$(document).ready(function() {
  window.history.forward(-1);
  var userId = getURLParameter("userId");
  var url = window.location.href;
  url = url.split("/musicplayer/assets/views/player.html")[0];
  if (url == undefined || url == '' || userId == undefined || userId == '') {
    // logout();
  } else {
    $('#playerBody').css('visibility', 'visible');
  }
  loadajax("Title");
  $('.listCustom').on('click', 'li', function() {
    $('.list-group-item').removeClass("selected");
    $(this).addClass("selected");
    var title = $(this).children('.title').text();
    var albumName = $(this).children('.album').text();
    var id = $(this).children('#songId').text();
    $('.albumName').text(albumName);
    $('.trackTitle').text(title);
    var albumArt = $(this).children('#albumArt').text();
    $(".albumCover").attr("src", albumArt);
    var src = $(this).children('#songSource').text();
    var audio = $("#playerTag");
    $("#player").attr("src", src);
    $("#player").attr("songId", id);
    $("#anchor").attr("href", src);
    audio[0].pause();
    audio[0].load(); //suspends and restores all audio element
    audio[0].play();

  })
  $.ajax({
    type: 'GET',
    url: url + "/musicplayer/rest/user/getLastPlayedSong",
    headers: {
      "userId": userId
    },
    success: function(msg) {
      var data = JSON.parse(msg).rData;
      var title = data.title;
      var albumName = data.album;
      $('.albumName').text(albumName);
      $('.trackTitle').text(title);
      var albumArt = url + data.albumArt;
      $(".albumCover").attr("src", albumArt);
      var src = url + data.src;
      var audio = $("#playerTag");
      $("#player").attr("src", src);
      $("#player").attr("songId", data.id);
      $("#anchor").attr("href", src);
      audio[0].pause();
      audio[0].load(); //suspends and restores all audio element
      audio[0].currentTime = data.duration;
      // audio[0].play();
      $('.list-group-item').removeClass("selected"); {
        $(".list-group-item").each(function() {
          if ($(this).find('#songSource').text() == $("#player").attr('src')) {
            $(this).addClass("selected");
          }
        });
      }
    },
    error: function() {}
  });
});


function getURLParameter(sParam) {
  var sPageURL = window.location.search.substring(1);
  var sURLVariables = sPageURL.split('&');
  for (var i = 0; i < sURLVariables.length; i++) {
    var sParameterName = sURLVariables[i].split('=');
    if (sParameterName[0] == sParam) {
      return sParameterName[1];
    }
  }
}

function loadajax(Selval) {
  var userId = getURLParameter("userId");
  var url = window.location.href;
  url = url.split("assets/views/player.html")[0];
  $.ajax({
    type: 'GET',
    url: url + "rest/song/getSongsList?sortBy=" + Selval,
    headers: {
      "userId": userId
    },
    success: function(msg) {
      var data = JSON.parse(msg);
      var items = [];
      $.each(data, function(i, item) {
        var selected = "selected";
        for (var j = 0; j < item.length; j++) {
          items.push('<li class="list-group-item "><div id="songId" class="hidden">' + item[j].id + '</div><div id="songSource" class="hidden">' +  item[j].src + '</div><div id="albumArt" class="hidden">' +  item[j].albumArt + '</div><div class="title">' + item[j].title + '</div><div class="album">' + item[j].album + '</div><div class="rating">Rating : ' + item[j].rating + '</div></li>');
          selected = "";
        }
      });
      $('.listCustom').html(items.join(''));
      var audio = $("#playerTag");
      if (firstTime && audio[0].currentTime == 0) {
        $(".list-group-item").first().trigger("click");
        audio[0].pause();
      } else {
        $(".list-group-item").each(function() {
          if ($(this).find('#songSource').text() == $("#player").attr('src')) {
            $(this).addClass("selected");
          }
        });
      }
      firstTime = false;
    },
    error: function() {
      alert("Error while loading songs.");
    }
  });
}


//$(window).bind('beforeunload', function(){
window.onbeforeunload = unload();
//);

function unload() {
  console.log("unload");
  var userId = getURLParameter("userId");
  var url = window.location.href;
  url = url.split("assets/views/player.html")[0];
  var duration = $("#playerTag")[0].currentTime;
  var id = $("#player").attr("songId");
  $.ajax({
    type: 'POST',
    url: url + "rest/user/updateLastPlayedSong",
    headers: {
      "userId": userId
    },
    data: '{' + '"id" : "' + id + '",' + '"duration"  : ' + duration + '}',
    success: function(msg) {
      console.log("user updated");
    }
  });
}


function logout() {
  unload();
  $(document).empty();
  window.location.href = "../../index.html";
  return false;
}
