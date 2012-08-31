package degreeseq;

import java.util.Arrays;


public class BipartiteGraphicalChecker {
    
    public static boolean isBigraphical(int[] p, int[] q) {
        return dominates(transpose(p), q);
    }
    
    /**
     * Check to see if p dominates q, which is true if the sum at position i 
     * for p is greater than or equal to the sum at i in q.
     *   
     * @param p an integer partition
     * @param q an integer partition
     * @return true if p dominates q
     */
    public static boolean dominates(int[] p, int[] q) {
        int max = Math.min(p.length, q.length);
        int pSum = 0;
        int qSum = 0;
        for (int i = 0; i < max; i++) {
            pSum += p[i];
            qSum += q[i];
            if (pSum < qSum) {
                return false;
            }
        }
        return true;
    }
    
    public static int[] transpose(int[] p) {
        int n = p.length;
        int m = p[0];
        int[] pStar = new int[m];
        Arrays.fill(pStar, 1);
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < p[i]; j++) {
                pStar[j] += 1;
            }
        }
        return pStar;
    }
    
}
