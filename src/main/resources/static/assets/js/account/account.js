'use strict'
document.addEventListener("DOMContentLoaded", () => {
    const now_utc = Date.now()
    const timeOff = new Date().getTimezoneOffset() * 60000;
    const today = new Date(now_utc-timeOff).toISOString().split("T")[0];
    document.getElementById("birthday").setAttribute("max", today);

    const signUpBtn = document.getElementById("sign-up-btn");
    const loginIdInput = document.getElementById("loginId");
    const errorMsg = document.getElementById("login-duplicate-msg");

    loginIdInput.addEventListener('keyup', () => {
        signUpBtn.disabled = true;
        loginIdInput.style.borderColor = 'red';
        errorMsg.style.display = 'none';
    })

    const passwordInput = document.getElementById("password");
    const passwordCheckInput = document.getElementById("password-check");

    passwordCheckInput.addEventListener('keyup', () => {
        changeStateProperly(passwordInput, passwordCheckInput, signUpBtn);
    })
    passwordCheckInput.addEventListener('focusout', () => {
        changeStateProperly(passwordInput, passwordCheckInput, signUpBtn);
    })
})

function changeStateProperly(passwordInput, passwordCheckInput, signUpBtn) {
    if (passwordInput.value !== passwordCheckInput.value) {
        passwordCheckInput.style.borderColor = 'red';
        signUpBtn.disabled = true;
    } else {
        passwordInput.style.borderColor = '#d3f2d4';
        passwordCheckInput.style.borderColor = '#d3f2d4';
    }
}

function checkExists() {
    const signUpBtn = document.getElementById("sign-up-btn");
    const loginIdInput = document.getElementById("loginId");
    const loginIdInputStyle = loginIdInput.style;
    const errorMsg = document.getElementById("login-duplicate-msg");
    errorMsg.style.color = 'red'
    errorMsg.textContent = '이미 존재하는 아이디입니다.';
    $.ajax({
        type: 'get',
        url: '/proxy/accounts/account/exists',
        headers: {
            'X-LOGIN-ID': loginIdInput.value
        },
        success: function (result) {
            if (result === true) {
                signUpBtn.disabled = true;
                loginIdInputStyle.borderColor = 'red';
                errorMsg.style.display = 'block';
            } else {
                signUpBtn.disabled = false;
                loginIdInputStyle.borderColor = '#d3f2d4';
                errorMsg.style.display = 'none';
            }
        }
    })
}
