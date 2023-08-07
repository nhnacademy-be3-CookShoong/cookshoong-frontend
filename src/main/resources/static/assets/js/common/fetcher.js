function sendDataWithCsrfToken(url, redirectUrl, data, method) {
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const headers = {
        'Content-Type': 'application/json'
    };
    headers[csrfHeader] = csrfToken;

    fetch(url, {
        method: method,
        headers: headers,
        body: JSON.stringify(data)
    })
        .then(async response => {
            if (response.ok) {
                if (window.location.href !== null) {
                    window.location.href = redirectUrl;
                }
                return response;
            }
        })
}

function justSendCsrfToken(url, redirectUrl) {
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const headers = {
        'Content-Type': 'application/json'
    };
    headers[csrfHeader] = csrfToken;
    console.log("url " + url);
    fetch(url, {
        method: 'POST',
        headers: headers
    })
        .then(async response => {
            if (response.ok) {
                window.location.href = redirectUrl;
            }
        })
}
