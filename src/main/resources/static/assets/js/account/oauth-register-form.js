document.addEventListener("DOMContentLoaded", () => {
    const now_utc = Date.now()
    const timeOff = new Date().getTimezoneOffset() * 60000;
    const today = new Date(now_utc - timeOff).toISOString().split("T")[0];
    document.getElementById("birthday").setAttribute("max", today);


    const oauthElementDivs = document.getElementsByClassName("oauth-info-element-div");

    for (const oauthElementDiv of oauthElementDivs) {
        const oauthElement = oauthElementDiv.getElementsByClassName('oauth-info-element').item(0);
        if (oauthElement.value) {
            console.log(oauthElement + ' : ' + oauthElement.value)
            oauthElementDiv.style.display = 'none';
        }
        if (oauthElement.id === 'birthday') {
            oauthElementDiv.style.display = 'block';
        }
    }
})
