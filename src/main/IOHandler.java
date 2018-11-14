package main;

public class IOHandler {
    /**
     * the starttime for time tracking
     */
    public double startTime;

    /**
     * starts the timer
     */
    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    public Graph importGraph(String path) {
        //TODO: openFileStream
        //TODO: read
        //TODO: generate Graph
        return null;
    }


    //TODO: importQueryFrom(String path)
    //TODO: assertTimeout: Graphimport under 2 minutes
    //TODO: exportSolutionTo(String path, solution):void
    //TODO: diff(String pathSol, String pathSolution):bool
}
