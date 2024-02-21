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
        window.location.href = host + '/user/login';
        return;
    }
    goToFeed();

})



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