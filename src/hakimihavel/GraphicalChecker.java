package hakimihavel;

/**
 * Based on JK Senior's 1951 paper in American Journal of Mathematics.
 * 
 * CHECKS FOR NON-SIMPLE GRAPHS!
 *  
 * @author maclean
 *
 */
public class GraphicalChecker {
    
    public enum Graphicality {
        NON_CONNECTED_OR_HAS_LOOPS,
        UNIGRAPHICAL_2_1,
        UNIGRAPHICAL_2_2,
        UNIGRAPHICAL_2_3,
        UNIGRAPHICAL_2_4,
        UNIGRAPHICAL_2_5,
        UNIGRAPHICAL_2_6_1,
        UNIGRAPHICAL_2_6_2,
        UNIGRAPHICAL_2_6_3,
        MULTIGRAPHICAL
    };
    
    public static Graphicality check(int[] degreeSequence) {
        int n = degreeSequence.length;
        int halfSum = getSum(degreeSequence) / 2;   // assumes even: one of the graphical conditions
        int fr = halfSum - degreeSequence[0];       // assumes sorted
        int ft = halfSum - (n - 1);
        if (ft < 0 || fr < 0) return Graphicality.NON_CONNECTED_OR_HAS_LOOPS;   // !ZCL(P)
        
        if (fr == 0) return Graphicality.UNIGRAPHICAL_2_1;                       // Th 2.1
        if (n == 3) return Graphicality.UNIGRAPHICAL_2_2;                        // Th 2.2
        if (degreeSequence[2] == 1) return Graphicality.UNIGRAPHICAL_2_3;        // Th 2.3
        if (degreeSequence[0] == 2) return Graphicality.UNIGRAPHICAL_2_4;        // Th 2.4
        if (fr == 1 && degreeSequence[1] 
                    == degreeSequence[n - 1]) {         // Th 2.5
            return Graphicality.UNIGRAPHICAL_2_5;
        }
        if (degreeSequence[0] == degreeSequence[2]) {   // Th 2.6
            if (degreeSequence[3] == 1) return Graphicality.UNIGRAPHICAL_2_6_1;
            if (n == 4) return Graphicality.UNIGRAPHICAL_2_6_2;
            if (ft == 0) return Graphicality.UNIGRAPHICAL_2_6_3;
        }
        
        return Graphicality.MULTIGRAPHICAL;
    }

    private static int getSum(int[] degreeSequence) {
        int sum = 0;
        for (int i : degreeSequence) {
            sum += i;
        }
        return sum;
    }
    
}
