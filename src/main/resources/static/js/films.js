const searchRadioButton = document.getElementsByName("selectSearch");
var valueRadioButton = getValueRadioButton();
var requestFilmsButtons;
var editFilmsButtons;
var deleteFilmsButtons;
var checkFilmsDivs;

window.addEventListener("DOMContentLoaded", function() {
    valueRadioButton = getValueRadioButton();
    selectedOption();

    checkFilmsDivs = document.getElementsByClassName("check-film");
    for (var i = 0; i < checkFilmsDivs.length; i++) {
        checkFilmsDivs[i].addEventListener("click", checkFilmDiv, false);
    }

    requestFilmsButtons = document.getElementsByClassName("request-film");
    for (var i = 0; i < requestFilmsButtons.length; i++) {
        requestFilmsButtons[i].addEventListener("click", detailFilm, false);
    }
    editFilmsButtons = document.getElementsByClassName("edit-film");
    for (var i = 0; i < editFilmsButtons.length; i++) {
        editFilmsButtons[i].addEventListener("click", editFilm, false);
    }
    deleteFilmsButtons = document.getElementsByClassName("delete-film");
    for (var i = 0; i < deleteFilmsButtons.length; i++) {
        deleteFilmsButtons[i].addEventListener("click", deleteFilm, false);
    }
}, false);

const checkFilmDiv = async function(){
    const idFilm = this.getAttribute("idfilm");
    window.location.href = "/films/" + idFilm + "?mode=request";
}

const detailFilm = async function(){
    const idFilm = this.getAttribute("idfilm");
    window.location.href = "/films/" + idFilm + "?mode=request";
}

const editFilm = async function(){
    const idFilm = this.getAttribute("idfilm");
    window.location.href = "/films/edit/" + idFilm + "?mode=edit";
}

const deleteFilm = async function () {
    const idFilm = this.getAttribute("idfilm");
    var actionConfirmed = confirm("¿Estás seguro de que desea eliminar esta película?");
    if(actionConfirmed) {
        await deleteRequest("/films/delete/" + idFilm);
        alert("La película ha sido eliminado");
        location.reload();
    }
};


function getValueRadioButton(){
    var selectedOption = "title";

    for(var i = 0; i < searchRadioButton.length; i++)
    {
        if(searchRadioButton[i].checked)
        {
            selectedOption = searchRadioButton[i].value;
        }
    }
    return selectedOption;
}

function selectedOption(){
    var selectedOption = getValueRadioButton();
    console.log(selectedOption);
    document.getElementById("searchTitle").value = "";
    document.getElementById("searchYearInit").value = "";
    document.getElementById("searchYearEnd").value = "";
    document.getElementById("searchCountry").value = "";
    document.getElementById("searchDirection").value = "";
    document.getElementById("searchGenres").value = "";
    document.getElementById("searchActorName").value = "";
    if(selectedOption === "title")
    {
        document.getElementById("searchingByTitle").classList.remove("none");
        document.getElementById("searchingByTitle").classList.add("visible");
        document.getElementById("searchingByYear").classList.remove("visible");
        document.getElementById("searchingByYear").classList.add("none");
        document.getElementById("searchingByCountry").classList.remove("visible");
        document.getElementById("searchingByCountry").classList.add("none");
        document.getElementById("searchingByDirection").classList.remove("visible");
        document.getElementById("searchingByDirection").classList.add("none");
        document.getElementById("searchingByGenres").classList.remove("visible");
        document.getElementById("searchingByGenres").classList.add("none");
        document.getElementById("searchingByActorName").classList.remove("visible");
        document.getElementById("searchingByActorName").classList.add("none");
        valueRadioButton = "title";
    }
    else if(selectedOption === "year")
    {
        document.getElementById("searchingByTitle").classList.remove("visible");
        document.getElementById("searchingByTitle").classList.add("none");
        document.getElementById("searchingByYear").classList.remove("none");
        document.getElementById("searchingByYear").classList.add("visible");
        document.getElementById("searchingByCountry").classList.remove("visible");
        document.getElementById("searchingByCountry").classList.add("none");
        document.getElementById("searchingByDirection").classList.remove("visible");
        document.getElementById("searchingByDirection").classList.add("none");
        document.getElementById("searchingByGenres").classList.remove("visible");
        document.getElementById("searchingByGenres").classList.add("none");
        document.getElementById("searchingByActorName").classList.remove("visible");
        document.getElementById("searchingByActorName").classList.add("none");
        valueRadioButton = "year";
    }
    else if(selectedOption === "country")
    {
        document.getElementById("searchingByTitle").classList.remove("visible");
        document.getElementById("searchingByTitle").classList.add("none");
        document.getElementById("searchingByYear").classList.remove("visible");
        document.getElementById("searchingByYear").classList.add("none");
        document.getElementById("searchingByCountry").classList.remove("none");
        document.getElementById("searchingByCountry").classList.add("visible");
        document.getElementById("searchingByDirection").classList.remove("visible");
        document.getElementById("searchingByDirection").classList.add("none");
        document.getElementById("searchingByGenres").classList.remove("visible");
        document.getElementById("searchingByGenres").classList.add("none");
        document.getElementById("searchingByActorName").classList.remove("visible");
        document.getElementById("searchingByActorName").classList.add("none");
        valueRadioButton = "country";
    }
    else if(selectedOption === "direction")
    {
        document.getElementById("searchingByTitle").classList.remove("visible");
        document.getElementById("searchingByTitle").classList.add("none");
        document.getElementById("searchingByYear").classList.remove("visible");
        document.getElementById("searchingByYear").classList.add("none");
        document.getElementById("searchingByCountry").classList.remove("visible");
        document.getElementById("searchingByCountry").classList.add("none");
        document.getElementById("searchingByDirection").classList.remove("none");
        document.getElementById("searchingByDirection").classList.add("visible");
        document.getElementById("searchingByGenres").classList.remove("visible");
        document.getElementById("searchingByGenres").classList.add("none");
        document.getElementById("searchingByActorName").classList.remove("visible");
        document.getElementById("searchingByActorName").classList.add("none");
        valueRadioButton = "direction";
    }
    else if(selectedOption === "genres")
    {
        document.getElementById("searchingByTitle").classList.remove("visible");
        document.getElementById("searchingByTitle").classList.add("none");
        document.getElementById("searchingByYear").classList.remove("visible");
        document.getElementById("searchingByYear").classList.add("none");
        document.getElementById("searchingByCountry").classList.remove("visible");
        document.getElementById("searchingByCountry").classList.add("none");
        document.getElementById("searchingByDirection").classList.remove("visible");
        document.getElementById("searchingByDirection").classList.add("none");
        document.getElementById("searchingByGenres").classList.remove("none");
        document.getElementById("searchingByGenres").classList.add("visible");
        document.getElementById("searchingByActorName").classList.remove("visible");
        document.getElementById("searchingByActorName").classList.add("none");
        valueRadioButton = "genres";
    }
    else if(selectedOption === "actorName")
    {
        document.getElementById("searchingByTitle").classList.remove("visible");
        document.getElementById("searchingByTitle").classList.add("none");
        document.getElementById("searchingByYear").classList.remove("visible");
        document.getElementById("searchingByYear").classList.add("none");
        document.getElementById("searchingByCountry").classList.remove("visible");
        document.getElementById("searchingByCountry").classList.add("none");
        document.getElementById("searchingByDirection").classList.remove("visible");
        document.getElementById("searchingByDirection").classList.add("none");
        document.getElementById("searchingByGenres").classList.remove("visible");
        document.getElementById("searchingByGenres").classList.add("none");
        document.getElementById("searchingByActorName").classList.remove("none");
        document.getElementById("searchingByActorName").classList.add("visible");
        valueRadioButton = "actorName";
    }
}

function searchFilms(){
    if(valueRadioButton === "title") //Title
    {
        window.location.href = "/films/title?title="+ document.getElementById("searchTitle").value
            +"&typeSearch="+valueRadioButton;
    }
    else if(valueRadioButton === "year") //Year
    {
        if(document.getElementById("searchYearInit").value === "" &&
            document.getElementById("searchYearEnd").value === "")
        {
            alert("Por favor, introduzca las dos fechas para poder realizar la búsqueda sobre un intervalo.");
        }
        else
        {
            window.location.href = "/films/year?yearInit="+ document.getElementById("searchYearInit").value
                +"&yearEnd=" + document.getElementById("searchYearEnd").value
                +"&typeSearch="+valueRadioButton;
        }
    }
    else if(valueRadioButton === "country") //Country
    {
        window.location.href = "/films/country?country="+ document.getElementById("searchCountry").value
            +"&typeSearch="+valueRadioButton;
    }
    else if(valueRadioButton === "direction") //Direction
    {
        window.location.href = "/films/direction?direction="+ document.getElementById("searchDirection").value
            +"&typeSearch="+valueRadioButton;
    }
    else if(valueRadioButton === "genres") //Genres
    {
        window.location.href = "/films/genres?genres="+ document.getElementById("searchGenres").value
            +"&typeSearch="+valueRadioButton;
    }
    else if(valueRadioButton === "actorName") //actorName
    {
        window.location.href = "/films/actorName?actorName="+ document.getElementById("searchActorName").value
            +"&typeSearch="+valueRadioButton;
    }
}