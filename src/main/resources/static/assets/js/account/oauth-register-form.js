document.addEventListener("DOMContentLoaded", () => {
    setCriterionDay();
    hideInputElementsIfExists();
    setInputElementsValidatable();
})

function hasValueAllElements() {
    const elements = getAllFormInputElements();
    for (const element of elements) {
        if (!element.value) {
            return false;
        }
    }
    return true;
}

function getAllFormInputElements() {
    return document.getElementsByClassName('oauth-info-element');
}

function setInputElementsValidatable() {
    const inputElements = getAllFormInputElements()
    for (const inputElement of inputElements) {
        console.log("inputElement :" + inputElement)
        inputElement.addEventListener('keyup', () => {
            validate();
        })
    }
}

function validate() {
    const signUpBtn = document.getElementById("sign-up-btn");
    signUpBtn.disabled = !hasValueAllElements();
}


function hideInputElementsIfExists() {
    const oauthElementDivs = document.getElementsByClassName("oauth-info-element-div");
    for (const oauthElementDiv of oauthElementDivs) {
        const oauthElement = oauthElementDiv.getElementsByClassName('oauth-info-element').item(0);
        console.log("oauthElement + ", oauthElement)
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
