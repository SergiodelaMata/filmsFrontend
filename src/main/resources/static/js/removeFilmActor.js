var deleteActorFilmButtons;

window.addEventListener("DOMContentLoaded", function() {
    deleteActorFilmButtons = document.getElementsByClassName("delete-relation");
    for (var i = 0; i < deleteActorFilmButtons.length; i++) {
        deleteActorFilmButtons[i].addEventListener("click", deleteRelation, false);
    }
}, false);

const deleteRelation = async function () {
    const idActor = this.getAttribute("idactor");
    const idFilm = this.getAttribute("idfilm");
    var actionConfirmed = confirm("¿Estás seguro de que desea eliminar a esta película del actor?");
    if(actionConfirmed) {
        await deleteRequest("/actors/delete/film/" + idActor + "/" + idFilm);
        //const response = await deleteRequest("/actors/delete/film/" + idActor + "/" + idFilm);
        alert("Se ha eliminado la película del actor");
        location.reload();
    }
};
