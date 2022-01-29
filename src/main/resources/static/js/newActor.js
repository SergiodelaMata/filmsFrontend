var formActor = document.getElementById("form-actor");

window.addEventListener("DOMContentLoaded", function() {
    formActor = document.getElementById("form-actor");
    formActor.addEventListener("submit", async(event) =>{
        event.preventDefault();
        const name = document.getElementById("name").value;
        const birthdate = document.getElementById("birthDate").value;
        const countryBirth = document.getElementById("countryBirth").value;
        if(name != null && name !== "" && birthdate != null && birthdate !== ""
            && countryBirth != null && countryBirth !== "")
        {
            const formData = formSerializer(event);
            await(postRequest("/actors/save",formData));
            //const response = await(postRequest("/actors/save",formData));
            alert("Los datos del actor se han guardado correctamente.");
            window.location.href = "/actors";
        }
        else
        {
            alert("Algunos de los campos obligatorios del formulario no se ha rellenado. " +
                "Por favor rellénelos para poder insertarlo.");
        }
    });
}, false);
