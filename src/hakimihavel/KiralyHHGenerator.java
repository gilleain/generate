package hakimihavel;

import generate.handler.GeneratorHandler;
import generate.handler.SystemOutHandler;

import java.util.BitSet;

import model.Graph;

public class KiralyHHGenerator {
    
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
        
        public TreeNode(TreeNode parent, int r, int s, boolean isLeftChild, int id) {
            this.id = id;
            this.r = r;
            this.parent = parent;
            this.isLeftChild = isLeftChild;
            if (r < s) {
                leftChild  = new TreeNode(this, r + 1, s, true, id + 1);
                rightChild = new TreeNode(this, r + 1, s, false, leftChild.rightmostLeaf().id + 1);
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
            return leftChild == null;   // could check both...
        }
        
        public String toString() {
            if (isLeaf()) {
                return "[" + id + "]";
            } else {
                return "[" + id + "](" + leftChild + "," + rightChild + ")";
            }
        }
        
    }
    
    public class RedBlackTree {
        
        public TreeNode root;
        
        public RedBlackTree(int s) {
            root = new TreeNode(0, s);
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
        
        public String toString() {
            return root.toString();
        }
    }
    
    private GeneratorHandler handler;
    
    private int[] degreeSequence;
    
    public KiralyHHGenerator() {
        this(new SystemOutHandler());
    }
    
    public KiralyHHGenerator(GeneratorHandler handler) {
        this.handler = handler;
    }
    
    public void generate(int[] degreeSequence) {
        generate(degreeSequence, degreeSequence.length, new Graph());
    }
    
    public void generate(int[] degreeSequence, int n, Graph g) {
//        System.out.println("recursing " + n + " " + degreeSequence[n - 1] + " " + g);
        if (n == 2) {
            handler.handle(null, g);
            return;
        }
        this.degreeSequence = degreeSequence;
        int dN = degreeSequence[n - 1];
        RedBlackTree tree = new RedBlackTree(n - 1);
        traverse(tree, g, n, dN);
    }
    
    private void setEdges(Graph g, BitSet chiP, int n) {
        for (int i = chiP.nextSetBit(0); i >= 0; i = chiP.nextSetBit(i+1)) {
            g.makeEdge(i, n);
        }
    }
    
    private void handleLeaf(RedBlackTree tree, TreeNode current, BitSet chiP, Graph g, int n, int degree) {
        if (chiP.cardinality() == degree) {
            setEdges(g, chiP, n - 1);
//            System.out.println("Leaf " + n + ":" + current + "\t" + chiP + "\t" + g.getSortedEdgeString());
//            System.out.println("Leaf " + n + ":" + current + "\t" + chiP + "\t" + g);
//        handler.handle(null, g);
            generate(degreeSequence, n - 1, new Graph(g));
        } 
        if (current.isLeftChild) {
            upFromLeft(tree, current.parent, chiP, g, n, degree);
        } else {
            upFromRight(tree, current.parent, chiP, g, n, degree);
        }
    }
    
    private void traverse(RedBlackTree tree, Graph g, int n, int degree) {
        TreeNode startLeaf = tree.getLeftmostLeaf(degree);
        BitSet chiP = new BitSet();
        for (int i = 0; i < degree; i++) { chiP.set(i); }
//        System.out.println("traversing from current " + startLeaf + " n= " + n + " degree = " + degree + " chiP " + chiP);
        handleLeaf(tree, startLeaf, chiP, g, n, degree);
    }

    private void traverseTreeDown(RedBlackTree tree, TreeNode current, BitSet chiP, Graph g, int n, int degree) {
//        System.out.println("D " + current.id + " " + chiP + " " + n + " " + degree);
        if (current.isLeaf()) {
           handleLeaf(tree, current, chiP, g, n, degree);
        } else {
//            if (chiP.cardinality() < degree && isGraphical(chiP)) {    // left is red
            if (chiP.cardinality() < degree) {
                chiP.set(current.r);
                traverseTreeDown(tree, current.leftChild, chiP, g, n, degree);
            } else {
                traverseTreeDown(tree, current.rightChild, chiP, g, n, degree);
            }
        }
    }

    private void upFromLeft(RedBlackTree tree, TreeNode current, BitSet chiP, Graph g, int n, int degree) {
        chiP.clear(current.r);
        g.edges = g.edges.subList(0, g.esize());
//        System.out.println("UFL " + current.id + " " + chiP);
        if (chiP.cardinality() < degree) {    // extension is possible
            traverseTreeDown(tree, current.rightChild, chiP, g, n, degree);
        } else {
            if (current.isLeftChild) {
                upFromLeft(tree, current.parent, chiP, g, n, degree);
            } else {
                upFromRight(tree, current.parent, chiP, g, n, degree);
            }
        }
    }
    
    private void upFromRight(RedBlackTree tree, TreeNode current, BitSet chiP, Graph g, int n, int degree) {
//        System.out.println("UFR " + current.id + " " + chiP);
        if (current.parent == null) {
            return;    // reached the root
        } else {
            if (current.isLeftChild) {
                upFromLeft(tree, current.parent, chiP, g, n, degree);
            } else {
                upFromRight(tree, current.parent, chiP, g, n, degree);
            }
        }
    }
    
    private boolean isGraphical(BitSet chiP) {
        int[] reducedDegreeSeq = new int[degreeSequence.length];
        for (int i = 0; i < degreeSequence.length; i++) {
            if (chiP.get(i)) {
                reducedDegreeSeq[i] = degreeSequence[i] - 1;
            } else {
                reducedDegreeSeq[i] = degreeSequence[i];
            }
        }
        return HakimiHavelGenerator.isGraphical(reducedDegreeSeq);  // TODO : replace with linear HH
    }
    
}
