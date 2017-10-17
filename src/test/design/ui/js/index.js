function getJson(callback) {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4) callback(this.response);
    }
    xhttp.open("GET", "http://localhost");
    xhttp.send();
}

function appendCategories(el, categories) {
    categories.forEach(value => el.innerHTML += categorieTemplate.innerHTML.replace("{{categorienaam}}", value));
}

window.onload = () => {
    appendCategories(document.body, ["Boodschappen","Tanken"]);
}