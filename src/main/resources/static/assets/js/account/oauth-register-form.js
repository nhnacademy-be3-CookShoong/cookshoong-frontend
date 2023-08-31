'use strict';
const LIGHT_GREEN = '#d3f2d4';

document.addEventListener("DOMContentLoaded", () => {
    setCriterionDay();
    hideInputElementsIfExists();
    setInputElementsValidatable();
})

function getAllFormInputElements() {
    return document.getElementsByClassName('oauth-info-element');
}

function setInputElementsValidatable() {
    const inputElements = getAllFormInputElements()
    for (const inputElement of inputElements) {
        inputElement.addEventListener('keyup', () => {
            if (validateOne(inputElement.id)) {
                setBorderColor(inputElement, LIGHT_GREEN);
                if (validateAll()) {
                    activateSignUpBtn();
                }
            } else {
                setBorderColor(inputElement, 'red');
                deactivateSignUpBtn();
            }
        })
    }
}

function validateAll() {
    const needValidationInputElements = document.getElementsByClassName("need-validation-input");
    for (const needValidationInputElement of needValidationInputElements) {
        if (!validateOne(needValidationInputElement.id)) {
            return false;
        }
    }
    return true;
}

function hideInputElementsIfExists() {
    const oauthElementDivs = document.getElementsByClassName("oauth-info-element-div");
    for (const oauthElementDiv of oauthElementDivs) {
        const oauthElement = oauthElementDiv.getElementsByClassName('oauth-info-element').item(0);
        if (oauthElement.value) {
            oauthElementDiv.style.display = 'none';
        }
        if (oauthElement.id === 'birthday') {
            oauthElementDiv.style.display = 'block';
        }
    }
}

function setCriterionDay() {
    const criterionDay = (function (minimumAge = 14) {
        const THIS_YEAR = (() => {return new Date().getFullYear()})();
        const DECEMBER = 11;
        return new Date(THIS_YEAR - minimumAge, DECEMBER, 32).toISOString().split("T")[0];
    });
    document.getElementById("birthday").setAttribute("max", criterionDay(15));
}

function setBorderColor(element, color) {
    element.style.borderColor = color;
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

function activateSignUpBtn() {
    const signUpBtn = document.getElementById("sign-up-btn");
    signUpBtn.disabled = false;
}

function deactivateSignUpBtn() {
    const signUpBtn = document.getElementById("sign-up-btn");
    signUpBtn.disabled = true;
}
