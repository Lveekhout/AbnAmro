function getJson(callback) {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4) callback(this.response);
    }
    xhttp.open("GET", "http://localhost");
    xhttp.send();
}