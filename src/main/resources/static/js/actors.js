const searchRadioButton = document.getElementsByName("selectSearch");
var valueRadioButton = getValueRadioButton();
var requestActorsButtons;
var editActorsButtons;
var deleteActorsButtons;
var checkActorsDivs;

window.addEventListener("DOMContentLoaded", function() {
    valueRadioButton = getValueRadioButton();
    selectedOption();

    checkActorsDivs = document.getElementsByClassName("check-actor");
    for (var i = 0; i < checkActorsDivs.length; i++) {
        checkActorsDivs[i].addEventListener("click", checkActorDiv, false);
    }

    requestActorsButtons = document.getElementsByClassName("request-actor");
    for (var i = 0; i < requestActorsButtons.length; i++) {
        requestActorsButtons[i].addEventListener("click", detailActor, false);
    }
    editActorsButtons = document.getElementsByClassName("edit-actor");
    for (var i = 0; i < editActorsButtons.length; i++) {
        editActorsButtons[i].addEventListener("click", editActor, false);
    }
    deleteActorsButtons = document.getElementsByClassName("delete-actor");
    for (var i = 0; i < deleteActorsButtons.length; i++) {
        deleteActorsButtons[i].addEventListener("click", deleteActor, false);
    }
}, false);

const checkActorDiv = async function(){
    const idActor = this.getAttribute("idActor");
    window.location.href = "/actors/" + idActor + "?mode=request";
}

const detailActor = async function(){
    const idActor = this.getAttribute("idActor");
    window.location.href = "/actors/" + idActor + "?mode=request";
}

const editActor = async function(){
    const idActor = this.getAttribute("idActor");
    window.location.href = "/actors/edit/" + idActor + "?mode=edit";
}

const deleteActor = async function () {
    const idActor = this.getAttribute("idActor");
    var actionConfirmed = confirm("¿Estás seguro de que desea eliminar a este actor?");
    if(actionConfirmed) {
        await deleteRequest("/actors/delete/" + idActor);
        //const response = await deleteRequest("/actors/delete/" + idActor);
        alert("El actor ha sido eliminado");
        location.reload();
    }
};


function getValueRadioButton(){
    var selectedOption = "name";

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
    document.getElementById("searchTitleFilm").value = "";
    document.getElementById("searchName").value = "";
    if(selectedOption === "name")
    {
        document.getElementById("searchingByName").classList.remove("none");
        document.getElementById("searchingByName").classList.add("visible");
        document.getElementById("searchingByTitleFilm").classList.remove("visible");
        document.getElementById("searchingByTitleFilm").classList.add("none");
        valueRadioButton = "name";
    }
    else if(selectedOption === "filmTitle")
    {
        document.getElementById("searchingByName").classList.remove("visible");
        document.getElementById("searchingByName").classList.add("none");
        document.getElementById("searchingByTitleFilm").classList.remove("none");
        document.getElementById("searchingByTitleFilm").classList.add("visible");
        valueRadioButton = "filmTitle";
    }
}

function searchActors(){
    if(valueRadioButton === "name") //name
    {
        window.location.href = "/actors/name?name="+ document.getElementById("searchName").value
            +"&typeSearch="+valueRadioButton;
    }
    else if(valueRadioButton === "filmTitle") //filmTitle
    {
        window.location.href = "/actors/filmTitle?filmTitle="+ document.getElementById("searchTitleFilm").value
            +"&typeSearch="+valueRadioButton;
    }
}