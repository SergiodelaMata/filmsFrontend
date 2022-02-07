var formUser = document.getElementById("form-user");
var idUser;
var requestCriticsButtons = document.getElementsByClassName("request-critic");
var editCriticsButtons = document.getElementsByClassName("edit-critic");
var deleteCriticsButtons = document.getElementsByClassName("delete-critic");

window.addEventListener("DOMContentLoaded", function() {
    formUser = document.getElementById("form-user");
    idUser = document.getElementById("idUser").value;

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
            window.location.href = "/users/profile/" + idUser;
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
