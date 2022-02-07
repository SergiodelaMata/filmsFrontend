var formUser = document.getElementById("form-user");

window.addEventListener("DOMContentLoaded", function() {
    formUser = document.getElementById("form-user");
    formUser.addEventListener("submit", async(event) =>{
        event.preventDefault();
        const password = document.getElementById("password").value;
        const verifyPassword = document.getElementById("verifyPassword").value;
        if(password === verifyPassword)
        {
            const formData = formSerializer(event);
            await(postRequest("/users/save",formData));
            alert("Los datos del usuario se han guardado correctamente.");
            window.location.href = "/users";
        }
        else
        {
            alert("No coinciden las contraseñas. Por favor introduzcalas para que coincidan.")
        }
    });
}, false);
