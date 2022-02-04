var formFilm = document.getElementById("form-login");

window.addEventListener("DOMContentLoaded", function() {
    formFilm = document.getElementById("form-login");
    formFilm.addEventListener("submit", async(event) =>{
        event.preventDefault();
        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;
        if(email != null && email !== "" && password != null && password !== "")
        {
            const formData = formSerializer(event);
            const response = await(getRequest("/login",formData));
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
