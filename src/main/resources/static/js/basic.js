const host = 'http://' + window.location.host;
let targetId;

// 주로 DOM 요소에 이벤트 핸들러를 등록하거나,
// 페이지 로딩이 완료된 직후에 실행해야 하는 초기화 코드를 작성하는 데 사용

$(document).ready(function () {
    const auth = getToken();

    if (auth !== undefined && auth !== '') {
        $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
            jqXHR.setRequestHeader('Authorization', auth);
        });
    } else {
        window.location.href = host + '/api/page/user/login';
        return;
    }

    getUserInfo();
    countPost();
    countFollowee();
    countFollower();
    getProfile();
    loadPosts();
    goToFeed();
    getIntroduceMyself();

    // id 가 query 인 녀석 위에서 엔터를 누르면 execSearch() 함수를 실행하라는 뜻입니다.
    // $('#query').on('keypress', function (e) {
    //     if (e.key == 'Enter') {
    //         execSearch();
    //     }
    // });
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


function getUserInfo() {
    $.ajax({
        type: 'GET',
        url: `/api/user/info`,
        contentType: 'application/json',
    })
        .done(function (res, status, xhr) {
            const username = res.username;
            const isAdmin = !!res.admin;

            if (!username) {
                window.location.href = '/api/page/user/login';
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
}

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

function getProfile() {
    $.ajax({
        type: 'GET',
        url: '/api/user/profile',
        success: function (response) {
            let imageData = response.profile;
            $('#profile-image').attr('src', 'data:image/png;base64,' + imageData);
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

function goToUserInfo() {
    // 프로필 수정 버튼
    $("#user-info").click(function(){
        window.location.href ='/api/page/user/info';
    });
}
// 게시물 업로드 하기

function uploadPost() {
    var formData = new FormData();
    formData.append('title', $('#post-title').val());
    formData.append('content', $('#post-content').val());

    $.ajax({
        type: 'POST',
        url: '/api/posts',
        data: formData,
        contentType: false,
        processData: false,
        success: function (response) {
            console.log("Post uploaded successfully.");
            // 게시글 업로드에 성공한 후에는, GET 요청을 통해 게시글 목록을 다시 불러옵니다.
            $('#postModal').modal('hide');
            loadPosts();
        },
        error: function(error, status, request) {
            if (error.status === 403) {
                $('html').html(error.responseText);
                return;
            }
            logout();
        }
    });
}


function loadPosts() {
    $.ajax({
        type: 'GET',
        url: '/api/posts',
        success: function (response) {
            // 받아온 게시글 데이터를 화면에 표시합니다.
            $('.body').empty();  // 기존에 표시된 게시글을 모두 지웁니다.
            $.each(response, function(index, post) {
                $('.body').prepend(`
                    <div class="post" style="border: 1px solid black; padding: 10px; width: 200px; height: 200px; overflow: auto;">
                        <p>${post.content}</p>
                    </div>
                `);
            });
        },
        error: function(error, status, request) {
            if (error.status === 403) {
                $('html').html(error.responseText);
                return;
            }
            logout();
        }
    });
}

function goToFeed() {
    // 내 게시판 보기 버튼
    $("#user-ok").click(function(){
        window.location.href = host + '/api/page/feed';
    });
}
function updateIntroduceMyself() {
    var introduceMyself = document.getElementById('introduce-myself').value;

    $.ajax({
        type: 'POST',
        url: '/api/user/info/introduce',
        data: {
            introduce: introduceMyself
        },
        success: function(response) {
            console.log('Introduce myself updated successfully.');
        },
        error: function(error) {
            console.log('Failed to update introduce myself:', error);
        }
    });
}
function getIntroduceMyself() {
    $.ajax({
        type: 'GET',
        url: `/api/user/info/introduce`,
        contentType: 'application/json',
    })
        .done(function (res) {
            const introduce = res.introduce;

            $('#user-introduce').text(introduce);

        })
        .fail(function (jqXHR, textStatus) {
            logout();
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
