function insertFilmActor()
{
    const insertFilmActorButton = document.getElementById("new-film-actor");
    const idActor = insertFilmActorButton.getAttribute("idactor");
    window.location.href = "/actors/film/" + idActor;
}