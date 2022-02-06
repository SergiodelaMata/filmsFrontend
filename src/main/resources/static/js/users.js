const searchRadioButton = document.getElementsByName("selectSearch");
var valueRadioButton = getValueRadioButton();
var requestUsersButtons;
var editUsersButtons;
var deleteUsersButtons;
var acceptUsersButtons;
var removeUsersButtons;
var increaseRolButtons;
var decreaseRolButtons;

window.addEventListener("DOMContentLoaded", function() {
    valueRadioButton = getValueRadioButton();
    selectedOption();
    requestUsersButtons = document.getElementsByClassName("request-user");
    for (var i = 0; i < requestUsersButtons.length; i++) {
        requestUsersButtons[i].addEventListener("click", detailUser, false);
    }
    editUsersButtons = document.getElementsByClassName("edit-user");
    for (var i = 0; i < editUsersButtons.length; i++) {
        editUsersButtons[i].addEventListener("click", editUser, false);
    }
    deleteUsersButtons = document.getElementsByClassName("delete-user");
    for (var i = 0; i < deleteUsersButtons.length; i++) {
        deleteUsersButtons[i].addEventListener("click", deleteUser, false);
    }
    acceptUsersButtons = document.getElementsByClassName("accept-user");
    for (var i = 0; i < acceptUsersButtons.length; i++) {
        acceptUsersButtons[i].addEventListener("click", acceptUser, false);
    }
    removeUsersButtons = document.getElementsByClassName("remove-user");
    for (var i = 0; i < removeUsersButtons.length; i++) {
        removeUsersButtons[i].addEventListener("click", removeUser, false);
    }
    increaseRolButtons = document.getElementsByClassName("increase-rol");
    for (var i = 0; i < increaseRolButtons.length; i++) {
        increaseRolButtons[i].addEventListener("click", increaseRol, false);
    }
    decreaseRolButtons = document.getElementsByClassName("decrease-rol");
    for (var i = 0; i < decreaseRolButtons.length; i++) {
        decreaseRolButtons[i].addEventListener("click", decreaseRol, false);
    }
}, false);

const detailUser = async function(){
    const idUser = this.getAttribute("idUser");
    window.location.href = "/users/" + idUser + "?mode=request";
}

const editUser = async function(){
    const idUser = this.getAttribute("idUser");
    window.location.href = "/users/edit/" + idUser + "?mode=edit";
}

const acceptUser = async function () {
    const idUser = this.getAttribute("idUser");
    var actionConfirmed = confirm("¿Estás seguro de que desea aceptar a este usuario?");
    if(actionConfirmed) {
        await putRequest("/users/acceptRequest/" + idUser);
        //const response = await putRequest("/users/acceptRequest/" + idUser);
        alert("El usuario ha sido aceptado");
        location.reload();
    }
};

const removeUser = async function () {
    const idUser = this.getAttribute("idUser");
    var actionConfirmed = confirm("¿Estás seguro de que desea eliminar a este usuario?");
    if(actionConfirmed) {
        await deleteRequest("/users/delete/" + idUser);
        //const response = await deleteRequest("/users/delete/" + idUser);
        alert("El usuario ha sido eliminado");
        location.reload();
    }
};

const increaseRol = async function () {
    const idUser = this.getAttribute("idUser");
    var actionConfirmed = confirm("¿Estás seguro de que desea hacer administrador a este usuario?");
    if(actionConfirmed) {
        await putRequest("/users/increaseRol/" + idUser);
        //const response = await putRequest("/users/increaseRol/" + idUser);
        alert("El usuario ha sido aceptado");
        location.reload();
    }
};

const decreaseRol = async function () {
    const idUser = this.getAttribute("idUser");
    var actionConfirmed = confirm("¿Estás seguro de que desea bajar el rol a este usuario?");
    if(actionConfirmed) {
        await putRequest("/users/decreaseRol/" + idUser);
        //const response = await putRequest("/users/decreaseRol/" + idUser);
        alert("El usuario ha sido aceptado");
        location.reload();
    }
};

const deleteUser = async function () {
    const idUser = this.getAttribute("idUser");
    var actionConfirmed = confirm("¿Estás seguro de que desea eliminar a este usuario?");
    if(actionConfirmed) {
        await deleteRequest("/users/delete/" + idUser);
        //const response = await deleteRequest("/users/delete/" + idUser);
        alert("El usuario ha sido eliminado");
        location.reload();
    }
};



function getValueRadioButton(){
    var selectedOption = "username";

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
    document.getElementById("searchUsername").value = "";
    document.getElementById("searchEmail").value = "";
    if(selectedOption === "username")
    {
        document.getElementById("searchingByUsername").classList.remove("none");
        document.getElementById("searchingByUsername").classList.add("visible");
        document.getElementById("searchingByEmail").classList.remove("visible");
        document.getElementById("searchingByEmail").classList.add("none");
        valueRadioButton = "username";
    }
    else if(selectedOption === "email")
    {
        document.getElementById("searchingByUsername").classList.remove("visible");
        document.getElementById("searchingByUsername").classList.add("none");
        document.getElementById("searchingByEmail").classList.remove("none");
        document.getElementById("searchingByEmail").classList.add("visible");
        valueRadioButton = "email";
    }
}

function searchUsers(){
    if(valueRadioButton === "username") //username
    {
        window.location.href = "/users/username?username="+ document.getElementById("searchUsername").value
            +"&typeSearch="+valueRadioButton;
    }
    else if(valueRadioButton === "email") //email
    {
        window.location.href = "/users/email?email="+ document.getElementById("searchEmail").value
            +"&typeSearch="+valueRadioButton;
    }
}