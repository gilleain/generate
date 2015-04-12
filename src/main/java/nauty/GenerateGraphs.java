package nauty;

import graph.model.Graph;
import graph.model.IntGraph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenerateGraphs {
    
    public static void writeGraphsToFile(int n, String filename) throws IOException, InterruptedException {
        GenerateGraphs.writeGraphsToFile(n, 4, 1, filename);
    }
    
    public static void writeGraphsToFile(int n, int maxDegree, String filename) throws IOException, InterruptedException {
    	GenerateGraphs.writeGraphsToFile(n, maxDegree, 1, filename);
    }
    
    public static void writeGraphsToFile(int n,
                                         int maxDegree,
                                         int maxEdgeColor,
                                         String filename) throws IOException, InterruptedException {
        // run geng
        String tmpGengFilename = "tmp_geng_" + n + ".g6";
        ProcessBuilder gengProcess = 
            new ProcessBuilder("nauty/geng", 
                               String.valueOf(n),   // the number of vertices
                               "-c",                // only connected graphs
                               "-D" + maxDegree,    // the maxdegree
                               tmpGengFilename);
        Process geng = gengProcess.start();
        geng.waitFor();
        
        // run multig
        String tmpMultigFilename = "tmp_multig_" + n + ".txt";
        ProcessBuilder listgProcess = 
            new ProcessBuilder("nauty/multig",
                               "-m" + maxEdgeColor,     // the max edge color
                               "-T",                   // text-readable format
                               tmpGengFilename,
                               tmpMultigFilename);
        Process listg = listgProcess.start();
        listg.waitFor();
        
        // read in the graphs
        List<IntGraph> graphs = 
            GenerateGraphs.readMultigTextFormatFile(tmpMultigFilename);
        
        // write
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (Graph g : graphs) {
            writer.write(g.toString());
            writer.newLine();
        }
        writer.close();
        File tmpGengFile = new File(tmpGengFilename);
        if (tmpGengFile.exists()) {
            tmpGengFile.delete();
        }
        File tmpMultigFile = new File(tmpMultigFilename);
        if (tmpMultigFile.exists()) {
            tmpMultigFile.delete();
        }
    }
    
    public static List<IntGraph> readMultigTextFormatFile(String filename) throws IOException {
        List<IntGraph> graphs = new ArrayList<IntGraph>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] bits = line.split("\\s");
            IntGraph graph = new IntGraph();
            for (int i = 2; i < bits.length; i += 3) {
                int a = Integer.parseInt(bits[i]);
                int b = Integer.parseInt(bits[i + 1]);
                int c = Integer.parseInt(bits[i + 2]);
                graph.makeEdge(a, b, c);
            }
            graphs.add(graph);
        }
        reader.close();
        return graphs;
    }

}
