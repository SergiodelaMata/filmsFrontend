var formFilm = document.getElementById("form-film");

window.addEventListener("DOMContentLoaded", function() {
    formFilm = document.getElementById("form-film");
    formFilm.addEventListener("submit", async(event) =>{
        event.preventDefault();
        const title = document.getElementById("title").value;
        const year = document.getElementById("year").value;
        const duration = document.getElementById("duration").value;
        const country = document.getElementById("country").value;
        const direction = document.getElementById("direction").value;
        const genres = document.getElementById("genres").value;
        const synopsis = document.getElementById("synopsis").value;
        if(title != null && title !== "" && year != null && year !== ""
            && duration != null && duration !== "" && country != null && country !== ""
            && direction != null && direction !== "" && genres != null && genres !== ""
            && synopsis != null && synopsis !== "")
        {
            const formData = formSerializer(event);
            await(postRequest("/films/save",formData));
            //const response = await(postRequest("/films/save",formData));
            alert("Los datos de la película se han guardado correctamente.");
            window.location.href = "/films";
        }
        else
        {
            alert("Algunos de los campos obligatorios del formulario no se ha rellenado. " +
                "Por favor rellénelos para poder insertarlo.");
        }
    });
}, false);
