var formFilm = document.getElementById("form-film");
var idFilm = document.getElementById("idFilm");
var criticsButton = document.getElementById("criticsButton");
var requestCriticsButtons;
var editCriticsButtons;
var deleteCriticsButtons;
var checkActorsDivs;

window.addEventListener("DOMContentLoaded", function() {
    formFilm = document.getElementById("form-film");
    idFilm = document.getElementById("idFilm").value;

    if(document.getElementById("criticsButton") !== null)
    {
        criticsButton = document.getElementById("criticsButton");
        criticsButton.addEventListener("click", async function() {
            window.location.href = "/critics/new/" + idFilm;
        });
    }

    checkActorsDivs = document.getElementsByClassName("check-actor");
    for (var i = 0; i < checkActorsDivs.length; i++) {
        checkActorsDivs[i].addEventListener("click", checkActorDiv, false);
    }

    requestCriticsButtons = document.getElementsByClassName("request-critic");
    for (var i = 0; i < requestCriticsButtons.length; i++) {
        requestCriticsButtons[i].addEventListener("click", detailCritic, false);
    }

    editCriticsButtons = document.getElementsByClassName("edit-critic");
    for (var i = 0; i < editCriticsButtons.length; i++) {
        editCriticsButtons[i].addEventListener("click", editCritic, false);
    }

    deleteCriticsButtons = document.getElementsByClassName("delete-critic");
    for (var i = 0; i < deleteCriticsButtons.length; i++) {
        deleteCriticsButtons[i].addEventListener("click", deleteCritic, false);
    }

    formFilm.addEventListener("submit", async(event) =>{
        event.preventDefault();
        const formData = formSerializer(event);
        await (putRequest("/films/save", formData));
        alert("Los datos de la película se han guardado correctamente.");
        window.location.href = "/films";
    });

}, false);

const checkActorDiv = async function(){
    const idActor = this.getAttribute("idActor");
    window.location.href = "/actors/" + idActor + "?mode=request";
}

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
};
