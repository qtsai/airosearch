function top10MostAirports() {
    getTop10CountriesMostAirports(processSearchResponse)
}

function search() {
    let input = document.getElementById("search-bar").value;
    getCountries(input, processSearchResponse);
}

function processSearchResponse() {
    let results = getAndClearSearchResultsEl();
    if (this.status === 200) {
        showNavigationBar(false);
        setHeader("Countries");
        let response = JSON.parse(this.responseText);
        response.forEach(item => {
            let resultEl = newSearchResultWrapper()
            let title = item.code + " - " + item.name;
            let link = item.wikipediaLink
            resultEl.innerHTML =
                "<h3 class='pointer' data-code='" + item.code + "' data-country='" + item.name + "' onclick='selectCountry(this)'>" + title + "</h3>" +
                "<p>" +
                "Continent: " + item.continent + "<br>" +
                "Keywords: " + item.keywords + "<br>" +
                "Number of airports: " + item.numberOfAirports +
                "</p>" +
                "<a href=\"" + link + "\" target='_blank'>" + link + "</a>";
            results.appendChild(resultEl);
        });
    } else {
        results.innerHTML = "No results..."
    }
}

function getAndClearSearchResultsEl() {
    let searchResults = document.getElementById('search-results')
    searchResults.innerHTML = "";
    return searchResults;
}

function newSearchResultWrapper() {
    let searchResult = document.createElement("div");
    searchResult.className = "search-result";
    return searchResult;
}

function selectCountry(el, page = 0) {
    let code = el.getAttribute("data-code");
    let country = el.getAttribute("data-country");
    getAirports(code, page, processAirportsResponse);
    setHeader("Airports of " + code + " - " + country, code);
    getAndClearSearchResultsEl();
}

function processAirportsResponse() {
    let results = getAndClearSearchResultsEl();
    scrollTop();
    if (this.status === 200) {
        showNavigationBar(true)
        let response = JSON.parse(this.responseText);
        response.airports.forEach(airport => {
            let resultEl = newSearchResultWrapper()
            let home = airport.homeLink
            let wiki = airport.wikipediaLink
            let runwaysEl = createRunwayList(airport)
            resultEl.innerHTML =
                "<h3>" + airport.identifier + " - " + airport.name + "</h3>" +
                "<p>" +
                "Type: " + airport.type + "<br>" +
                "Region: " + airport.region + "<br>" +
                "Municipality: " + airport.municipality + "<br>" +
                "Keywords: " + airport.keywords + "<br>" +
                "Runways: <br>" +
                runwaysEl.outerHTML +
                "Homepage: <a href=\"" + home + "\" target='_blank'>" + home + "</a> <br>" +
                "Wikipedia: <a href=\"" + wiki + "\" target='_blank'>" + wiki + "</a>" +
                "</p>";
            results.appendChild(resultEl);
        });
        pagination(response);
    } else {
        results.innerHTML = "No results..."
    }
}

function createRunwayList(airport) {
    let runwaysEl = document.createElement("ul")
    airport.runways.forEach(runway => {
        let runwayEl = document.createElement("li")
        runwayEl.innerHTML = [runway.id, runway.surface, runway.lighted ? 'lighted' : 'non-lighted', runway.closed ? 'closed' : 'open'].join(" - ");
        runwaysEl.appendChild(runwayEl);
    })
    return runwaysEl;
}

function setHeader(type, countryCode = "") {
    let header = document.getElementById("result-type");
    header.innerHTML = type + ":";
    header.setAttribute("data-country", countryCode);
}

function showNavigationBar(show) {
    let el = document.getElementById("nav-bar");
    el.hidden = !show;
}


function pagination({current, numberOfPages}) {
    let prev = document.getElementById("prev");
    prev.hidden = current === 0;
    prev.onclick = function () {
        getAirports(getCountry(), current - 1, processAirportsResponse);
    }
    let next = document.getElementById("next");
    next.hidden = current === numberOfPages - 1;
    next.onclick = function () {
        getAirports(getCountry(), current + 1, processAirportsResponse);
    }
    let navigation = document.getElementById("pagination");
    navigation.innerHTML = "";

    let currentPage = current + 1;
    let pagesAdded = 0;

    let pageNumber =
        // render correctly for first 5 pages
        Math.min(Math.max(1, currentPage - 2), Math.max(numberOfPages - 5, 1));
    // render correctly for last 5 pages
    if (currentPage > numberOfPages - 4) {
        pageNumber = Math.max(1, numberOfPages - 4);
    }

    while (pageNumber <= numberOfPages && pagesAdded < 5) {
        let page = newPageEl();
        page.innerHTML = "" + pageNumber;
        page.setAttribute('data-page', (pageNumber - 1).toString());
        if (currentPage === pageNumber) {
            page.classList.add("active");
        } else {
            page.onclick = function () {
                getAirports(getCountry(), getPageNumber(this), processAirportsResponse);
            }
        }
        navigation.appendChild(page);
        pageNumber++;
        pagesAdded++;
    }
}

function newPageEl() {
    let page = document.createElement("span");
    page.classList.add("page");
    return page;
}

function getPageNumber(page) {
    return page.getAttribute("data-page");
}

function getCountry() {
    return document.getElementById("result-type").getAttribute("data-country");
}

function scrollTop() {
    window.scrollTo({ top: 0, behavior: 'auto' });
}