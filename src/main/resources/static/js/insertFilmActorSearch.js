var insertFilmActorButtons;

window.addEventListener("DOMContentLoaded", function() {
    insertFilmActorButtons = document.getElementsByClassName("new-film-actor");
    for (var i = 0; i < insertFilmActorButtons.length; i++) {
        insertFilmActorButtons[i].addEventListener("click", addRelationFilmActor, false);
    }
}, false);

const addRelationFilmActor = async function () {
    const idActor = this.getAttribute("idActor");
    window.location.href = "/actors/film/" + idActor;
}