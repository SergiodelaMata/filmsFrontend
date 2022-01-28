var deleteActorFilmButtons;

window.addEventListener("DOMContentLoaded", function() {
    deleteActorFilmButtons = document.getElementsByClassName("delete-relation");
    for (var i = 0; i < deleteActorFilmButtons.length; i++) {
        deleteActorFilmButtons[i].addEventListener("click", deleteRelation, false);
    }
}, false);

const deleteRelation = async function () {
    const idFilm = this.getAttribute("idfilm");
    const idActor = this.getAttribute("idactor");
    var actionConfirmed = confirm("¿Estás seguro de que desea eliminar a este actor del reparto de esta película?");
    if(actionConfirmed) {
        await deleteRequest("/films/delete/actor/" + idFilm + "/" + idActor);
        //const response = await deleteRequest("/films/delete/actor/" + idFilm + "/" + idActor);
        alert("Se ha eliminado el actor del reparto de la película");
        location.reload();
    }
};
