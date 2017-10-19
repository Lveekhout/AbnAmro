function createExpandknopAnchorImage() {
    let img = document.createElement("img");
    img.src="images/plus.png"
    return img;
}

function createExpandknopAnchor() {
    let a = document.createElement("a");
    a.href = "javascript: void(0);";
    a.addEventListener("click", event => {
        if (a.parentNode.parentNode.nextElementSibling)
//            a.parentNode.parentNode.nextElementSibling.style.display = a.parentNode.parentNode.nextElementSibling.style.display == "" ? "block" : ""
            $(a.parentNode.parentNode.nextElementSibling).slideToggle("fast")
    });
    a.onclick = "toggleElement(this.parentNode.parentNode.nextElementSibling)";
    a.appendChild(createExpandknopAnchorImage())
    return a;
}

function createExpandknop() {
    let div = document.createElement("div");
    div.classList.add("expandknop");
    div.appendChild(createExpandknopAnchor());
    return div;
}

function createTekst(tekst) {
    let div = document.createElement("div");
    div.classList.add("tekst");
    div.textContent = tekst;
    return div;
}

function createTextcontainer(post) {
    let div = document.createElement("div");
    div.classList.add("textcontainer");
    div.appendChild(createExpandknop());
    div.appendChild(createTekst(post.categorie));
    return div;
}

function createNamenTable(namen) {
    let table = document.createElement("table");
    namen.forEach(naam => {
        let tr = document.createElement("tr");
        let td = document.createElement("td");
        td.appendChild(document.createTextNode(naam.naam));
        tr.appendChild(td);
        td = document.createElement("td");
        td.appendChild(document.createTextNode(naam.bedrag.credit));
        tr.appendChild(td);
        td = document.createElement("td");
        td.appendChild(document.createTextNode(naam.bedrag.debet));
        tr.appendChild(td);
        table.appendChild(tr);
    });
    return table;
}

function createNamen(namen) {
    let div = document.createElement("div");
    div.classList.add("namen");
    div.appendChild(createNamenTable(namen))
    return div;
}

function createCategorie(post) {
    let div = document.createElement("div");
    div.classList.add("categorie");
    div.appendChild(createTextcontainer(post));
    div.appendChild(createNamen(post.namen));
    return div;
}

function appendCategories(div, categories) {
    categories.vast.forEach(value => div.appendChild(createCategorie(value)));
    categories.incidenteel.forEach(value => div.appendChild(createCategorie(value)));
}

window.onload = () => {
    let div = document.getElementById("rekeningoverzicht");
    if (!div||div.constructor.name!="HTMLDivElement") throw new Error("HTML element div.rekeningoverzicht niet gevonden");

    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 1)
            div.innerHTML = "";
        else if (this.readyState == 4)
            if (this.status>=200&this.status<300) appendCategories(div, JSON.parse(this.responseText));
    }
    xhttp.open("GET", "json/categorie.json");
    xhttp.send();
}