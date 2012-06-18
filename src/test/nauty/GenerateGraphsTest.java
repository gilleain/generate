package test.nauty;

import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import model.Graph;
import nauty.GenerateGraphs;

import org.junit.Test;

public class GenerateGraphsTest {
    
    @Test
    public void readGraphs() throws IOException {
        String filename = "fours_multig.txt";
        List<Graph> graphs = GenerateGraphs.readMultigTextFormatFile(filename);
        for (Graph g : graphs) {
            System.out.println(g);
        }
        Assert.assertEquals(6, graphs.size());
    }
    
    @Test
    public void generateGraphsOnFour() throws IOException, InterruptedException {
        String outputFilename = "fours_nauty.txt";
        GenerateGraphs.writeGraphsToFile(4, outputFilename);
    }
    
    @Test
    public void generateGraphsOnFive() throws IOException, InterruptedException {
        String outputFilename = "fives_nauty.txt";
        GenerateGraphs.writeGraphsToFile(5, outputFilename);
    }
    
    @Test
    public void generateGraphsOnSixes() throws IOException, InterruptedException {
        String outputFilename = "sixes_nauty.txt";
        GenerateGraphs.writeGraphsToFile(6, outputFilename);
    }
    
    @Test
    public void generateGraphsOnSevens() throws IOException, InterruptedException {
        String outputFilename = "sevens_nauty.txt";
        GenerateGraphs.writeGraphsToFile(7, outputFilename);
    }
    
    @Test
    public void generateGraphsOnEights() throws IOException, InterruptedException {
        String outputFilename = "eights_nauty.txt";
        GenerateGraphs.writeGraphsToFile(8, outputFilename);
    }
    
    @Test
    public void generateGraphsOnNines() throws IOException, InterruptedException {
        String outputFilename = "nines_nauty.txt";
        GenerateGraphs.writeGraphsToFile(9, outputFilename);
    }
    
    @Test
    public void generateGraphsOnTens() throws IOException, InterruptedException {
        String outputFilename = "tens_nauty.txt";
        GenerateGraphs.writeGraphsToFile(10, outputFilename);
    }
    
    @Test
    public void generateGraphsOnTwelves() throws IOException, InterruptedException {
        String outputFilename = "twelves__three_nauty.txt";
        GenerateGraphs.writeGraphsToFile(12, 3, outputFilename);
    }

}
