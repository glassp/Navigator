# Server component
- Install Tomcat via `sudo apt` and start service with attachment to localhost:8080
- Let server load data from ./src/java/main/AppResource.java (or rather .class)
- Handle AJAX-POST Requests with same class


# Client componente
- Overlay map with a points layer fetched from server via POST as GeoJSON
  - Marke Graph Node Points with Leaflets Marker
- Implement Routefinder function
  - Form with start and dest?
  - Button (mark as start) and Button (mark as destination)?
