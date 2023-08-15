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

