const host = 'http://' + window.location.host;
let targetId;

$(document).ready(function () {
    const auth = getToken();

    if (auth !== undefined && auth !== '') {
        $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
            jqXHR.setRequestHeader('Authorization', auth);
        });
    } else {
        window.location.href = host + '/api/user/login';
        return;
    }

    // 게시물 수, 팔로우 수, 팔로워 수
    countPost();
    countFollowee();
    countFollower();

    // 프로필 사진


    $.ajax({
        type: 'GET',
        url: `/api/user/info`,
        contentType: 'application/json',
    })
        .done(function (res, status, xhr) {
            const username = res.username;
            const isAdmin = !!res.admin;

            if (!username) {
                window.location.href = '/api/user/login';
                return;
            }

            $('#username').text(username);
            if (isAdmin) {
                $('#admin').text(true);
                showPost(true);
            } else {
                showPost();
            }
        })
        .fail(function (jqXHR, textStatus) {
            logout();
        });

    // id 가 query 인 녀석 위에서 엔터를 누르면 execSearch() 함수를 실행하라는 뜻입니다.
    $('#query').on('keypress', function (e) {
        if (e.key == 'Enter') {
            execSearch();
        }
    });
    $('#close').on('click', function () {
        $('#container').removeClass('active');
    })
    $('#close2').on('click', function () {
        $('#container2').removeClass('active');
    })
    $('.nav div.nav-see').on('click', function () {
        $('div.nav-see').addClass('active');
        $('div.nav-search').removeClass('active');

        $('#see-area').show();
        $('#search-area').hide();
    })
    $('.nav div.nav-search').on('click', function () {
        $('div.nav-see').removeClass('active');
        $('div.nav-search').addClass('active');

        $('#see-area').hide();
        $('#search-area').show();
    })

    $('#see-area').show();
    $('#search-area').hide();
})

$.ajax({
    // ...
    error: function(jqXHR, textStatus, errorThrown) {
        if (jqXHR.status === 401) {
            // 401 Unauthorized 응답을 받았을 때의 처리
            console.log(jqXHR.responseText);  // "Token expired"
            window.location.href = "/login";
        }
    }
});


function countPost() {

    dataSource = `/api/post/count`;

    $.ajax({
        type: 'GET',
        url: dataSource,
        contentType: 'application/json',
        success: function (response) {
            $('#post_count').text(response.count || 0);
        },
        error(error, status, request) {
            if (error.status === 403) {
                $('html').html(error.responseText);
                return;
            }
            logout();
        }
    });
    return "";
}

function countFollowee() {

    dataSource = `/api/user/followee/count`;

    $.ajax({
        type: 'GET',
        url: dataSource,
        contentType: 'application/json',
        success: function (response) {
            $('#followee_count').text(response.count || 0);
        },
        error(error, status, request) {
            if (error.status === 403) {
                $('html').html(error.responseText);
                return;
            }
            logout();
        }
    });
    return "";
}

function countFollower() {

    dataSource = `/api/user/follower/count`;

    $.ajax({
        type: 'GET',
        url: dataSource,
        contentType: 'application/json',
        success: function (response) {
            $('#follower_count').text(response.count || 0);
        },
        error(error, status, request) {
            if (error.status === 403) {
                $('html').html(error.responseText);
                return;
            }
            logout();
        }
    });
    return "";
}

function uploadProfile() {
    $('#profile-input').click();
}

function setProfile(event) {
    let file = event.target.files[0];
    let reader = new FileReader();
    reader.onloadend = e => {
        $('#profile-image').attr('src', e.target.result);
    };
    reader.readAsDataURL(file);

    let formData = new FormData();
    formData.append('file', file);

    $.ajax({
        type: 'POST',
        url: '/api/user/profile',
        data: formData,
        contentType: false,
        processData: false,
        success: function (response) {
            console.log("Image uploaded successfully.");
        },
        error(error, status, request) {
            if (error.status === 403) {
                $('html').html(error.responseText);
                return;
            }
            logout();
        }
    });
}

function logout() {
    // 토큰 삭제
    Cookies.remove('Authorization', {path: '/'});
    window.location.href = host + '/api/user/logout';
}

function getToken() {
    let auth = Cookies.get('Authorization');

    if (auth === undefined) {
        return '';
    }

    return auth;
}
