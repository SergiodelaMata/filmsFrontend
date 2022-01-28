var insertActorFilmButtons;

window.addEventListener("DOMContentLoaded", function() {
    insertActorFilmButtons = document.getElementsByClassName("new-actor-film");
    for (var i = 0; i < insertActorFilmButtons.length; i++) {
        insertActorFilmButtons[i].addEventListener("click", addRelationActorFilm, false);
    }
}, false);

const addRelationActorFilm = async function () {
    const idFilm = this.getAttribute("idfilm");
    window.location.href = "/films/actor/" + idFilm;
}