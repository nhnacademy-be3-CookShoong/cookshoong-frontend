document.addEventListener('DOMContentLoaded', () => {
    const accountInfoUpdateBtn = document.getElementById('update-account-info-btn');
    const orgNickname = document.getElementById('nickname').value;
    const orgEmail = document.getElementById('email').value;
    const orgPhoneNumber = document.getElementById('phoneNumber').value;
    const orgMap = {
        'nickname' : orgNickname,
        'email': orgEmail,
        'phoneNumber': orgPhoneNumber
    }

    const modifiableElements = document.getElementsByClassName('modifiable-element');
    for (const modifiableElement of modifiableElements) {
        modifiableElement.addEventListener('keyup', () => {
            let isDisplayed = false;
            for (const argument of modifiableElements) {
                isDisplayed = argument.value !== orgMap[argument.id];
                if (isDisplayed) {
                    break;
                }
            }
            accountInfoUpdateBtn.style.display = isDisplayed ? 'block' : 'none';
        });
    }
})
