package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * The IOHandler which reads the graph .fmi file
 */
public class IOHandler extends CLILogger {

    String pathToBin() {
        return pathToProjectRoot() + "/bin/";
    }

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
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (runtime == 0)
            throw new RuntimeException("Error creating graph");
        return graph;
    }

    public double runQuery(String path, Graph graph) {
        //TODO: implement
        return 0;
    }

    public int diff(String solPath, String outputPath) {
        int counter = 0;
        try {
            var solReader = new FileReader(solPath);
            var sol = new BufferedReader(solReader);
            var outReader = new FileReader(outputPath);
            var out = new BufferedReader(outReader);
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
