//Implemented:
//init map
//function mark(x,y) to set Markers at Point(lat:x,long:y)
//handleClick(e) -> Snackbar("You clicked at e.lat, e.long")
//addEventlistner DOMContentLoader->init, click->handleClick

//TODO: load GeoJson via AJAX
//TODO: function getRoute(startLat, startLong, destLat, destLong) and helper functions

var mapVar;
var popupVar;

// to store coordinates:
var start;
var dest;
var startMarker;
var destMarker;

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
    L.tileLayer( 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>',
        subdomains: ['a', 'b', 'c']
    }).addTo( mapVar );

    // Activate Listener
    mapVar.on('click', onMapClick); 
    
    //document.getElementById('helperLine').innerHTML=""; //Can be used for feedback
} 


function onMapClick(e) {
    if (showCoordinates) {
         popupVar = L.popup()
        .setLatLng(e.latlng)
        .setContent("You clicked at<br>Latitude: " + e.latlng.lat + "<br>Longitude: "+ e.latlng.lng)
        .openOn(mapVar);   
    }
    
    if (selectStart) {
        if (startMarker !== undefined) {
            removeMarker(startMarker);
        }
        start = e;
        startMarker = mark(e.latlng.lat, e.latlng.lng, "Start - ");
        
        toggleSelectStart();
    }
    
    if (selectDest) {
        if (destMarker !== undefined) {
            removeMarker(destMarker);
        }
        dest = e;
        destMarker = mark(e.latlng.lat, e.latlng.lng, "Destination - ");
        
        toggleSelectDest()
    }
}

function toggleShowCoordinates(){
    if (showCoordinates) {
        showCoordinates = false;
        document.getElementById('btnPopup').innerHTML= "Show Coordinates On Click";
    }
    else {
        showCoordinates = true;
        document.getElementById('btnPopup').innerHTML= "Stop Showing Coordinates On Click";
    }
}

function toggleSelectStart(){
    if (selectDest) {return}
    
    if (selectStart) {
        selectStart = false;
        document.getElementById('btnSelectStart').innerHTML= "Select Starting Point";
    }
    else {
        selectStart = true;
        document.getElementById('btnSelectStart').innerHTML= "Cancel Start Selection";
    }
}

function toggleSelectDest(){
    if (selectStart) {return}
    
    if (selectDest) {
        selectDest = false;
        document.getElementById('btnSelectDest').innerHTML= "Select Destination";
    }
    else {
        selectDest = true;
        document.getElementById('btnSelectDest').innerHTML= "Cancel Destination Selection";
    }
}


function getRouteAjax() {
    console.log("Not yet implementd");

    //Hope this works via jQuery else we have to use pure JS
    //jQuery.getJSON(".server.api.PathResource.api", displayGeo(contentGeoJ));
    
    $.getJSON(".server.api.PathResource.api", displayGeo);
    //$.getJSON("geoTest.json", displayGeo);
}
function displayGeo(geoJson) {
    //console.log(geoJson);
    //console.log(JSON.stringify(geoJson));
    L.geoJSON(geoJson).addTo(mapVar);
}



function mark(x, y, label) {
    if (label === undefined) { label = ""}
    
    var marker = L.marker([x,y]).addTo(mapVar);
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

//TODO add functionality for find Route Button
document.querySelector("#btnFindRoute").addEventListener('click', getRouteAjax);
