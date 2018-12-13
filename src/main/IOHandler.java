package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * The IOHandler
 */
public class IOHandler extends CLILogger {

    /**
     * returns the path to the bin dir
     *
     * @return path to bin dir
     */
    String pathToBin() {
        return pathToProjectRoot() + "/bin/";
    }

    /**
     * returns the path to the projects root dir
     * @return path to project root dir
     */
    String pathToProjectRoot() {
        String current = Paths.get(".").toAbsolutePath().normalize().toString();
        return current.split("/src/")[0];
    }

    /**
     * imports the Graph from a graph .fmi file also stops runtime for this task
     *
     * @param path the path to the file
     * @return a Graph object or null
     */
    public Graph importGraph(String path) {
        this.print("Reading Graph Data...");
        Graph graph;
        try {
            this.startTiming();
            var fileReader = new FileReader(path);
            var file = new BufferedReader(fileReader);

            //ignore the 4 comment lines
            file.readLine();
            file.readLine();
            file.readLine();
            file.readLine();
            //ignore blank line
            file.readLine();

            String nodesStr = file.readLine();
            String edgesStr = file.readLine();

            int nodes = Integer.parseInt(nodesStr);
            int edges = Integer.parseInt(edgesStr);

            graph = new Graph(nodes, edges);

            for (int i = 0; i < nodes; i++) {
                String line = file.readLine();
                String[] data = line.split(" ");
                int node = Integer.parseInt(data[0]);
                double latitude = Double.parseDouble(data[2]);
                double longitude = Double.parseDouble(data[3]);
                graph.setGeo(node, latitude, longitude);
            }
            for (int i = 0; i < edges; i++) {
                String line = file.readLine();
                String[] data = line.split(" ");
                int start = Integer.parseInt(data[0]);
                int dest = Integer.parseInt(data[1]);
                double cost = Double.parseDouble(data[2]);
            }
            this.stop();
            this.print("Finished Reading Graph Data in " + CLILogger.runtimeInMinutes(this.runtime) + "minutes." + "\r\n" + "Time in Seconds: " + CLILogger.runtimeInSeconds(this.runtime));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (runtime == 0)
            throw new RuntimeException("Error creating graph");
        return graph;
    }

    /**
     * runs the queries from .que file on the graph and outputs into a .out file
     * @param path the path the .que file
     * @param graph the graph
     */
    public void runQuery(String path, Graph graph) {
        try {
            var fileReader = new FileReader(path);
            var file = new BufferedReader(fileReader);
            String line;
            while ((line = file.readLine()) != null) {
                String[] data = line.split(" ");
                if (data[0] != null && data[1] != null && !data[0].isEmpty() && !data[1].isEmpty()) {
                    var result = graph.runQuery(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
                    //TODO: write into .out file
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * checks for differences between .sol and .out filr
     *
     * @param solPath path to .sol file
     * @param outPath path to .out file
     * @return number of differences
     */
    public int diff(String solPath, String outPath) {
        int counter = 0;
        try {
            var outReader = new FileReader(outPath);
            var solReader = new FileReader(solPath);
            var out = new BufferedReader(outReader);
            var sol = new BufferedReader(solReader);
            String s;
            String o;
            while ((s = sol.readLine()) != null && (o = out.readLine()) != null) {
                if (!s.equals(o))
                    counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        return counter;
    }

    //TODO: assertTimeout: Graphimport under 2 minutes
    //TODO: exportSolutionTo(String path, solution):void
}
