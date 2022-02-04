var formFilm = document.getElementById("form-film");

window.addEventListener("DOMContentLoaded", function() {
    formFilm = document.getElementById("form-film");
    formFilm.addEventListener("submit", async(event) =>{
        event.preventDefault();
        const formData = formSerializer(event);
        await (putRequest("/films/save", formData));
        //const response = await(putRequest("/films/save",formData));
        alert("Los datos de la película se han guardado correctamente.");
        window.location.href = "/films";
    });
}, false);
