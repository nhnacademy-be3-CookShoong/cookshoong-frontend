'use strict'
document.addEventListener("DOMContentLoaded", () => {
    const now_utc = Date.now()
    const timeOff = new Date().getTimezoneOffset() * 60000;
    const today = new Date(now_utc - timeOff).toISOString().split("T")[0];
    document.getElementById("birthday").setAttribute("max", today);

    const signUpBtn = document.getElementById("sign-up-btn");
    const loginIdInput = document.getElementById("loginId");
    const errorMsg = document.getElementById("login-duplicate-msg");
    const $isDuplicated = document.getElementById("loginId")
    loginIdInput.addEventListener('keyup', () => {
        signUpBtn.disabled = true;
        $isDuplicated.dataset.checked = 'false';
        loginIdInput.style.borderColor = 'red';
        errorMsg.style.display = 'none';
    })

    const passwordInput = document.getElementById("password");
    const passwordCheckInput = document.getElementById("password-check");
    passwordInput.addEventListener('keyup', () => {
        changeStateProperly(passwordInput, signUpBtn);
    })
    passwordCheckInput.addEventListener('keyup', () => {
        changeStateProperly(passwordCheckInput, signUpBtn);
    })

    const elements = getAllElementsWithoutIdAndPw();
    for (const element of elements) {
        element.addEventListener('keyup', () => {
            signUpBtn.disabled = !validateAll();
        });
    }
})

function changeStateProperly(input, signUpBtn) {
    const passwordInput = document.getElementById("password");
    const passwordCheckInput = document.getElementById("password-check");
    const $isSame = document.getElementById("password")

    if (passwordInput.value !== passwordCheckInput.value) {
        input.style.borderColor = 'red';
        $isSame.dataset.checked = 'false';
    } else {
        $isSame.dataset.checked = 'true';
        signUpBtn.disabled = !validateAll();
        passwordInput.style.borderColor = '#d3f2d4';
        input.style.borderColor = '#d3f2d4';
    }
}




function checkExists() {
    const signUpBtn = document.getElementById("sign-up-btn");
    const loginIdInput = document.getElementById("loginId");
    const loginIdInputStyle = loginIdInput.style;
    const errorMsg = document.getElementById("login-duplicate-msg");
    const $isDuplicated = document.getElementById("loginId")

    errorMsg.style.color = 'red'
    errorMsg.textContent = '이미 존재하는 아이디입니다.';
    $.ajax({
        type: 'get',
        url: '/proxy/accounts/account/exists',
        headers: {
            'X-LOGIN-ID': loginIdInput.value
        },
        success: function (result) {
            $isDuplicated.dataset.checked = (!result).toString();
            if (result === true) {
                loginIdInputStyle.borderColor = 'red';
                errorMsg.style.display = 'block';
            } else {
                loginIdInputStyle.borderColor = '#d3f2d4';
                errorMsg.style.display = 'none';
            }
        }
    })
    signUpBtn.disabled = !validateAll();
}

function hasValueAllElements() {
    const elements = getAllElements();

    for (const element of elements) {
        if (!element.value) {
            return false;
        }
    }
    return true;
}


function getAllElements() {
    return [
        document.getElementById("loginId"),
        document.getElementById("password"),
        document.getElementById("name"),
        document.getElementById("nickname"),
        document.getElementById("email"),
        document.getElementById("birthday"),
        document.getElementById("phoneNumber"),
        document.getElementById("mainPlace"),
        document.getElementById("detailPlace"),
        document.getElementById("alias")
    ];
}

function getAllElementsWithoutIdAndPw() {
    return [
        document.getElementById("name"),
        document.getElementById("nickname"),
        document.getElementById("email"),
        document.getElementById("birthday"),
        document.getElementById("phoneNumber"),
        document.getElementById("mainPlace"),
        document.getElementById("detailPlace"),
        document.getElementById("alias")
    ];
}

function validateAll() {
    const duplicateCheck = document.getElementById('loginId').dataset.checked;
    const passwordCheck = document.getElementById('password').dataset.checked;
    return hasValueAllElements() && duplicateCheck && passwordCheck;
}
