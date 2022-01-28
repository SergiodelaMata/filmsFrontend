function insertActorFilm()
{
    const insertActorFilmButton = document.getElementById("new-actor-film");
    const idFilm = insertActorFilmButton.getAttribute("idfilm");
    window.location.href = "/films/actor/" + idFilm;
}