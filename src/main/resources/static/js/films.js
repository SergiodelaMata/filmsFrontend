const searchRadioButton = document.getElementsByName("selectSearch");
const searchTitle = document.getElementById("searchTitle");
const searchYearInit = document.getElementById("searchYearInit");
const searchYearEnd = document.getElementById("searchYearEnd");
const searchCountry = document.getElementById("searchCountry");
const searchDirection = document.getElementById("searchDirection");
const searchGenres = document.getElementById("searchGenres");

function selectedOption(){
    var selectedOption = "title";

    for(var i = 0; i < searchRadioButton.length; i++)
    {
        if(searchRadioButton[i].checked)
        {
            selectedOption = searchRadioButton[i].value;
        }
    }
    console.log(selectedOption);
    document.getElementById("searchTitle").value = "";
    document.getElementById("searchYearInit").value = "";
    document.getElementById("searchYearEnd").value = "";
    document.getElementById("searchCountry").value = "";
    document.getElementById("searchDirection").value = "";
    document.getElementById("searchGenres").value = "";
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
    }

}

/*searchTitle.addEventListener("change", async () => {
   const title = searchTitle.value;

});*/
