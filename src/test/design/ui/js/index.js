function getRekeningoverzichtJson(opened, done) {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        console.log(this.readyState);
        if (this.readyState == 1) opened();
        else if (this.readyState == 4) {
            if (this.status>=200&this.status<300)
                done(JSON.parse(this.responseText));
        }
    }
    xhttp.open("GET", "json/categorie.json");
    xhttp.send();
}

function appendCategories(el, categories) {
    categories.vast.forEach(value => el.innerHTML += categorieTemplate.innerHTML.replace("{{categorienaam}}", value.categorie));
    categories.incidenteel.forEach(value => el.innerHTML += categorieTemplate.innerHTML.replace("{{categorienaam}}", value.categorie));
}

window.onload = () => {
    let ul = document.getElementById("rekeningoverzicht");
    if (!ul||ul.constructor.name!="HTMLUListElement") throw new Error("HTML element ul.rekeningoverzicht niet gevonden");

    getRekeningoverzichtJson(
        () => ul.innerHTML = "",
        done => appendCategories(ul, done));
}