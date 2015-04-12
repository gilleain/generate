package degreeseq;

import java.util.Arrays;

import org.junit.Test;

public class BipartiteGraphicalCheckerTest {
    
    @Test
    public void transposeTest() {
        int[] p = new int[] { 3, 2, 2, 2, 1 };
        int[] pStar = BipartiteGraphicalChecker.transpose(p);
        System.out.println(Arrays.toString(pStar));
    }
    
    public void checkDominance(int[] p, int[] q, String pName, String qName) {
        String dominateString = 
            BipartiteGraphicalChecker.dominates(p, q)? "dominates" : "!dominate";
        System.out.println(pName + "\t" 
                + dominateString + "\t" 
                + qName + "\t"
                + Arrays.toString(p) + "\t" 
                + Arrays.toString(q)
                );
    }
    
    @Test
    public void dominateTest() {
        int[] p = new int[] { 5, 4, 1 };
        int[] q = new int[] { 3, 3, 3, 1 };
        checkDominance(p, q, "p", "q");
        checkDominance(q, p, "q", "p");
        int[] pStar = BipartiteGraphicalChecker.transpose(p);
        int[] qStar = BipartiteGraphicalChecker.transpose(q);
        checkDominance(pStar, qStar, "p*", "q*");
        checkDominance(qStar, pStar, "q*", "p*");
        checkDominance(p, qStar, "p", "q*");
        checkDominance(q, pStar, "q", "p*");
    }
    
    @Test
    public void bugTest() {
        int[] p = new int[] { 2, 2, 2, 1 };
        int[] q = new int[] { 3, 1, 1, 1, 1 };
        checkDominance(p, q, "p", "q");
        checkDominance(q, p, "q", "p");
    }
    
}
