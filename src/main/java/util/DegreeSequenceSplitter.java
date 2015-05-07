package util;

import graph.model.GraphFileReader;
import graph.model.IntGraph;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Split a file of graphs into separate files of degree sequences.
 * 
 * @author maclean
 *
 */
public class DegreeSequenceSplitter {
    
    public void split(String inputFilename, String outputDirname) throws IOException {
        File outputDir = new File(outputDirname);
        if (outputDir.isDirectory()) {
            split(new File(inputFilename), outputDir);
        }
    }
    
    private void split(File inputFile, File outputDir) throws IOException {
        GraphFileReader reader = new GraphFileReader(new FileReader(inputFile));
        Map<String, List<IntGraph>> degreeSeqMap = new HashMap<String, List<IntGraph>>();
        for (IntGraph graph : reader) {
            String degSeqString = Arrays.toString(graph.degreeSequence(true));
            List<IntGraph> bucket;
            if (degreeSeqMap.containsKey(degSeqString)) {
                bucket = degreeSeqMap.get(degSeqString);
            } else {
                bucket = new ArrayList<IntGraph>();
                degreeSeqMap.put(degSeqString, bucket);
            }
            bucket.add(graph);
        }
        
        for (String degreeSeqString : degreeSeqMap.keySet()) {
            File outputFile = new File(outputDir, toFilename(degreeSeqString));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            for (IntGraph graph : degreeSeqMap.get(degreeSeqString)) {
                writer.write(graph.toString());
                writer.newLine();
            }
            writer.flush();
            writer.close();
        }
    }

    private String toFilename(String degreeSeqString) {
        return degreeSeqString.substring(1, degreeSeqString.length() - 1).replace(", ", "_");
    }

}
