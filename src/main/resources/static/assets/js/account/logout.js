const logoutAnchors = document.getElementsByClassName("logout-anchor")

for (const logoutAnchorElement of logoutAnchors) {
    logoutAnchorElement.addEventListener("click", () => {
        logoutAnchorElement.style.pointerEvents = 'none';
    })
}
