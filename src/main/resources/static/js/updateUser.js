var formUser = document.getElementById("form-user");
var idUser;
var acceptUsersButton = document.getElementById("accept-user");
var removeUsersButton = document.getElementById("remove-user");
var increaseRolButton = document.getElementById("increase-rol");
var decreaseRolButton = document.getElementById("decrease-rol");

window.addEventListener("DOMContentLoaded", function() {
    formUser = document.getElementById("form-user");
    idUser = document.getElementById("idUser").value;
    if(document.getElementById("accept-user") !== null)
    {
        acceptUsersButton = document.getElementById("accept-user");
        acceptUsersButton.addEventListener("click", async function(){
            var actionConfirmed = confirm("¿Estás seguro de que desea aceptar a este usuario?");
            if(actionConfirmed) {
                await putRequest("/users/acceptRequest/" + idUser);
                //const response = await putRequest("/users/acceptRequest/" + idUser);
                alert("El usuario ha sido aceptado");
                location.reload();
            }
        }, false);
    }
    if(document.getElementById("remove-user") !== null)
    {
        removeUsersButton = document.getElementById("remove-user");
        removeUsersButton.addEventListener("click", async function(){
            var actionConfirmed = confirm("¿Estás seguro de que desea eliminar a este usuario?");
            if(actionConfirmed) {
                await deleteRequest("/users/delete/" + idUser);
                //const response = await deleteRequest("/users/delete/" + idUser);
                alert("El usuario ha sido eliminado");
                window.location.href = "/users";
            }
        }, false);
    }
    if(document.getElementById("increase-rol") !== null)
    {
        increaseRolButton = document.getElementById("increase-rol");
        increaseRolButton.addEventListener("click", async function(){
            var actionConfirmed = confirm("¿Estás seguro de que desea hacer administrador a este usuario?");
            if(actionConfirmed) {
                await putRequest("/users/increaseRol/" + idUser);
                //const response = await putRequest("/users/increaseRol/" + idUser);
                alert("El usuario ahora es administrador");
                location.reload();
            }
        }, false);
    }
    if(document.getElementById("decrease-rol") !== null)
    {
        decreaseRolButton = document.getElementById("decrease-rol");
        decreaseRolButton.addEventListener("click", async function(){
            var actionConfirmed = confirm("¿Estás seguro de que desea bajar el rol a este usuario?");
            if(actionConfirmed) {
                await putRequest("/users/decreaseRol/" + idUser);
                //const response = await putRequest("/users/decreaseRol/" + idUser);
                alert("El usuario ahora es usuario consultor");
                location.reload();
            }
        }, false);
    }

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
            window.location.href = "/users/" + idUser + "?mode=edit";
        }
        else
        {
            alert("Error al introducir la contraseña. Por favor, evita introducir su contraseña anterior y que la contraseña" +
                " sea igual en los dos campos relacionados a ella.");
        }

    });
}, false);