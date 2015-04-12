package nauty;

import graph.model.IntGraph;
import group.Partition;
import group.Permutation;
import group.PermutationGroup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DreadnautLink {
    
    public static PermutationGroup getAutGroup(IntGraph graph, boolean useColors) throws IOException, InterruptedException {
        List<Permutation> generators = new ArrayList<Permutation>();
        int size = graph.getVertexCount();
        List<String> output = getOutput(graph, useColors);
        System.out.println("got output");
        for (String o : output) {
            System.out.println(o);
            if (o.startsWith("(")) {
                Permutation p = Permutation.fromCycleString(o, size);
                System.out.println(p);
                generators.add(p);
            }
        }
        PermutationGroup group = new PermutationGroup(size, generators);
        return group;
    }
    
    public static void writeScript(File inFile, File outFile) throws IOException {
        File script = new File("tmp.sh");
        if (!script.exists()) { script.createNewFile(); }
        BufferedWriter file = new BufferedWriter(new FileWriter(script));
        file.write("#!/bin/sh");
        file.newLine();
        file.write(String.format("./nauty/dreadnaut < %s > %s", inFile, outFile));
        file.newLine();
        file.flush();
        file.close();
        script.setExecutable(true);
    }
    
    public static List<String> getOutput(IntGraph g, boolean useColors) throws IOException, InterruptedException {
        File file = writeToDreadnautInputFile(g, useColors);
        File outfile = new File("out.txt");
        if (!outfile.exists()) { outfile.createNewFile(); }

        writeScript(file, outfile);
        File afile = new File("tmp.sh");
        System.out.println(afile.exists());
        Process p = Runtime.getRuntime().exec("./tmp.sh", new String[]{}, 
                new File("/Users/maclean/development/projects/mathgraphs"));
        p.waitFor();
        List<String> output = new ArrayList<String>();
        BufferedReader out = new BufferedReader(new FileReader(outfile));
        String line;
        while ((line = out.readLine()) != null) {
            output.add(line);
            if (line.contains("time")) break;
        }
        out.close();
        
        file.delete();
        outfile.delete();
        return output;
    }
    
    public static File writeToDreadnautInputFile(IntGraph g, boolean useColors) throws IOException {
        File file = new File("tmp.txt");
        if (!file.exists()) { file.createNewFile(); }
        BufferedWriter in = new BufferedWriter(new FileWriter(file));
        int n = g.getVertexCount();
        String initialString = String.format("n=%s g;", n); 
        in.write(initialString); in.newLine();
        System.out.println(initialString);
        for (int vertexIndex = 0; vertexIndex < n; vertexIndex++) {
            String adjString = String.format("%s :", vertexIndex);
            for (int connected : g.getConnected(vertexIndex)) {
                adjString += String.format(" %s", connected);
            }
            adjString += ";";
            in.write(adjString); in.newLine();
            System.out.println(adjString);
        }
        if (useColors) {
            Partition p = g.getColorsAsPartition();
            String partitonString = String.format("f=%s", p); 
            in.write(partitonString); in.newLine();
            System.out.println(partitonString);
        }
        in.write("x"); in.newLine(); 
        in.write("q"); in.newLine(); in.flush();
        System.out.println("x");
        in.close();
        return file;
    }
}
