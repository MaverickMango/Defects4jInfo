package org.apache.commons.math.util;


public final class MathUtils {
    public static final double EPSILON = 1.1102230246251565E-16;

    public static final double SAFE_MIN = 2.2250738585072014E-308;

    public static final double TWO_PI = 2 * (FastMath.PI);

    private static final byte NB = ((byte) (-1));

    private static final short NS = ((short) (-1));

    private static final byte PB = ((byte) (1));

    private static final short PS = ((short) (1));

    private static final byte ZB = ((byte) (0));

    private static final short ZS = ((short) (0));

    private static final int NAN_GAP = (4 * 1024) * 1024;

    private static final long SGN_MASK = -9223372036854775808L;

    private static final long[] FACTORIALS = new long[]{ 1L, 1L, 2L, 6L, 24L, 120L, 720L, 5040L, 40320L, 362880L, 3628800L, 39916800L, 479001600L, 6227020800L, 87178291200L, 1307674368000L, 20922789888000L, 355687428096000L, 6402373705728000L, 121645100408832000L, 2432902008176640000L };

    private MathUtils() {
        super();
    }

    public static int addAndCheck(int x, int y) {
        long s = ((long) (x)) + ((long) (y));
        if ((s < (java.lang.Integer.MIN_VALUE)) || (s > (java.lang.Integer.MAX_VALUE))) {
            throw org.apache.commons.math.MathRuntimeException.createArithmeticException(LocalizedFormats.OVERFLOW_IN_ADDITION, x, y);
        }
        return ((int) (s));
    }

    public static long addAndCheck(long a, long b) {
        return org.apache.commons.math.util.MathUtils.addAndCheck(a, b, LocalizedFormats.OVERFLOW_IN_ADDITION);
    }

    private static long addAndCheck(long a, long b, org.apache.commons.math.exception.util.Localizable pattern) {
        long ret;
        if (a > b) {
            ret = org.apache.commons.math.util.MathUtils.addAndCheck(b, a, pattern);
        }else {
            if (a < 0) {
                if (b < 0) {
                    if (((java.lang.Long.MIN_VALUE) - b) <= a) {
                        ret = a + b;
                    }else {
                        throw org.apache.commons.math.MathRuntimeException.createArithmeticException(pattern, a, b);
                    }
                }else {
                    ret = a + b;
                }
            }else {
                if (a <= ((java.lang.Long.MAX_VALUE) - b)) {
                    ret = a + b;
                }else {
                    throw org.apache.commons.math.MathRuntimeException.createArithmeticException(pattern, a, b);
                }
            }
        }
        return ret;
    }

    public static long binomialCoefficient(final int n, final int k) {
        org.apache.commons.math.util.MathUtils.checkBinomial(n, k);
        if ((n == k) || (k == 0)) {
            return 1;
        }
        if ((k == 1) || (k == (n - 1))) {
            return n;
        }
        if (k > (n / 2))
            return org.apache.commons.math.util.MathUtils.binomialCoefficient(n, (n - k));

        long result = 1;
        if (n <= 61) {
            int i = (n - k) + 1;
            for (int j = 1; j <= k; j++) {
                result = (result * i) / j;
                i++;
            }
        }else
            if (n <= 66) {
                int i = (n - k) + 1;
                for (int j = 1; j <= k; j++) {
                    final long d = org.apache.commons.math.util.MathUtils.gcd(i, j);
                    result = (result / (j / d)) * (i / d);
                    i++;
                }
            }else {
                int i = (n - k) + 1;
                for (int j = 1; j <= k; j++) {
                    final long d = org.apache.commons.math.util.MathUtils.gcd(i, j);
                    result = org.apache.commons.math.util.MathUtils.mulAndCheck((result / (j / d)), (i / d));
                    i++;
                }
            }

        return result;
    }

    public static double binomialCoefficientDouble(final int n, final int k) {
        org.apache.commons.math.util.MathUtils.checkBinomial(n, k);
        if ((n == k) || (k == 0)) {
            return 1.0;
        }
        if ((k == 1) || (k == (n - 1))) {
            return n;
        }
        if (k > (n / 2)) {
            return org.apache.commons.math.util.MathUtils.binomialCoefficientDouble(n, (n - k));
        }
        if (n < 67) {
            return org.apache.commons.math.util.MathUtils.binomialCoefficient(n, k);
        }
        double result = 1.0;
        for (int i = 1; i <= k; i++) {
            result *= ((double) ((n - k) + i)) / ((double) (i));
        }
        return org.apache.commons.math.util.FastMath.floor((result + 0.5));
    }

    public static double binomialCoefficientLog(final int n, final int k) {
        org.apache.commons.math.util.MathUtils.checkBinomial(n, k);
        if ((n == k) || (k == 0)) {
            return 0;
        }
        if ((k == 1) || (k == (n - 1))) {
            return org.apache.commons.math.util.FastMath.log(n);
        }
        if (n < 67) {
            return org.apache.commons.math.util.FastMath.log(org.apache.commons.math.util.MathUtils.binomialCoefficient(n, k));
        }
        if (n < 1030) {
            return org.apache.commons.math.util.FastMath.log(org.apache.commons.math.util.MathUtils.binomialCoefficientDouble(n, k));
        }
        if (k > (n / 2)) {
            return org.apache.commons.math.util.MathUtils.binomialCoefficientLog(n, (n - k));
        }
        double logSum = 0;
        for (int i = (n - k) + 1; i <= n; i++) {
            logSum += org.apache.commons.math.util.FastMath.log(i);
        }
        for (int i = 2; i <= k; i++) {
            logSum -= org.apache.commons.math.util.FastMath.log(i);
        }
        return logSum;
    }

    private static void checkBinomial(final int n, final int k) throws java.lang.IllegalArgumentException {
        if (n < k) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(LocalizedFormats.BINOMIAL_INVALID_PARAMETERS_ORDER, n, k);
        }
        if (n < 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(LocalizedFormats.BINOMIAL_NEGATIVE_PARAMETER, n);
        }
    }

    public static int compareTo(double x, double y, double eps) {
        if (org.apache.commons.math.util.MathUtils.equals(x, y, eps)) {
            return 0;
        }else
            if (x < y) {
                return -1;
            }

        return 1;
    }

    public static double cosh(double x) {
        return ((org.apache.commons.math.util.FastMath.exp(x)) + (org.apache.commons.math.util.FastMath.exp((-x)))) / 2.0;
    }

    public static boolean equals(double x, double y) {
        return org.apache.commons.math.util.MathUtils.equals(x, y, 1);
    }

    public static boolean equalsIncludingNaN(double x, double y) {
        return ((java.lang.Double.isNaN(x)) && (java.lang.Double.isNaN(y))) || (org.apache.commons.math.util.MathUtils.equals(x, y, 1));
    }

    public static boolean equals(double x, double y, double eps) {
        return (org.apache.commons.math.util.MathUtils.equals(x, y, 1)) || ((org.apache.commons.math.util.FastMath.abs((y - x))) <= eps);
    }

    public static boolean equalsIncludingNaN(double x, double y, double eps) {
        return (org.apache.commons.math.util.MathUtils.equalsIncludingNaN(x, y)) || ((org.apache.commons.math.util.FastMath.abs((y - x))) <= eps);
    }

    public static boolean equals(double x, double y, int maxUlps) {
        assert (maxUlps > 0) && (maxUlps < (org.apache.commons.math.util.MathUtils.NAN_GAP));
        long xInt = java.lang.Double.doubleToLongBits(x);
        long yInt = java.lang.Double.doubleToLongBits(y);
        if (xInt < 0) {
            xInt = (org.apache.commons.math.util.MathUtils.SGN_MASK) - xInt;
        }
        if (yInt < 0) {
            yInt = (org.apache.commons.math.util.MathUtils.SGN_MASK) - yInt;
        }
        final boolean isEqual = (org.apache.commons.math.util.FastMath.abs((xInt - yInt))) <= maxUlps;
        return (isEqual && (!(java.lang.Double.isNaN(x)))) && (!(java.lang.Double.isNaN(y)));
    }

    public static boolean equalsIncludingNaN(double x, double y, int maxUlps) {
        return ((java.lang.Double.isNaN(x)) && (java.lang.Double.isNaN(y))) || (org.apache.commons.math.util.MathUtils.equals(x, y, maxUlps));
    }

    public static boolean equals(double[] x, double[] y) {
        if ((x == null) || (y == null)) {
            return !((x == null) ^ (y == null));
        }
        if ((x.length) != (y.length)) {
            return false;
        }
        for (int i = 0; i < (x.length); ++i) {
            if (!(org.apache.commons.math.util.MathUtils.equals(x[i], y[i]))) {
                return false;
            }
        }
        return true;
    }

    public static boolean equalsIncludingNaN(double[] x, double[] y) {
        if ((x == null) || (y == null)) {
            return !((x == null) ^ (y == null));
        }
        if ((x.length) != (y.length)) {
            return false;
        }
        for (int i = 0; i < (x.length); ++i) {
            if (!(org.apache.commons.math.util.MathUtils.equalsIncludingNaN(x[i], y[i]))) {
                return false;
            }
        }
        return true;
    }

    public static long factorial(final int n) {
        if (n < 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(LocalizedFormats.FACTORIAL_NEGATIVE_PARAMETER, n);
        }
        if (n > 20) {
            throw new java.lang.ArithmeticException("factorial value is too large to fit in a long");
        }
        return org.apache.commons.math.util.MathUtils.FACTORIALS[n];
    }

    public static double factorialDouble(final int n) {
        if (n < 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(LocalizedFormats.FACTORIAL_NEGATIVE_PARAMETER, n);
        }
        if (n < 21) {
            return org.apache.commons.math.util.MathUtils.factorial(n);
        }
        return org.apache.commons.math.util.FastMath.floor(((org.apache.commons.math.util.FastMath.exp(org.apache.commons.math.util.MathUtils.factorialLog(n))) + 0.5));
    }

    public static double factorialLog(final int n) {
        if (n < 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(LocalizedFormats.FACTORIAL_NEGATIVE_PARAMETER, n);
        }
        if (n < 21) {
            return org.apache.commons.math.util.FastMath.log(org.apache.commons.math.util.MathUtils.factorial(n));
        }
        double logSum = 0;
        for (int i = 2; i <= n; i++) {
            logSum += org.apache.commons.math.util.FastMath.log(i);
        }
        return logSum;
    }

    public static int gcd(final int p, final int q) {
        int u = p;
        int v = q;
        if ((u == 0) || (v == 0)) {
            if ((u == (java.lang.Integer.MIN_VALUE)) || (v == (java.lang.Integer.MIN_VALUE))) {
                throw org.apache.commons.math.MathRuntimeException.createArithmeticException(LocalizedFormats.GCD_OVERFLOW_32_BITS, p, q);
            }
            return (org.apache.commons.math.util.FastMath.abs(u)) + (org.apache.commons.math.util.FastMath.abs(v));
        }
        if (u > 0) {
            u = -u;
        }
        if (v > 0) {
            v = -v;
        }
        int k = 0;
        while ((((u & 1) == 0) && ((v & 1) == 0)) && (k < 31)) {
            u /= 2;
            v /= 2;
            k++;
        } 
        if (k == 31) {
            throw org.apache.commons.math.MathRuntimeException.createArithmeticException(LocalizedFormats.GCD_OVERFLOW_32_BITS, p, q);
        }
        int t = ((u & 1) == 1) ? v : -(u / 2);
        do {
            while ((t & 1) == 0) {
                t /= 2;
            } 
            if (t > 0) {
                u = -t;
            }else {
                v = t;
            }
            t = (v - u) / 2;
        } while (t != 0 );
        return (-u) * (1 << k);
    }

    public static long gcd(final long p, final long q) {
        long u = p;
        long v = q;
        if ((u == 0) || (v == 0)) {
            if ((u == (java.lang.Long.MIN_VALUE)) || (v == (java.lang.Long.MIN_VALUE))) {
                throw org.apache.commons.math.MathRuntimeException.createArithmeticException(LocalizedFormats.GCD_OVERFLOW_64_BITS, p, q);
            }
            return (org.apache.commons.math.util.FastMath.abs(u)) + (org.apache.commons.math.util.FastMath.abs(v));
        }
        if (u > 0) {
            u = -u;
        }
        if (v > 0) {
            v = -v;
        }
        int k = 0;
        while ((((u & 1) == 0) && ((v & 1) == 0)) && (k < 63)) {
            u /= 2;
            v /= 2;
            k++;
        } 
        if (k == 63) {
            throw org.apache.commons.math.MathRuntimeException.createArithmeticException(LocalizedFormats.GCD_OVERFLOW_64_BITS, p, q);
        }
        long t = ((u & 1) == 1) ? v : -(u / 2);
        do {
            while ((t & 1) == 0) {
                t /= 2;
            } 
            if (t > 0) {
                u = -t;
            }else {
                v = t;
            }
            t = (v - u) / 2;
        } while (t != 0 );
        return (-u) * (1L << k);
    }

    public static int hash(double value) {
        return new java.lang.Double(value).hashCode();
    }

    public static int hash(double[] value) {
        return java.util.Arrays.hashCode(value);
    }

    public static byte indicator(final byte x) {
        return x >= (org.apache.commons.math.util.MathUtils.ZB) ? org.apache.commons.math.util.MathUtils.PB : org.apache.commons.math.util.MathUtils.NB;
    }

    public static double indicator(final double x) {
        if (java.lang.Double.isNaN(x)) {
            return java.lang.Double.NaN;
        }
        return x >= 0.0 ? 1.0 : -1.0;
    }

    public static float indicator(final float x) {
        if (java.lang.Float.isNaN(x)) {
            return java.lang.Float.NaN;
        }
        return x >= 0.0F ? 1.0F : -1.0F;
    }

    public static int indicator(final int x) {
        return x >= 0 ? 1 : -1;
    }

    public static long indicator(final long x) {
        return x >= 0L ? 1L : -1L;
    }

    public static short indicator(final short x) {
        return x >= (org.apache.commons.math.util.MathUtils.ZS) ? org.apache.commons.math.util.MathUtils.PS : org.apache.commons.math.util.MathUtils.NS;
    }

    public static int lcm(int a, int b) {
        if ((a == 0) || (b == 0)) {
            return 0;
        }
        int lcm = org.apache.commons.math.util.FastMath.abs(org.apache.commons.math.util.MathUtils.mulAndCheck((a / (org.apache.commons.math.util.MathUtils.gcd(a, b))), b));
        if (lcm == (java.lang.Integer.MIN_VALUE)) {
            throw org.apache.commons.math.MathRuntimeException.createArithmeticException(LocalizedFormats.LCM_OVERFLOW_32_BITS, a, b);
        }
        return lcm;
    }

    public static long lcm(long a, long b) {
        if ((a == 0) || (b == 0)) {
            return 0;
        }
        long lcm = org.apache.commons.math.util.FastMath.abs(org.apache.commons.math.util.MathUtils.mulAndCheck((a / (org.apache.commons.math.util.MathUtils.gcd(a, b))), b));
        if (lcm == (java.lang.Long.MIN_VALUE)) {
            throw org.apache.commons.math.MathRuntimeException.createArithmeticException(LocalizedFormats.LCM_OVERFLOW_64_BITS, a, b);
        }
        return lcm;
    }

    public static double log(double base, double x) {
        return (org.apache.commons.math.util.FastMath.log(x)) / (org.apache.commons.math.util.FastMath.log(base));
    }

    public static int mulAndCheck(int x, int y) {
        long m = ((long) (x)) * ((long) (y));
        if ((m < (java.lang.Integer.MIN_VALUE)) || (m > (java.lang.Integer.MAX_VALUE))) {
            throw new java.lang.ArithmeticException("overflow: mul");
        }
        return ((int) (m));
    }

    public static long mulAndCheck(long a, long b) {
        long ret;
        java.lang.String msg = "overflow: multiply";
        if (a > b) {
            ret = org.apache.commons.math.util.MathUtils.mulAndCheck(b, a);
        }else {
            if (a < 0) {
                if (b < 0) {
                    if (a >= ((java.lang.Long.MAX_VALUE) / b)) {
                        ret = a * b;
                    }else {
                        throw new java.lang.ArithmeticException(msg);
                    }
                }else
                    if (b > 0) {
                        if (((java.lang.Long.MIN_VALUE) / b) <= a) {
                            ret = a * b;
                        }else {
                            throw new java.lang.ArithmeticException(msg);
                        }
                    }else {
                        ret = 0;
                    }

            }else
                if (a > 0) {
                    if (a <= ((java.lang.Long.MAX_VALUE) / b)) {
                        ret = a * b;
                    }else {
                        throw new java.lang.ArithmeticException(msg);
                    }
                }else {
                    ret = 0;
                }

        }
        return ret;
    }

    public static double scalb(final double d, final int scaleFactor) {
        if (((d == 0) || (java.lang.Double.isNaN(d))) || (java.lang.Double.isInfinite(d))) {
            return d;
        }
        final long bits = java.lang.Double.doubleToLongBits(d);
        final long exponent = bits & 9218868437227405312L;
        final long rest = bits & -9218868437227405313L;
        final long newBits = rest | (exponent + (((long) (scaleFactor)) << 52));
        return java.lang.Double.longBitsToDouble(newBits);
    }

    public static double normalizeAngle(double a, double center) {
        return a - ((org.apache.commons.math.util.MathUtils.TWO_PI) * (org.apache.commons.math.util.FastMath.floor((((a + (FastMath.PI)) - center) / (org.apache.commons.math.util.MathUtils.TWO_PI)))));
    }

    public static double[] normalizeArray(double[] values, double normalizedSum) throws java.lang.ArithmeticException, java.lang.IllegalArgumentException {
        if (java.lang.Double.isInfinite(normalizedSum)) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(LocalizedFormats.NORMALIZE_INFINITE);
        }
        if (java.lang.Double.isNaN(normalizedSum)) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(LocalizedFormats.NORMALIZE_NAN);
        }
        double sum = 0.0;
        final int len = values.length;
        double[] out = new double[len];
        for (int i = 0; i < len; i++) {
            if (java.lang.Double.isInfinite(values[i])) {
                throw org.apache.commons.math.MathRuntimeException.createArithmeticException(LocalizedFormats.INFINITE_ARRAY_ELEMENT, values[i], i);
            }
            if (!(java.lang.Double.isNaN(values[i]))) {
                sum += values[i];
            }
        }
        if (sum == 0) {
            throw org.apache.commons.math.MathRuntimeException.createArithmeticException(LocalizedFormats.ARRAY_SUMS_TO_ZERO);
        }
        for (int i = 0; i < len; i++) {
            if (java.lang.Double.isNaN(values[i])) {
                out[i] = java.lang.Double.NaN;
            }else {
                out[i] = ((values[i]) * normalizedSum) / sum;
            }
        }
        return out;
    }

    public static double round(double x, int scale) {
        return org.apache.commons.math.util.MathUtils.round(x, scale, java.math.BigDecimal.ROUND_HALF_UP);
    }

    public static double round(double x, int scale, int roundingMethod) {
        try {
            return new java.math.BigDecimal(java.lang.Double.toString(x)).setScale(scale, roundingMethod).doubleValue();
        } catch (java.lang.NumberFormatException ex) {
            if (java.lang.Double.isInfinite(x)) {
                return x;
            }else {
                return java.lang.Double.NaN;
            }
        }
    }

    public static float round(float x, int scale) {
        return org.apache.commons.math.util.MathUtils.round(x, scale, java.math.BigDecimal.ROUND_HALF_UP);
    }

    public static float round(float x, int scale, int roundingMethod) {
        float sign = org.apache.commons.math.util.MathUtils.indicator(x);
        float factor = ((float) (org.apache.commons.math.util.FastMath.pow(10.0F, scale))) * sign;
        return ((float) (org.apache.commons.math.util.MathUtils.roundUnscaled((x * factor), sign, roundingMethod))) / factor;
    }

    private static double roundUnscaled(double unscaled, double sign, int roundingMethod) {
        switch (roundingMethod) {
            case java.math.BigDecimal.ROUND_CEILING :
                if (sign == (-1)) {
                    unscaled = org.apache.commons.math.util.FastMath.floor(org.apache.commons.math.util.FastMath.nextAfter(unscaled, java.lang.Double.NEGATIVE_INFINITY));
                }else {
                    unscaled = org.apache.commons.math.util.FastMath.ceil(org.apache.commons.math.util.FastMath.nextAfter(unscaled, java.lang.Double.POSITIVE_INFINITY));
                }
                break;
            case java.math.BigDecimal.ROUND_DOWN :
                unscaled = org.apache.commons.math.util.FastMath.floor(org.apache.commons.math.util.FastMath.nextAfter(unscaled, java.lang.Double.NEGATIVE_INFINITY));
                break;
            case java.math.BigDecimal.ROUND_FLOOR :
                if (sign == (-1)) {
                    unscaled = org.apache.commons.math.util.FastMath.ceil(org.apache.commons.math.util.FastMath.nextAfter(unscaled, java.lang.Double.POSITIVE_INFINITY));
                }else {
                    unscaled = org.apache.commons.math.util.FastMath.floor(org.apache.commons.math.util.FastMath.nextAfter(unscaled, java.lang.Double.NEGATIVE_INFINITY));
                }
                break;
            case java.math.BigDecimal.ROUND_HALF_DOWN :
                {
                    unscaled = org.apache.commons.math.util.FastMath.nextAfter(unscaled, java.lang.Double.NEGATIVE_INFINITY);
                    double fraction = unscaled - (org.apache.commons.math.util.FastMath.floor(unscaled));
                    if (fraction > 0.5) {
                        unscaled = org.apache.commons.math.util.FastMath.ceil(unscaled);
                    }else {
                        unscaled = org.apache.commons.math.util.FastMath.floor(unscaled);
                    }
                    break;
                }
            case java.math.BigDecimal.ROUND_HALF_EVEN :
                {
                    double fraction = unscaled - (org.apache.commons.math.util.FastMath.floor(unscaled));
                    if (fraction > 0.5) {
                        unscaled = org.apache.commons.math.util.FastMath.ceil(unscaled);
                    }else
                        if (fraction < 0.5) {
                            unscaled = org.apache.commons.math.util.FastMath.floor(unscaled);
                        }else {
                            if (((org.apache.commons.math.util.FastMath.floor(unscaled)) / 2.0) == (org.apache.commons.math.util.FastMath.floor(((java.lang.Math.floor(unscaled)) / 2.0)))) {
                                unscaled = org.apache.commons.math.util.FastMath.floor(unscaled);
                            }else {
                                unscaled = org.apache.commons.math.util.FastMath.ceil(unscaled);
                            }
                        }

                    break;
                }
            case java.math.BigDecimal.ROUND_HALF_UP :
                {
                    unscaled = org.apache.commons.math.util.FastMath.nextAfter(unscaled, java.lang.Double.POSITIVE_INFINITY);
                    double fraction = unscaled - (org.apache.commons.math.util.FastMath.floor(unscaled));
                    if (fraction >= 0.5) {
                        unscaled = org.apache.commons.math.util.FastMath.ceil(unscaled);
                    }else {
                        unscaled = org.apache.commons.math.util.FastMath.floor(unscaled);
                    }
                    break;
                }
            case java.math.BigDecimal.ROUND_UNNECESSARY :
                if (unscaled != (org.apache.commons.math.util.FastMath.floor(unscaled))) {
                    throw new java.lang.ArithmeticException("Inexact result from rounding");
                }
                break;
            case java.math.BigDecimal.ROUND_UP :
                unscaled = org.apache.commons.math.util.FastMath.ceil(org.apache.commons.math.util.FastMath.nextAfter(unscaled, java.lang.Double.POSITIVE_INFINITY));
                break;
            default :
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(LocalizedFormats.INVALID_ROUNDING_METHOD, roundingMethod, "ROUND_CEILING", java.math.BigDecimal.ROUND_CEILING, "ROUND_DOWN", java.math.BigDecimal.ROUND_DOWN, "ROUND_FLOOR", java.math.BigDecimal.ROUND_FLOOR, "ROUND_HALF_DOWN", java.math.BigDecimal.ROUND_HALF_DOWN, "ROUND_HALF_EVEN", java.math.BigDecimal.ROUND_HALF_EVEN, "ROUND_HALF_UP", java.math.BigDecimal.ROUND_HALF_UP, "ROUND_UNNECESSARY", java.math.BigDecimal.ROUND_UNNECESSARY, "ROUND_UP", java.math.BigDecimal.ROUND_UP);
        }
        return unscaled;
    }

    public static byte sign(final byte x) {
        return x == (org.apache.commons.math.util.MathUtils.ZB) ? org.apache.commons.math.util.MathUtils.ZB : x > (org.apache.commons.math.util.MathUtils.ZB) ? org.apache.commons.math.util.MathUtils.PB : org.apache.commons.math.util.MathUtils.NB;
    }

    public static double sign(final double x) {
        if (java.lang.Double.isNaN(x)) {
            return java.lang.Double.NaN;
        }
        return x == 0.0 ? 0.0 : x > 0.0 ? 1.0 : -1.0;
    }

    public static float sign(final float x) {
        if (java.lang.Float.isNaN(x)) {
            return java.lang.Float.NaN;
        }
        return x == 0.0F ? 0.0F : x > 0.0F ? 1.0F : -1.0F;
    }

    public static int sign(final int x) {
        return x == 0 ? 0 : x > 0 ? 1 : -1;
    }

    public static long sign(final long x) {
        return x == 0L ? 0L : x > 0L ? 1L : -1L;
    }

    public static short sign(final short x) {
        return x == (org.apache.commons.math.util.MathUtils.ZS) ? org.apache.commons.math.util.MathUtils.ZS : x > (org.apache.commons.math.util.MathUtils.ZS) ? org.apache.commons.math.util.MathUtils.PS : org.apache.commons.math.util.MathUtils.NS;
    }

    public static double sinh(double x) {
        return ((org.apache.commons.math.util.FastMath.exp(x)) - (org.apache.commons.math.util.FastMath.exp((-x)))) / 2.0;
    }

    public static int subAndCheck(int x, int y) {
        long s = ((long) (x)) - ((long) (y));
        if ((s < (java.lang.Integer.MIN_VALUE)) || (s > (java.lang.Integer.MAX_VALUE))) {
            throw org.apache.commons.math.MathRuntimeException.createArithmeticException(LocalizedFormats.OVERFLOW_IN_SUBTRACTION, x, y);
        }
        return ((int) (s));
    }

    public static long subAndCheck(long a, long b) {
        long ret;
        java.lang.String msg = "overflow: subtract";
        if (b == (java.lang.Long.MIN_VALUE)) {
            if (a < 0) {
                ret = a - b;
            }else {
                throw new java.lang.ArithmeticException(msg);
            }
        }else {
            ret = org.apache.commons.math.util.MathUtils.addAndCheck(a, (-b), LocalizedFormats.OVERFLOW_IN_ADDITION);
        }
        return ret;
    }

    public static int pow(final int k, int e) throws java.lang.IllegalArgumentException {
        if (e < 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(LocalizedFormats.POWER_NEGATIVE_PARAMETERS, k, e);
        }
        int result = 1;
        int k2p = k;
        while (e != 0) {
            if ((e & 1) != 0) {
                result *= k2p;
            }
            k2p *= k2p;
            e = e >> 1;
        } 
        return result;
    }

    public static int pow(final int k, long e) throws java.lang.IllegalArgumentException {
        if (e < 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(LocalizedFormats.POWER_NEGATIVE_PARAMETERS, k, e);
        }
        int result = 1;
        int k2p = k;
        while (e != 0) {
            if ((e & 1) != 0) {
                result *= k2p;
            }
            k2p *= k2p;
            e = e >> 1;
        } 
        return result;
    }

    public static long pow(final long k, int e) throws java.lang.IllegalArgumentException {
        if (e < 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(LocalizedFormats.POWER_NEGATIVE_PARAMETERS, k, e);
        }
        long result = 1L;
        long k2p = k;
        while (e != 0) {
            if ((e & 1) != 0) {
                result *= k2p;
            }
            k2p *= k2p;
            e = e >> 1;
        } 
        return result;
    }

    public static long pow(final long k, long e) throws java.lang.IllegalArgumentException {
        if (e < 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(LocalizedFormats.POWER_NEGATIVE_PARAMETERS, k, e);
        }
        long result = 1L;
        long k2p = k;
        while (e != 0) {
            if ((e & 1) != 0) {
                result *= k2p;
            }
            k2p *= k2p;
            e = e >> 1;
        } 
        return result;
    }

    public static java.math.BigInteger pow(final java.math.BigInteger k, int e) throws java.lang.IllegalArgumentException {
        if (e < 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(LocalizedFormats.POWER_NEGATIVE_PARAMETERS, k, e);
        }
        return k.pow(e);
    }

    public static java.math.BigInteger pow(final java.math.BigInteger k, long e) throws java.lang.IllegalArgumentException {
        if (e < 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(LocalizedFormats.POWER_NEGATIVE_PARAMETERS, k, e);
        }
        java.math.BigInteger result = java.math.BigInteger.ONE;
        java.math.BigInteger k2p = k;
        while (e != 0) {
            if ((e & 1) != 0) {
                result = result.multiply(k2p);
            }
            k2p = k2p.multiply(k2p);
            e = e >> 1;
        } 
        return result;
    }

    public static java.math.BigInteger pow(final java.math.BigInteger k, java.math.BigInteger e) throws java.lang.IllegalArgumentException {
        if ((e.compareTo(java.math.BigInteger.ZERO)) < 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(LocalizedFormats.POWER_NEGATIVE_PARAMETERS, k, e);
        }
        java.math.BigInteger result = java.math.BigInteger.ONE;
        java.math.BigInteger k2p = k;
        while (!(java.math.BigInteger.ZERO.equals(e))) {
            if (e.testBit(0)) {
                result = result.multiply(k2p);
            }
            k2p = k2p.multiply(k2p);
            e = e.shiftRight(1);
        } 
        return result;
    }

    public static double distance1(double[] p1, double[] p2) {
        double sum = 0;
        for (int i = 0; i < (p1.length); i++) {
            sum += org.apache.commons.math.util.FastMath.abs(((p1[i]) - (p2[i])));
        }
        return sum;
    }

    public static int distance1(int[] p1, int[] p2) {
        int sum = 0;
        for (int i = 0; i < (p1.length); i++) {
            sum += org.apache.commons.math.util.FastMath.abs(((p1[i]) - (p2[i])));
        }
        return sum;
    }

    public static double distance(double[] p1, double[] p2) {
        double sum = 0;
        for (int i = 0; i < (p1.length); i++) {
            final double dp = (p1[i]) - (p2[i]);
            sum += dp * dp;
        }
        return org.apache.commons.math.util.FastMath.sqrt(sum);
    }

    public static double distance(int[] p1, int[] p2) {
        double sum = 0;
        for (int i = 0; i < (p1.length); i++) {
            final double dp = (p1[i]) - (p2[i]);
            sum += dp * dp;
        }
        return org.apache.commons.math.util.FastMath.sqrt(sum);
    }

    public static double distanceInf(double[] p1, double[] p2) {
        double max = 0;
        for (int i = 0; i < (p1.length); i++) {
            max = org.apache.commons.math.util.FastMath.max(max, org.apache.commons.math.util.FastMath.abs(((p1[i]) - (p2[i]))));
        }
        return max;
    }

    public static int distanceInf(int[] p1, int[] p2) {
        int max = 0;
        for (int i = 0; i < (p1.length); i++) {
            max = org.apache.commons.math.util.FastMath.max(max, org.apache.commons.math.util.FastMath.abs(((p1[i]) - (p2[i]))));
        }
        return max;
    }

    public static enum OrderDirection {

        INCREASING,
        DECREASING;}

    public static void checkOrder(double[] val, org.apache.commons.math.util.MathUtils.OrderDirection dir, boolean strict) {
        double previous = val[0];
        boolean ok = true;
        int max = val.length;
        for (int i = 1; i < max; i++) {
            switch (dir) {
                case INCREASING :
                    if (strict) {
                        if ((val[i]) <= previous) {
                            ok = false;
                        }
                    }else {
                        if ((val[i]) < previous) {
                            ok = false;
                        }
                    }
                    break;
                case DECREASING :
                    if (strict) {
                        if ((val[i]) >= previous) {
                            ok = false;
                        }
                    }else {
                        if ((val[i]) > previous) {
                            ok = false;
                        }
                    }
                    break;
                default :
                    throw new java.lang.IllegalArgumentException();
            }
            if (!ok) {
                throw new org.apache.commons.math.exception.NonMonotonousSequenceException(val[i], previous, i, dir, strict);
            }
            previous = val[i];
        }
    }

    public static void checkOrder(double[] val) {
        org.apache.commons.math.util.MathUtils.checkOrder(val, org.apache.commons.math.util.MathUtils.OrderDirection.INCREASING, true);
    }

    public static double safeNorm(double[] v) {
        double rdwarf = 3.834E-20;
        double rgiant = 1.304E19;
        double s1 = 0.0;
        double s2 = 0.0;
        double s3 = 0.0;
        double x1max = 0.0;
        double x3max = 0.0;
        double floatn = ((double) (v.length));
        double agiant = rgiant / floatn;
        for (int i = 0; i < (v.length); i++) {
            double xabs = java.lang.Math.abs(v[i]);
            if ((xabs < rdwarf) || (xabs > agiant)) {
                if (xabs > rdwarf) {
                    if (xabs > x1max) {
                        double r = x1max / xabs;
                        s1 = 1.0 + ((s1 * r) * r);
                        x1max = xabs;
                    }else {
                        double r = xabs / x1max;
                        s1 += r * r;
                    }
                }else {
                    if (xabs > x3max) {
                        double r = x3max / xabs;
                        s3 = 1.0 + ((s3 * r) * r);
                        x3max = xabs;
                    }else {
                        if (xabs != 0.0) {
                            double r = xabs / x3max;
                            s3 += r * r;
                        }
                    }
                }
            }else {
                s2 += xabs * xabs;
            }
        }
        double norm;
        if (s1 != 0.0) {
            norm = x1max * (java.lang.Math.sqrt((s1 + ((s2 / x1max) / x1max))));
        }else {
            if (s2 == 0.0) {
                norm = x3max * (java.lang.Math.sqrt(s3));
            }else {
                if (s2 >= x3max) {
                    norm = java.lang.Math.sqrt((s2 * (1.0 + ((x3max / s2) * (x3max * s3)))));
                }else {
                    norm = java.lang.Math.sqrt((x3max * ((s2 / x3max) + (x3max * s3))));
                }
            }
        }
        return norm;
    }
}

