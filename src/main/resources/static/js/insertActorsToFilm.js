async function addActorFilm()
{
    const idFilm = document.getElementById("idFilm").value;
    const selectActors = document.getElementById("selectActors");
    const values = getSelectValues(selectActors);
    for(let i = 0; i < values.length; i++)
    {
        await(putRequest("/films/insert/actor/" + idFilm + "/" + values[i], null));
    }
    alert("Se han insertado correctamente los actores al reparto de la película")
    window.location.href = "/films/edit/" + idFilm + "?mode=edit";
}