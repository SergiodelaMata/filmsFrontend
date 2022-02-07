var formCritic = document.getElementById("form-critic");

window.addEventListener("DOMContentLoaded", function() {
    formCritic = document.getElementById("form-critic");
    const selectFilm = document.getElementById("selectFilms");
    const value = getSelectValues(selectFilm);

    formCritic.addEventListener("submit", async(event) =>{
        event.preventDefault();
        const formData = formSerializer(event);
        await(postRequest("/critics/save/" + value[0],formData));
        alert("Los datos de la crítica se han guardado correctamente.");
        window.location.href = "/critics";
    });
}, false);
