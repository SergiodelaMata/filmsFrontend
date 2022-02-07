var formUser = document.getElementById("form-user");
var idUser;
var acceptUsersButton = document.getElementById("accept-user");
var removeUsersButton = document.getElementById("remove-user");
var increaseRolButton = document.getElementById("increase-rol");
var decreaseRolButton = document.getElementById("decrease-rol");
var requestCriticsButtons = document.getElementsByClassName("request-critic");
var editCriticsButtons = document.getElementsByClassName("edit-critic");
var deleteCriticsButtons = document.getElementsByClassName("delete-critic");

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
                alert("El usuario ahora es usuario consultor");
                location.reload();
            }
        }, false);
    }
    if(document.getElementsByClassName("request-critic") !== null)
    {
        requestCriticsButtons = document.getElementsByClassName("request-critic");
        for (var i = 0; i < requestCriticsButtons.length; i++) {
            requestCriticsButtons[i].addEventListener("click", detailCritic, false);
        }
    }
    if(document.getElementsByClassName("edit-critic") !== null)
    {
        editCriticsButtons = document.getElementsByClassName("edit-critic");
        for (var i = 0; i < editCriticsButtons.length; i++) {
            editCriticsButtons[i].addEventListener("click", editCritic, false);
        }
    }
    if(document.getElementsByClassName("delete-critic") !== null)
    {
        deleteCriticsButtons = document.getElementsByClassName("delete-critic");
        for (var i = 0; i < deleteCriticsButtons.length; i++) {
            deleteCriticsButtons[i].addEventListener("click", deleteCritic, false);
        }
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

const detailCritic = async function(){
    const idCritic = this.getAttribute("idcritic");
    window.location.href = "/critics/" + idCritic + "?mode=request";
}

const editCritic = async function(){
    const idCritic = this.getAttribute("idcritic");
    window.location.href = "/critics/edit/" + idCritic + "?mode=edit";
}

const deleteCritic = async function () {
    const idCritic = this.getAttribute("idcritic");
    var actionConfirmed = confirm("¿Estás seguro de que desea eliminar esta crítica?");
    if(actionConfirmed) {
        await deleteRequest("/critics/delete/" + idCritic);
        alert("La crítica ha sido eliminada");
        location.reload();
    }
}
