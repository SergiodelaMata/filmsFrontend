var formCritic = document.getElementById("form-critic");
var idCritic;

window.addEventListener("DOMContentLoaded", function() {
    formCritic = document.getElementById("form-critic");
    idCritic = document.getElementById("idCritic").value;

    formCritic.addEventListener("submit", async(event) =>{
        event.preventDefault();
            const formData = formSerializer(event);
            await (putRequest("/critics/save", formData));
            alert("Los datos de la crítica se han guardado correctamente.");
            window.location.href = "/critics/edit/" + idCritic + "?mode=edit";
    });
}, false);
