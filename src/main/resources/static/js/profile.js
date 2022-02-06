var profileButton;

window.addEventListener("DOMContentLoaded", function() {
    profileButton = document.getElementById("profile");
    const idUser = profileButton.getAttribute("idUser");
    profileButton.addEventListener("click", async function(){
        window.location.href = "/users/profile/" + idUser;
    }, false);

}, false);