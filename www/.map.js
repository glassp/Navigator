var mapVar;
var popupVar;
var routeGeoLayer;

// to store coordinates:
var start;
var dest;
var startLat;
var startLong;
var destLat;
var destLong;
var startMarker;
var destMarker;
var nodeId;
var nodeLat;
var nodeLong;
var wait = false;

var selectStart = false;
var selectDest = false;
var showCoordinates = false;

function initMap() {
    // Init map to lat/lon for Uni Stuttgart, IT building
    // 48.7451, 9.1067

    mapVar = L.map('map', {
        center: [48.7451, 9.1067],
        zoom: 15
    });


    // use Open Street Map tiles for map
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap contributors</a>',
        subdomains: ['a', 'b', 'c']
    }).addTo(mapVar);

    // Activate Listener
    mapVar.on('click', onMapClick);

    //document.getElementById('helperLine').innerHTML=""; //Can be used for feedback
}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

function isNode(lat, lng) {
    if (lat === nodeLat && lng === nodeLong)
        return "<span class=\"green\"><p>These Coordinates are a Node</p></span>";
    return "<span class=\"red\"><p>These Coordinates are not a Node</p></span>"
}

async function onMapClick(e) {
    if (selectStart) {
        if (startMarker !== undefined) {
            removeMarker(startMarker);
        }
        start = e;
        startLat = e.latlng.lat;
        startLong = e.latlng.lng;
        startMarker = mark(startLat, startLong, "Start - ");

        toggleSelectStart();
//        if (showCoordinates) {  
//            toggleShowCoordinates
//        }
    }

    if (selectDest) {
        if (destMarker !== undefined) {
            removeMarker(destMarker);
        }
        dest = e;
        destLat = e.latlng.lat;
        destLong = e.latlng.lng;
        destMarker = mark(destLat, destLong, "Destination - ");

        toggleSelectDest()
//          if (showCoordinates) {  
//            toggleShowCoordinates
//        }
    }
    
        if (showCoordinates) {
        popupVar = L.popup()        // Immediate feedback, since inquiry can take eup to 5 seconds
            .setLatLng(e.latlng)
            .setContent("You clicked at" +
                "<br>Latitude: " + e.latlng.lat + "" +
                "<br>Longitude: " + e.latlng.lng +
                "<br><br>Fetching info on closest node now...")
            .openOn(mapVar);
        getNextNodeAjax(e.latlng.lat, e.latlng.lng);
        while (wait)
            await sleep(1000);
        popupVar = L.popup()        // Full info shown
            .setLatLng(e.latlng)
            .setContent("You clicked at" +
                "<br>Latitude: " + e.latlng.lat + "" +
                "<br>Longitude: " + e.latlng.lng +
                "<br><br>Next Node is:<br> &nbsp;&nbsp;&nbsp;&nbsp;Node ID: " + nodeId +
                "<br>&nbsp;&nbsp;&nbsp;&nbsp;Node Latitude: " + nodeLat +
                "<br> &nbsp;&nbsp;&nbsp;&nbsp;Node Longitude: " + nodeLong + "<br>" +
                isNode(e.latlng.lat, e.latlng.lng))
            .openOn(mapVar);
        
        //mark(nodeLat, nodeLong); // Marker to see where next node would be
    }
    
}

function toggleShowCoordinates() {
    if (showCoordinates) {
        showCoordinates = false;
        document.getElementById('btnPopup').innerHTML = "Show Coordinates On Click";
    } else {
        showCoordinates = true;
        document.getElementById('btnPopup').innerHTML = "Stop Showing Coordinates On Click";
    }
}

function toggleSelectStart() {
    if (selectDest) {
        return;
    }

    if (selectStart) {
        selectStart = false;
        document.getElementById('btnSelectStart').innerHTML = "Select Starting Point";
    } else {
        selectStart = true;
        document.getElementById('btnSelectStart').innerHTML = "Cancel Start Selection";
    }
}

function toggleSelectDest() {
    if (selectStart) {
        return;
    }

    if (selectDest) {
        selectDest = false;
        document.getElementById('btnSelectDest').innerHTML = "Select Destination";
    } else {
        selectDest = true;
        document.getElementById('btnSelectDest').innerHTML = "Cancel Destination Selection";
    }
}


function getRouteAjax() {
    if (start === undefined || dest === undefined) {
        return;
    }
    
    document.getElementById('btnFindRoute').innerHTML = "Calculating Route...";
    
    var requestUrl = ".server.api.PathResource.api?startLat=" + startLat + "&startLong=" + startLong + "&destLat=" + destLat + "&destLong=" + destLong;

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var response = this.responseText;
            document.getElementById('btnFindRoute').innerHTML = "Find Route";
            displayGeo(JSON.parse(response));
        }
    };
    xhttp.open("GET", requestUrl, true);
    xhttp.send();
}

async function getNextNodeAjax(lat, lng) {
    wait = true;
    var requestUrl = ".server.api.NextNodeResource.api?lat=" + lat + "&long=" + lng;
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var nodeInfo = this.responseText;
            var node = nodeInfo.split(":");
            nodeId = node[0];
            nodeLat = node[1];
            nodeLong = node[2];
            wait = false;
        }
    };
    xhttp.open("GET", requestUrl, true);
    xhttp.send();
}

function showGraphPoints() {
    //can crash the site when the produced geoJson is too big (tested with bw.fmi)

    var requestUrl = ".server.api.PointsResource.api";

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            console.log("Graph Points GeoJson");
            var response = this.responseText;
            displayGeo(JSON.parse(response));
            console.log(JSON.parse(response));
            console.log(response);
        }
    };
    xhttp.open("GET", requestUrl, true);
    xhttp.send();
}

function displayGeo(geoJson) {
    if (routeGeoLayer !== undefined) {
        routeGeoLayer.remove();
    }

    routeGeoLayer = L.geoJSON(geoJson).addTo(mapVar);
}


function mark(x, y, label) {
    if (label === undefined) {
        label = ""
    }

    var marker = L.marker([x, y]).addTo(mapVar);
    marker.bindPopup(label + "Marker Position: <br> Latitude: " + x + "<br>Longitude: " + y);
    return marker;
}

function removeMarker(m) {
    m.remove();
}


//////////////////////////////
//   Click Event Listner   //
document.addEventListener('DOMContentLoaded', initMap);
document.querySelector("#btnPopup").addEventListener('click', toggleShowCoordinates);
document.querySelector("#btnSelectStart").addEventListener('click', toggleSelectStart);
document.querySelector("#btnSelectDest").addEventListener('click', toggleSelectDest);

document.querySelector("#btnFindRoute").addEventListener('click', getRouteAjax);
