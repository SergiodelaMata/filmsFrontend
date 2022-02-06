var formUser = document.getElementById("form-user");
var idUser;
var increaseRolButton = document.getElementById("increase-rol");
var decreaseRolButton = document.getElementById("decrease-rol");

window.addEventListener("DOMContentLoaded", function() {
    formUser = document.getElementById("form-user");
    idUser = document.getElementById("idUser").value;

    formUser.addEventListener("submit", async(event) =>{
        event.preventDefault();
        const currentPassword = document.getElementById("actual-password").value;
        const password = document.getElementById("password").value;
        const verifyPassword = document.getElementById("verify-password").value;
        if(currentPassword !== password && password === verifyPassword)
        {
            const formData = formSerializer(event);
            await (putRequest("/users/save", formData));
            //const response = await(putRequest("/users/save",formData));
            alert("Los datos del usuario se han guardado correctamente.");
            window.location.href = "/users/profile/" + idUser;
        }
        else
        {
            alert("Error al introducir la contraseña. Por favor, evita introducir su contraseña anterior y que la contraseña" +
                " sea igual en los dos campos relacionados a ella.");
        }

    });
}, false);