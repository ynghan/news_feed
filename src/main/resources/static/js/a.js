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
})

$.ajax({
    error: function(jqXHR, textStatus, errorThrown) {
        if (jqXHR.status === 401) {
            // 401 Unauthorized 응답을 받았을 때의 처리
            console.log(jqXHR.responseText);  // "Token expired"
            window.location.href = "/login";
        }
    }
});


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