function getCountries(input, callback) {
    get("http://localhost:8080/countries/search?term=" + input, callback)
}

function getTop10CountriesMostAirports(callback) {
    get("http://localhost:8080/countries/top-10-most-airports", callback)
}

function getAirports(code, page, callback) {
    get("http://localhost:8080/airports/search?term=" + code + "&size=20&page=" + page, callback)
}

function get(url, callback) {
    let xhr = new XMLHttpRequest();
    xhr.addEventListener("load", callback);
    xhr.open("GET", url);
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send();
}