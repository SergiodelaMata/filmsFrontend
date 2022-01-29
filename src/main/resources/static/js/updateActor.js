var formActor = document.getElementById("form-actor");

window.addEventListener("DOMContentLoaded", function() {
    formActor = document.getElementById("form-actor");
    formActor.addEventListener("submit", async(event) =>{
        event.preventDefault();
        const formData = formSerializer(event);
        //console.log(formData);
        await(putRequest("/actors/save",formData));
        //const response = await(putRequest("/films/save",formData));
        alert("Los datos del actor se han guardado correctamente.");
        window.location.href = "/actors";
    });
}, false);
