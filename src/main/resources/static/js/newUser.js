var formUser = document.getElementById("form-user");

window.addEventListener("DOMContentLoaded", function() {
    formUser = document.getElementById("form-user");
    formUser.addEventListener("submit", async(event) =>{
        event.preventDefault();
        const formData = formSerializer(event);
        await(postRequest("/users/save",formData));
        //const response = await(postRequest("/users/save",formData));
        alert("Los datos del usuario se han guardado correctamente.");
        window.location.href = "/users";
    });
}, false);
