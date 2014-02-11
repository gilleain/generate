package mast;

import java.math.BigInteger;

public class AbstractAlgorithm {

    public BigInteger factorial(BigInteger k) {
        BigInteger one = BigInteger.valueOf(1);
        if (k.equals(one)) {
            return BigInteger.valueOf(1);
        } else {
            return k.multiply(factorial(k.subtract(one)));  
        }
    }
    
}
