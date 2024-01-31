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


    countPost();

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


function countPost() {

    dataSource = `/api/post/count`;

    $.ajax({
        type: 'GET',
        url: dataSource,
        contentType: 'application/json',
        success: function (response) {
            $('#post_count').text(response.count);
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
function showPost(isAdmin = false) {
    /**
     * 관심상품 목록: #product-container
     * 검색결과 목록: #search-result-box
     * 관심상품 HTML 만드는 함수: addProductItem
     */

    let dataSource = null;

    // admin 계정
    if (isAdmin) {
        dataSource = `/api/admin/posts`;
    } else {
        dataSource = `/api/posts`;
    }

    $.ajax({
        type: 'GET',
        url: dataSource,
        contentType: 'application/json',
        success: function (response) {
            $('#post-container').empty();
            for (let i = 0; i < response.length; i++) {
                let post = response[i];
                let tempHtml = addPostItem(post);
                $('#post-container').append(tempHtml);
            }
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

function addPostItem(post) {
    console.log(post)
    return `<div class="post-card">
                <div onclick="window.location.href='${post.link}'">
                    <div class="card-header">
                        <img src="${post.image}"
                             alt="">
                    </div>
                    <div class="card-body">
                        <div class="title">
                            ${post.title}
                        </div>
                        <div class="lprice">
                            <span>${numberWithCommas(post.lprice)}</span>원
                        </div>
                        <div class="isgood ${post.lprice > post.myprice ? 'none' : ''}">
                            최저가
                        </div>
                    </div>
                </div>
            </div>`;
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
