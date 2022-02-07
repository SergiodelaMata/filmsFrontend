var formCritic = document.getElementById("form-critic");

window.addEventListener("DOMContentLoaded", function() {
    formCritic = document.getElementById("form-critic");
    const selectFilm = document.getElementById("selectFilms");

    formCritic.addEventListener("submit", async(event) =>{
        event.preventDefault();
        const formData = formSerializer(event);
        await(postRequest("/critics/save",formData));
        //const response = await(postRequest("/critics/save",formData));
        alert("Los datos de la crítica se han guardado correctamente.");
        window.location.href = "/films";
    });
}, false);
