const searchRadioButton = document.getElementsByName("selectSearch");
var valueRadioButton = getValueRadioButton();
var requestCriticsButtons;
var editCriticsButtons;
var deleteCriticsButtons;

window.addEventListener("DOMContentLoaded", function() {
    valueRadioButton = getValueRadioButton();
    selectedOption();
    requestCriticsButtons = document.getElementsByClassName("request-critic");
    for (var i = 0; i < requestCriticsButtons.length; i++) {
        requestCriticsButtons[i].addEventListener("click", detailCritic, false);
    }
    editCriticsButtons = document.getElementsByClassName("edit-critic");
    for (var i = 0; i < editCriticsButtons.length; i++) {
        editCriticsButtons[i].addEventListener("click", editCritic, false);
    }
    deleteCriticsButtons = document.getElementsByClassName("delete-critic");
    for (var i = 0; i < deleteCriticsButtons.length; i++) {
        deleteCriticsButtons[i].addEventListener("click", deleteCritic, false);
    }
}, false);

const detailCritic = async function(){
    const idCritic = this.getAttribute("idcritic");
    window.location.href = "/critics/" + idCritic + "?mode=request";
}

const editCritic = async function(){
    const idCritic = this.getAttribute("idcritic");
    window.location.href = "/critics/edit/" + idCritic + "?mode=edit";
}

const deleteCritic = async function () {
    const idCritic = this.getAttribute("idcritic");
    var actionConfirmed = confirm("¿Estás seguro de que desea eliminar esta crítica?");
    if(actionConfirmed) {
        await deleteRequest("/critics/delete/" + idCritic);
        alert("La crítica ha sido eliminada");
        location.reload();
    }
};


function getValueRadioButton(){
    var selectedOption = "allCritics";

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
    if(selectedOption === "title")
    {
        document.getElementById("searchingByTitle").classList.remove("none");
        document.getElementById("searchingByTitle").classList.add("visible");
        valueRadioButton = "title";
    }
    else if(selectedOption === "allCritics")
    {
        document.getElementById("searchingByTitle").classList.remove("visible");
        document.getElementById("searchingByTitle").classList.add("none");
        valueRadioButton = "allCritics";
    }
    else if(selectedOption === "ownCritics")
    {
        document.getElementById("searchingByTitle").classList.remove("visible");
        document.getElementById("searchingByTitle").classList.add("none");
        valueRadioButton = "ownCritics";
    }
}

function searchCritics(){
    if(valueRadioButton === "allCritics") //allCritics
    {
        window.location.href = "/critics";
    }
    else if(valueRadioButton === "ownCritics") //ownCritics
    {
        window.location.href = "/critics/ownCritics?typeSearch="+valueRadioButton;
    }
    else if(valueRadioButton === "title") //title
    {
        window.location.href = "/critics/title?title="+ document.getElementById("searchTitle").value
            +"&typeSearch="+valueRadioButton;
    }
}