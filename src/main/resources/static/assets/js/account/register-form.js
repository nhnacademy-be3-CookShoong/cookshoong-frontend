'use strict';
const LIGHT_GREEN = '#d3f2d4';

setup();
function setup() {
    setCriterionDay();

    const errorMsg = document.getElementById("login-duplicate-msg");
    const $isDuplicated = document.getElementById("loginId")
    const passwordInput = document.getElementById("password");
    const passwordCheckInput = document.getElementById("password-check");

    const needValidationInputs = getAllNeedValidationInputs();
    for (const input of needValidationInputs) {
        input.addEventListener('keyup', () => {
            if (input.id === "loginId") {
                deactivateSignUpBtn()
                setBorderColor(input, 'red');
                $isDuplicated.dataset.checked = 'false';
                errorMsg.style.display = 'none';
            } else if (input.id === "password") {
                checkPasswordEqual(input, passwordCheckInput);
            } else if (input.id === "password-check") {
                checkPasswordEqual(input, passwordInput);
            } else {
                validateRegex(input.id, getRegex(input.id)) ? setBorderColor(input, LIGHT_GREEN) : setBorderColor(input, 'red')
            }
            validateAll() ? activateSignUpBtn() : deactivateSignUpBtn();
        });
    }
}

function checkPasswordEqual(focusedPasswordInput, otherPasswordInput) {
    const focusedPassword = focusedPasswordInput.value;
    const otherPassword = otherPasswordInput.value;
    if (focusedPassword === otherPassword && !isEmpty(focusedPassword) && !isEmpty(otherPassword)
        && checkPasswordPattern(focusedPasswordInput)) {
        setBorderColor(focusedPasswordInput, LIGHT_GREEN);
        setBorderColor(otherPasswordInput, LIGHT_GREEN);
        return true;
    }
    setBorderColor(focusedPasswordInput, 'red');
    return false;
}

function checkPasswordPattern(focusedPasswordInput) {
    const pwd = focusedPasswordInput.value;
    const pwdRegex = getRegex("password");
    const infoMsg = getElementById("password-info-msg");
    infoMsg.style.display = pwdRegex.test(pwd) ? 'none' : 'block';
    return pwdRegex.test(pwd);
}


function checkExists() {
    const loginIdInput = document.getElementById("loginId");
    const loginIdInputStyle = loginIdInput.style;
    const errorMsg = document.getElementById("login-duplicate-msg");
    const successMsg = document.getElementById("login-success-msg");
    const $isDuplicated = document.getElementById("loginId")

    $.ajax({
        type: 'get',
        url: '/proxy/accounts/account/exists',
        headers: {
            'X-LOGIN-ID': loginIdInput.value
        },
        success: function (result) {
            $isDuplicated.dataset.checked = (!result).toString();
            if (result === true) {
                setBorderColor(loginIdInput, 'red');
                errorMsg.style.display = 'block';
            } else if (result === false) {
                setBorderColor(loginIdInput, LIGHT_GREEN);
                successMsg.style.display = 'block';
            } else {
                setBorderColor(loginIdInput, 'red');
                loginIdInputStyle.borderColor = 'red';
                errorMsg.textContent = result;
                errorMsg.style.display = 'block';
                successMsg.style.display = 'none';
            }
        }
    })
    validateAll() ? activateSignUpBtn() : deactivateSignUpBtn();
}

function getAllNeedValidationInputs() {
    return document.getElementsByClassName("need-validation-input");
}

function validateAll() {
    const duplicateCheck = document.getElementById('loginId').dataset.checked === 'true';
    const passwordCheck = findAndCheckIsSamePassword();
    return duplicateCheck && passwordCheck && executeAllRegexTest();
}

function checkAllAndShowSuccess() {
    if (validateAll()) {
        Swal.fire({
            icon: 'success',
            title: '회원가입 성공!',
            showConfirmButton: false,
            timer: 1000
        });
        return true;
    }
    return false;
}

function setCriterionDay() {
    const criterionDay = (function (minimumAge = 14) {
        const THIS_YEAR = (() => {
            return new Date().getFullYear()
        })();
        const DECEMBER = 11;
        return new Date(THIS_YEAR - minimumAge, DECEMBER, 32).toISOString().split("T")[0];
    });
    document.getElementById("birthday").setAttribute("max", criterionDay(15));
}

function findAndCheckIsSamePassword() {
    const passwordInput = document.getElementById("password");
    const passwordCheckInput = document.getElementById("password-check");
    if (passwordInput.value === "" || passwordCheckInput.value === "") {
        return false;
    }
    return passwordInput.value === passwordCheckInput.value;
}

function activateSignUpBtn() {
    const signUpBtn = document.getElementById("sign-up-btn");
    signUpBtn.disabled = false;
}

function deactivateSignUpBtn() {
    const signUpBtn = document.getElementById("sign-up-btn");
    signUpBtn.disabled = true;
}

function isEmpty(value) {
    return value === "";
}

function setBorderColor(element, color) {
    element.style.borderColor = color;
}
