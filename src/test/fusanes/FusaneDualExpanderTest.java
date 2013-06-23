package test.fusanes;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import tree.WROMTreeGenerator;
import filter.MaxDegreeFilter;
import fusanes.FusaneDualExpander;
import fusanes.FusaneInnerDual;
import fusanes.SimpleFusaneLabeler;
import graph.model.Graph;
import graph.tree.TreeCertificateMaker;

public class FusaneDualExpanderTest {
    
    public List<Integer> makeLabels(int... labels) {
        List<Integer> list = new ArrayList<Integer>();
        for (int l : labels) {
            list.add(l);
        }
        return list;
    }
    
    public List<List<Integer>> allLabels() {
        List<List<Integer>> all = new ArrayList<List<Integer>>();
        all.add(makeLabels(5));
        all.add(makeLabels(1, 3));
        all.add(makeLabels(3, 1));
        all.add(makeLabels(2, 2));
        all.add(makeLabels(1, 1, 1));
        return all;
    }
    
    @Test
    public void makeInitialCycle() {
        for (List<Integer> labels : allLabels()) {
            System.out.println(FusaneDualExpander.makeSixCycle(new Graph(), labels));
        }
    }
    
    @Test
    public void expandCycle() {
        for (List<Integer> labels : allLabels()) {
            Graph g = new Graph();
            List<Integer> attachmentPoints = FusaneDualExpander.makeSixCycle(g, labels);
            for (int attachPoint : attachmentPoints) {
                System.out.println(FusaneDualExpander.makeLoop(g, attachPoint, labels));
            }
            System.out.println(g);
        }
    }
    
    @Test
    public void tree_5_13_5Test() {
        FusaneInnerDual dual = new FusaneInnerDual(new Graph("0:1,0:2"));
        dual.setLabel(0, 1, 3);
        dual.setLabel(1, 5);
        dual.setLabel(2, 5);
        System.out.println(dual);
        Graph g = FusaneDualExpander.expand(dual);
        System.out.println(g);
    }
    
    @Test
    public void planarChiralTest() {
        FusaneInnerDual dual = new FusaneInnerDual(new Graph("0:1,0:2,0:3,1:6,1:8,3:4,3:5,6:7,8:9"));
        dual.setLabel(0, 1, 1, 1);
        dual.setLabel(1, 1, 1, 1);
        dual.setLabel(2, 5);
        dual.setLabel(3, 1, 1, 1);
        dual.setLabel(4, 5);
        dual.setLabel(5, 5);
        dual.setLabel(6, 1, 3);
        dual.setLabel(7, 5);
        dual.setLabel(8, 2, 2);
        dual.setLabel(9, 5);
        Graph g = FusaneDualExpander.expand(dual);
        System.out.println(g);
    }
    
    @Test
    public void bugTest() {
        Graph t = new Graph("0:1, 1:2, 1:3, 0:4, 4:5");
        int counter = 0;
        for (FusaneInnerDual dual : SimpleFusaneLabeler.label(t)) {
            Graph g = FusaneDualExpander.expand(dual);
            System.out.println(counter + "\t" + dual + "\t" + g);
            counter++;
        }
    }
    
    @Test
    public void threesTest() {
        Graph t = new Graph("0:1,0:2");
        int counter = 0;
        for (FusaneInnerDual dual : SimpleFusaneLabeler.label(t)) {
            Graph g = FusaneDualExpander.expand(dual);
            System.out.println(counter + "\t" + dual + "\t" + g);
            counter++;
        }
    }
    
    public void allTreesOnNVerticesTest(int n) {
        MaxDegreeFilter filter = new MaxDegreeFilter(3);
        int counter = 0;
        List<String> certs = new ArrayList<String>();
        List<String> graphStrings = new ArrayList<String>();
        for (Graph tree : WROMTreeGenerator.generate(n)) {
            // certificate destroys graph, so we need to clone it
            String cert = TreeCertificateMaker.treeToCertificate(new Graph(tree));  
            System.out.println(tree + "\t" + cert);
            if (certs.contains(cert)) {
                continue;
            } else {
                certs.add(cert);
            }
            if (filter.filter(tree)) {
                for (FusaneInnerDual dual : SimpleFusaneLabeler.label(tree)) {
                    System.out.print(counter + "\t" + tree + "\t" + dual + "\t");
                    Graph g = FusaneDualExpander.expand(dual);
                    System.out.println(g);
                    String es = g.getSortedEdgeString();
                    if (!graphStrings.contains(es)) { graphStrings.add(es); }   // acts as a crude graph cert
                    counter++;
                }
            }
        }
        counter = 0;
        for (String es : graphStrings) {
            System.out.println(counter + "\t" + es);
            counter++;
        }
    }
    
    @Test
    public void allTreesOn3VerticesTest() {
        allTreesOnNVerticesTest(3);
    }
    
    @Test
    public void allTreesOn4VerticesTest() {
        allTreesOnNVerticesTest(4);
    }
    
    @Test
    public void allTreesOn6VerticesTest() {
        allTreesOnNVerticesTest(6);
    }
    
    @Test
    public void allTreesOn7VerticesTest() {
        allTreesOnNVerticesTest(7);
    }
    
}
