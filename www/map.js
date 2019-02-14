//TODO: init map
//TODO: load GeoJson via AJAX
//TODO: function mark(x,y) to set Markers at Point(lat:x,long:y)
//TODO: handleClick(e) -> Snackbar("You clicked at e.lat, e.long")
//TODO: addEventlistner DOMContentLoader->init, click->handleClick
//TODO: function getRoute(startLat, startLong, destLat, destLong) and helper functions


// Init map

// uing default lat/lon for Uni Stuttgart, IT building
// 48.7451 9.1067


function initMap() {
    document.getElementById('helperLine').innerHTML = 'Initializing map now.';    
    var mapVar = L.map('map', {
        center: [48.7451, 9.1067],
        zoom: 15
    });
    
    // .setView([48.7451, 9.1067], 12) alternatively, as used in quick start guide

    
    // using Open Street Map tiles.
    L.tileLayer( 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>',
        subdomains: ['a', 'b', 'c']
    }).addTo( mapVar );

    document.getElementById('helperLine').innerHTML='Map initialization successfull.';

} 