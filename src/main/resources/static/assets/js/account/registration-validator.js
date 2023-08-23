function makeNumberOnly(elementId) {
    replace(elementId, /[^0-9]/g);
}

function makeLetterOnly(elementId) {
    replace(elementId, /[^ㄱ-ㅎ가-힇a-zA-Z]/g);
}

function replace(element, pattern) {
    const input = document.getElementById(`${element}`);
    input.value = input.value.replace(pattern, '');
}

function getRegex(inputId) {
    return regexValidationMap[inputId];
}

function validateRegex(inputTagId, regex) {
    return regex.test(getValue(inputTagId));
}

function getElementById(id) {
    return document.getElementById(id);
}

function getValue(id) {
    return getElementById(id).value;
}

const regexValidationMap = {
    "loginId" : /^[a-zA-Z0-9]{4,30}$/,
    "password" : /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/,
    "password-check" : /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/,
    "name" : /^[a-zA-Z가-힇]{2,30}$/,
    "nickname" : /^[a-zA-Z가-힇]{1,30}$/,
    "email" : /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/,
    "birthday" : /[0-9]{4}-[0-9]{2}-[0-9]{2}/,
    "phoneNumber" : /^010[0-9]{8}$/,
    "mainPlace" : /.+/,
    "detailPlace" : /.+/,
    "alias" : /.+/
}

function executeAllRegexTest() {
    for (let inputId in regexValidationMap) {
        if (getElementById(inputId) === undefined) {
            continue;
        }
        if (!validateRegex(inputId, regexValidationMap[inputId])) {
            return false;
        }
    }
    return true;
}
