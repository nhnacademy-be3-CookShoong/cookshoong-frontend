'use strict'
document.addEventListener("DOMContentLoaded", () => {
    const now_utc = Date.now()
    const timeOff = new Date().getTimezoneOffset() * 60000;
    const today = new Date(now_utc-timeOff).toISOString().split("T")[0];
    document.getElementById("birthday").setAttribute("max", today);
})
