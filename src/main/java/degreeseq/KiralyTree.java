package degreeseq;

import graph.model.IntEdge;
import graph.model.IntGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class KiralyTree {
    
    public class TreeNode {
        
        public int id;
        
        public int r;
        
        public TreeNode parent;
        
        public TreeNode leftChild;
        
        public TreeNode rightChild;
        
        public int[] currentDegSeq;
        
        public boolean hasRedLeftChild;
        
        public boolean hasRedRightChild;
        
        public boolean isLeftChild;
        
        public boolean isRed;
        
        public BitSet chiP;
        
        public TreeNode(int r, int s) {
            this(null, r, s, false, 0);
        }
        
        public TreeNode(TreeNode parent, int r, int s, boolean isLeftChild,
                int id) {
            this.id = id;
            this.r = r;
            this.parent = parent;
            this.isLeftChild = isLeftChild;
            if (r < s) {
                leftChild = new TreeNode(this, r + 1, s, true, id + 1);
                rightChild = new TreeNode(this, r + 1, s, false,
                        leftChild.rightmostLeaf().id + 1);
            }
        }
        
        public int getA() {
            return chiP.cardinality();
        }
        
        public TreeNode rightmostLeaf() {
            if (isLeaf()) {
                return this;
            } else {
                return rightChild.rightmostLeaf();
            }
        }
        
        public boolean isLeaf() {
            return leftChild == null; // could check both...
        }
        
        public String toString() {
            if (isLeaf()) {
                return "[" + id + "]";
            } else {
                return "[" + id + "](" + leftChild + "," + rightChild + ")";
            }
        }
        
    }
    
    private TreeNode root;
    
    private KiralyTreeListener listener;
    
    public KiralyTree(int s, KiralyTreeListener listener) {
        root = new TreeNode(0, s);
        this.listener = listener;
    }
    
    public TreeNode getLeftmostLeaf(int d) {
        return moveToLeftmostLeaf(d, root);
    }
    
    private TreeNode moveToLeftmostLeaf(int d, TreeNode t) {
        t.isRed = true;
        if (t.isLeaf()) {
            return t;
        } else {
            if (d > 0) {
                return moveToLeftmostLeaf(d - 1, t.leftChild);
            } else {
                return moveToLeftmostLeaf(0, t.rightChild);
            }
        }
    }
    
    private void handleLeaf(int[] degreeSequence, TreeNode current, BitSet chiP, IntGraph g, int n, int degree) {
        if (chiP.cardinality() == degree) {
            listener.handle(reduce(degreeSequence, chiP, n - 1), g);
        }
        if (current.isLeftChild) {
            upFromLeft(degreeSequence, current.parent, chiP, g, n, degree);
        } else {
            upFromRight(degreeSequence, current.parent, chiP, g, n, degree);
        }
    }
    
    public void traverse(int[] degreeSequence, IntGraph g, int n, int degree) {
        TreeNode startLeaf = this.getLeftmostLeaf(degree);
        BitSet chiP = new BitSet();
        for (int i = 0; i < degree; i++) {
            chiP.set(i);
        }
        setEdges(g, chiP, n - 1);
        // System.out.println("traversing from current " + startLeaf + " n= " +
        // n + " degree = " + degree + " chiP " + chiP);
        handleLeaf(degreeSequence, startLeaf, chiP, g, n, degree);
    }
    
    private void traverseTreeDown(int[] degreeSequence, TreeNode current, BitSet chiP, IntGraph g, int n, int degree) {
        // System.out.println("DDD " + String.format("%5s", n + ":" +
        // current.id) + " " + Arrays.toString(degreeSequence) + " " + degree +
        // " " + chiP + " " + g);
        if (current.isLeaf()) {
            handleLeaf(degreeSequence, current, chiP, g, n, degree);
        } else {
            if (chiP.cardinality() < degree && isGraphical(degreeSequence, chiP, n - 1)) { // left is red
                g.makeEdge(current.r, n - 1);
                chiP.set(current.r);
                traverseTreeDown(degreeSequence, current.leftChild, chiP, g, n, degree);
            } else {
                traverseTreeDown(degreeSequence, current.rightChild, chiP, g, n, degree);
            }
        }
    }
    
    private void upFromLeft(int[] degreeSequence, TreeNode current, BitSet chiP, IntGraph g, int n, int degree) {
        chiP.clear(current.r);
        removeLast(g);
        // System.out.println("UFL " + String.format("%5s", n + ":" +
        // current.id) + " " + Arrays.toString(degreeSequence) + " " + degree +
        // " " + chiP + " " + g);
        if (chiP.cardinality() < degree) { // extension is possible
            traverseTreeDown(degreeSequence, current.rightChild, chiP, g, n, degree);
        } else {
            if (current.isLeftChild) {
                upFromLeft(degreeSequence, current.parent, chiP, g, n, degree);
            } else {
                upFromRight(degreeSequence, current.parent, chiP, g, n, degree);
            }
        }
    }
    
    private void upFromRight(int[] degreeSequence, TreeNode current, BitSet chiP, IntGraph g, int n, int degree) {
        // System.out.println("UFR " + String.format("%5s", n + ":" +
        // current.id) + " " + Arrays.toString(degreeSequence) + " " + degree +
        // " " + chiP + " " + g);
        if (current.parent == null) {
            // System.out.println("At root" + g);
            return; // reached the root
        } else {
            if (current.isLeftChild) {
                upFromLeft(degreeSequence, current.parent, chiP, g, n, degree);
            } else {
                upFromRight(degreeSequence, current.parent, chiP, g, n, degree);
            }
        }
    }
    
    private void setEdges(IntGraph g, BitSet chiP, int n) {
        for (int i = chiP.nextSetBit(0); i >= 0; i = chiP.nextSetBit(i + 1)) {
            if (g.hasEdge(i, n)) {
                continue;
            } else {
                g.makeEdge(i, n);
            }
        }
    }
    
    public IntGraph connectRemaining(int[] degreeSequence, IntGraph p) {
        IntGraph g = new IntGraph(p);
        // XXX - there must be a better way...
        // System.out.print("LEAF " + Arrays.toString(degreeSequence));
        boolean complete = false;
        if (hasPattern(degreeSequence, 0, 0, 0)) {
            complete = true;
        } else if (hasPattern(degreeSequence, 1, 1, 2)) {
            g.makeEdge(0, 2);
            g.makeEdge(1, 2);
            complete = true;
        } else if (hasPattern(degreeSequence, 1, 2, 1)) {
            g.makeEdge(0, 1);
            g.makeEdge(1, 2);
            complete = true;
        } else if (hasPattern(degreeSequence, 2, 1, 1)) {
            g.makeEdge(0, 1);
            g.makeEdge(0, 2);
            complete = true;
        } else if (hasPattern(degreeSequence, 0, 1, 1)) {
            g.makeEdge(1, 2);
            complete = true;
        } else if (hasPattern(degreeSequence, 1, 0, 1)) {
            g.makeEdge(0, 2);
            complete = true;
        } else if (hasPattern(degreeSequence, 1, 1, 0)) {
            g.makeEdge(0, 1);
            complete = true;
        } else if (hasPattern(degreeSequence, 2, 2, 2)) {
            g.makeEdge(0, 1);
            g.makeEdge(0, 2);
            g.makeEdge(1, 2);
            complete = true;
        }
        
        if (complete) {
            // System.out.println("\t" + g.getEdgeStringWithEdgeOrder() +
            // " COMPLETE");
//            listener.handle(degreeSequence, g);
            return g;
        } else {
            // System.out.println("\t" + g.getEdgeStringWithEdgeOrder() +
            // " INCOMPLETE");
            return null;
        }
    }
    
    private boolean hasPattern(int[] degreeSequence, int i0, int i1, int i2) {
        return degreeSequence[0] == i0 && degreeSequence[1] == i1
                && degreeSequence[2] == i2;
    }
    
    private void removeLast(IntGraph g) {
        List<IntEdge> edges = new ArrayList<IntEdge>();
        for (int i = 0; i < g.esize() - 1; i++) {
            edges.add(g.edges.get(i));
        }
        g.edges = edges;
    }
    
    private boolean isGraphical(int[] degreeSequence, BitSet chiP, int n) {
        // TODO : replace with linear HH
        return HakimiHavelGenerator.isGraphical(sort(reduce(degreeSequence,chiP, n)));
    }
    
    private int[] reduce(int[] degreeSequence, BitSet chiP, int n) {
        int[] reducedDegreeSeq = new int[degreeSequence.length];
        for (int i = 0; i < degreeSequence.length; i++) {
            if (chiP.get(i)) {
                reducedDegreeSeq[i] = degreeSequence[i] - 1;
            } else if (i == n) {
                reducedDegreeSeq[i] = degreeSequence[i] - chiP.cardinality();
            } else {
                reducedDegreeSeq[i] = degreeSequence[i];
            }
        }
        return reducedDegreeSeq;
    }
    
    private int[] sort(int[] arr) {
        int[] sortedArr = new int[arr.length];
        System.arraycopy(arr, 0, sortedArr, 0, arr.length);
        Arrays.sort(sortedArr);
        return sortedArr;
    }
    
    public String toString() {
        return root.toString();
    }
    
}
