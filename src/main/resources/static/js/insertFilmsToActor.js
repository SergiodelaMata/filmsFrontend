async function addFilmActor()
{
    const idActor = document.getElementById("idActor").value;
    const selectFilms = document.getElementById("selectFilms");
    const values = getSelectValues(selectFilms);
    for(let i = 0; i < values.length; i++)
    {
        await(putRequest("/actors/insert/film/" + idActor + "/" + values[i], null));
    }
    alert("Se han insertado correctamente las películas al actor")
    window.location.href = "/actors/edit/" + idActor + "?mode=edit";
}