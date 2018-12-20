package main;

import java.io.*;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

/**
 * The IOHandler
 */
public class IOHandler extends CLILogger {

    private String filename;
    private BufferedWriter outputStream;


    /**
     * returns the path to the bin dir
     *
     * @return path to bin dir
     */
    static String pathToBin() {
        return pathToProjectRoot() + "/bin/";
    }

    /**
     * returns the path to the projects root dir
     * @return path to project root dir
     */
    static String pathToProjectRoot() {
        String current = Paths.get(".").toAbsolutePath().normalize().toString();
        return current.split("/src/")[0];
    }

    private void initStream(Graph graph) {
//    	if (new File(pathToBin() + "out/").mkdir()) {
//			print("Created directory '" + pathToBin() + "out'.\n");
//		}
//    	//Not strictly necessary since makefile already creates path.

        filename = graph.hashCode() + ".out";
        try {
            outputStream = new BufferedWriter(new FileWriter(pathToBin() + "out/" + filename));
        } catch (IOException e) {
            exceptionPrint(e);
        }
    }

    /**
     * imports the Graph from a graph .fmi file also stops runtime for this task
     *
     * @param path the path to the file
     * @throws FileNotFoundException let FNFException go up to CLI where it will be handled
     * @return a Graph object or null
     */
    public Graph importGraph(String path) throws FileNotFoundException {
        this.print("Reading Graph Data...");
        Graph graph;
        try {
            this.startTiming();
            FileReader fileReader = new FileReader(path);
            BufferedReader file = new BufferedReader(fileReader);

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
                
                graph.insertEdge(start, dest, cost);

            }
            this.stop();
            this.print("Finished Reading Graph Data in " + CLILogger.runtimeInMinutes(this.runtime) + " minutes." + "\r\n" + "Time in Seconds: " + CLILogger.runtimeInSeconds(this.runtime));
        } catch (NoSuchFileException | FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (Exception e) {
            exceptionPrint(e);
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
     * @throws FileNotFoundException lets FNFException go up to CLI where it will be handled
     * @return the output path of the file as absolute path
     */
    public String runQuery(String path, Graph graph) throws FileNotFoundException {
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader file = new BufferedReader(fileReader);
            this.initStream(graph);
            String line;
            while ((line = file.readLine()) != null) {
                String[] data = line.split(" ");
                if (data[0] != null && data[1] != null && !data[0].isEmpty() && !data[1].isEmpty()) {
                    int result = (int) graph.runQuery(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
                    outputStream.write(result + "");
                    outputStream.newLine();
                }
            }
            outputStream.close();
            file.close();
        } catch (NoSuchFileException | FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            exceptionPrint(e);
        }

        return pathToBin() + "out/" + filename;
    }

    /**
     * checks for differences between .sol and .out file
     *
     * @param solPath path to .sol file
     * @param outPath path to .out file
     * @throws FileNotFoundException lets FNFException go up to CLI where it will be handled
     */
    public void diff(String solPath, String outPath) throws FileNotFoundException {
        int counter = 0;
        int line = 1;
//        print("line:\tdifference");
        try {
            FileReader outReader = new FileReader(outPath);
            FileReader solReader = new FileReader(solPath);
            BufferedReader out = new BufferedReader(outReader);
            BufferedReader sol = new BufferedReader(solReader);
            String s;
            String o;
            while ((s = sol.readLine()) != null && (o = out.readLine()) != null) {
                if (!o.equals(s))
                    counter++;
//                print( (line++) + ":\t" + (Integer.parseInt(s) - Integer.parseInt(o) ) ); 	//useful for debugging, but can cause exception if random file is chosen that can't be converted to int.
            }
            print("There are " + counter + " differences.");
        } catch (NoSuchFileException | FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            exceptionPrint(e);
        }

    }

    public void exceptionPrint(Exception e) {
        this.print();
        this.print("Something went wrong. Check verbose and debug prints for more information.");
        if (debug) {
            for (StackTraceElement elem : e.getStackTrace()) {
                debugPrint(elem.toString());
            }
        }
        this.print();
    }
}
