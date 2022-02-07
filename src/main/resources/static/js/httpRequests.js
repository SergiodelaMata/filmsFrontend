async function getRequest(url) {
    const response = await fetch(url, {
        method: "GET",
    });
    return await response.json();
}

async function postRequest(url, data) {
    const response = await fetch(url, {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
            "Content-type": "application/json; charset=UTF-8",
        },
    });
}

async function putRequest(url, data = null) {
    const response = await fetch(url, {
        method: "PUT",
        body: JSON.stringify(data ? data : {}),
        headers: {
            "Content-type": "application/json; charset=UTF-8",
        },
    });
}

async function deleteRequest(url) {
    const response = await fetch(url, {
        method: "DELETE",
    });
}
