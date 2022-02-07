var formFilm = document.getElementById("form-film");
var idFilm = document.getElementById("idFilm");
var criticsButton = document.getElementById("criticsButton");


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


    formFilm.addEventListener("submit", async(event) =>{
        event.preventDefault();
        const formData = formSerializer(event);
        await (putRequest("/films/save", formData));
        //const response = await(putRequest("/films/save",formData));
        alert("Los datos de la película se han guardado correctamente.");
        window.location.href = "/films";
    });


}, false);
