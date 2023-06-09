package org.apache.commons.math.distribution;
public class PoissonDistributionImpl extends org.apache.commons.math.distribution.AbstractIntegerDistribution implements org.apache.commons.math.distribution.PoissonDistribution , java.io.Serializable {
	public static final int DEFAULT_MAX_ITERATIONS = 10000000;

	public static final double DEFAULT_EPSILON = 1.0E-12;

	private static final long serialVersionUID = -3349935121172596109L;

	private org.apache.commons.math.distribution.NormalDistribution normal;

	private double mean;

	private int maxIterations = org.apache.commons.math.distribution.PoissonDistributionImpl.DEFAULT_MAX_ITERATIONS;

	private double epsilon = org.apache.commons.math.distribution.PoissonDistributionImpl.DEFAULT_EPSILON;

	public PoissonDistributionImpl(double p) {
		this(p, org.apache.commons.math.distribution.PoissonDistributionImpl.DEFAULT_EPSILON, org.apache.commons.math.distribution.PoissonDistributionImpl.DEFAULT_MAX_ITERATIONS);
	}

	public PoissonDistributionImpl(double p, double epsilon, int maxIterations) {
		if (p <= 0) {
			throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_POSITIVE_POISSON_MEAN, p);
		}
		mean = p;
		normal = new org.apache.commons.math.distribution.NormalDistributionImpl(p, org.apache.commons.math.util.FastMath.sqrt(p));
		this.epsilon = epsilon;
		this.maxIterations = maxIterations;
	}

	public PoissonDistributionImpl(double p, double epsilon) {
		this(p, epsilon, org.apache.commons.math.distribution.PoissonDistributionImpl.DEFAULT_MAX_ITERATIONS);
	}

	public PoissonDistributionImpl(double p, int maxIterations) {
		this(p, org.apache.commons.math.distribution.PoissonDistributionImpl.DEFAULT_EPSILON, maxIterations);
	}

	public double getMean() {
		return mean;
	}

	public double probability(int x) {
		double ret;
		if ((x < 0) || (x == java.lang.Integer.MAX_VALUE)) {
			ret = 0.0;
		} else if (x == 0) {
			ret = org.apache.commons.math.util.FastMath.exp(-mean);
		} else {
			ret = org.apache.commons.math.util.FastMath.exp((-org.apache.commons.math.distribution.SaddlePointExpansion.getStirlingError(x)) - org.apache.commons.math.distribution.SaddlePointExpansion.getDeviancePart(x, mean)) / org.apache.commons.math.util.FastMath.sqrt(org.apache.commons.math.util.MathUtils.TWO_PI * x);
		}
		return ret;
	}

	@java.lang.Override
	public double cumulativeProbability(int x) throws org.apache.commons.math.MathException {
		if (x < 0) {
			return 0;
		}
		if (x == java.lang.Integer.MAX_VALUE) {
			return 1;
		}
		return org.apache.commons.math.special.Gamma.regularizedGammaQ(((double) (x)) + 1, mean, epsilon, maxIterations);
	}

	public double normalApproximateProbability(int x) throws org.apache.commons.math.MathException {
		return normal.cumulativeProbability(x + 0.5);
	}

	@java.lang.Override
	public int sample() throws org.apache.commons.math.MathException {
		return ((int) (org.apache.commons.math.util.FastMath.min(randomData.nextPoisson(mean), java.lang.Integer.MAX_VALUE)));
	}

	@java.lang.Override
	protected int getDomainLowerBound(double p) {
		return 0;
	}

	@java.lang.Override
	protected int getDomainUpperBound(double p) {
		return java.lang.Integer.MAX_VALUE;
	}
}