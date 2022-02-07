var formActor = document.getElementById("form-actor");
var checkFilmsDivs;

window.addEventListener("DOMContentLoaded", function() {
    formActor = document.getElementById("form-actor");

    checkFilmsDivs = document.getElementsByClassName("check-film");
    for (var i = 0; i < checkFilmsDivs.length; i++) {
        checkFilmsDivs[i].addEventListener("click", checkFilmDiv, false);
    }

    formActor.addEventListener("submit", async(event) =>{
        event.preventDefault();
        const formData = formSerializer(event);
        await (putRequest("/actors/save", formData));
        alert("Los datos del actor se han guardado correctamente.");
        window.location.href = "/actors";
    });
}, false);

const checkFilmDiv = async function(){
    const idFilm = this.getAttribute("idfilm");
    window.location.href = "/films/" + idFilm + "?mode=request";
}
