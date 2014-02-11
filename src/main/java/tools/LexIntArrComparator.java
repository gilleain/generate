package tools;

import java.util.Comparator;

/**
 * Lexicographically sort integer arrays.
 * 
 * @author maclean
 *
 */
public class LexIntArrComparator implements Comparator<int[]> {

        @Override
        public int compare(int[] arrA, int[] arrB) {
            if (arrA.length != arrB.length) {
                return new Integer(arrA.length).compareTo(new Integer(arrB.length));
            } else {
                for (int i = 0 ; i < arrA.length; i++) {
                    if (arrA[i] < arrB[i]) {
                        return -1;
                    } else if (arrA[i] > arrB[i]) {
                        return 1;
                    }
                }
            }
            return 0;
        }
        
    }