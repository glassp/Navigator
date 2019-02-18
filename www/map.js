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
var findClosestNode = false;

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
    if (findClosestNode) {
        popupVar = L.popup()
            .setLatLng(e.latlng)
            .setContent("Inquiring closest node.<br>Please stand by.")
            .openOn(mapVar);  
            
         // Inquire Node from Server by sending lat/lng
        $.get(".server.api.PointsResource", [e.latitude, e.longitude], popupClosestNode);
            
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

function popupClosestNode(data) {
    // Expects server to answer with Latitude, Longitude and node ID
    // TODO: Implement answer either as JSON or as String that reads as below
    
    popupVar = L.popup()
            .setLatLng(e.latlng)
            .setContent("Closest node with id " + data[0] + "<br>Latitude: " + data[1] + "<br>Longitude: " + data[2])
            .openOn(mapVar);  
    
}

function toggleShowCoordinates(){        
    if (showCoordinates) {
        showCoordinates = false;
        document.getElementById('btnPopup').innerHTML= "Show Coordinates On Click";
    }
    else {
        showCoordinates = true;
        document.getElementById('btnPopup').innerHTML= "Stop Showing Coordinates On Click";
        
          if (findClosestNode) {
            toggleFindNode();
        }
    }
}
function toggleFindNode() {
    if (findClosestNode) {
        findClosestNode = false;
        document.getElementById('btnFindNode').innerHTML= "Find Closest Node On Click";
    }
    else {
        findClosestNode = true;
        document.getElementById('btnFindNode').innerHTML= "Stop Finding Closest Node On Click";
        
        if (showCoordinates) {
            toggleShowCoordinates();
        }
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
document.querySelector("#btnFindNode").addEventListener('click', toggleFindNode);
document.querySelector("#btnSelectStart").addEventListener('click', toggleSelectStart);
document.querySelector("#btnSelectDest").addEventListener('click', toggleSelectDest);

//TODO add functionality for find Route Button
document.querySelector("#btnFindRoute").addEventListener('click', getRouteAjax);
