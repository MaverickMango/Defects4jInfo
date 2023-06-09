package org.apache.commons.math3.optimization.linear;
public class SimplexSolver extends org.apache.commons.math3.optimization.linear.AbstractLinearOptimizer {
	private static final double DEFAULT_EPSILON = 1.0E-6;

	private static final int DEFAULT_ULPS = 10;

	private final double epsilon;

	private final int maxUlps;

	public SimplexSolver() {
		this(org.apache.commons.math3.optimization.linear.SimplexSolver.DEFAULT_EPSILON, org.apache.commons.math3.optimization.linear.SimplexSolver.DEFAULT_ULPS);
	}

	public SimplexSolver(final double epsilon, final int maxUlps) {
		this.epsilon = epsilon;
		this.maxUlps = maxUlps;
	}

	private java.lang.Integer getPivotColumn(org.apache.commons.math3.optimization.linear.SimplexTableau tableau) {
		double minValue = 0;
		java.lang.Integer minPos = null;
		for (int i = tableau.getNumObjectiveFunctions(); i < (tableau.getWidth() - 1); i++) {
			final double entry = tableau.getEntry(0, i);
			if (entry < minValue) {
				minValue = entry;
				minPos = i;
			}
		}
		return minPos;
	}

	private java.lang.Integer getPivotRow(org.apache.commons.math3.optimization.linear.SimplexTableau tableau, final int col) {
		java.util.List<java.lang.Integer> minRatioPositions = new java.util.ArrayList<java.lang.Integer>();
		double minRatio = java.lang.Double.MAX_VALUE;
		for (int i = tableau.getNumObjectiveFunctions(); i < tableau.getHeight(); i++) {
			final double rhs = tableau.getEntry(i, tableau.getWidth() - 1);
			final double entry = tableau.getEntry(i, col);
			if (org.apache.commons.math3.util.Precision.compareTo(entry, 0.0, maxUlps) > 0) {
				final double ratio = rhs / entry;
				final int cmp = java.lang.Double.compare(ratio, minRatio);
				if (cmp == 0) {
					minRatioPositions.add(i);
				} else if (cmp < 0) {
					minRatio = ratio;
					minRatioPositions = new java.util.ArrayList<java.lang.Integer>();
					minRatioPositions.add(i);
				}
			}
		}
		if (minRatioPositions.size() == 0) {
			return null;
		} else if (minRatioPositions.size() > 1) {
			for (java.lang.Integer row : minRatioPositions) {
				for (int i = 0; i < tableau.getNumArtificialVariables(); i++) {
					int column = i + tableau.getArtificialVariableOffset();
					final double entry = tableau.getEntry(row, column);
					if (org.apache.commons.math3.util.Precision.equals(entry, 1.0, maxUlps) && row.equals(tableau.getBasicRow(column))) {
						return row;
					}
				}
			}
			java.lang.Integer minRow = null;
			int minIndex = tableau.getWidth();
			for (java.lang.Integer row : minRatioPositions) {
				int i = tableau.getNumObjectiveFunctions();
				for (; (i < (tableau.getWidth() - 1)) && (minRow != row); i++) {
					if (row == tableau.getBasicRow(i)) {
						if (i < minIndex) {
							minIndex = i;
							minRow = row;
						}
					}
				}
			}
			return minRow;
		}
		return minRatioPositions.get(0);
	}

	protected void doIteration(final org.apache.commons.math3.optimization.linear.SimplexTableau tableau) throws org.apache.commons.math3.exception.MaxCountExceededException, org.apache.commons.math3.optimization.linear.UnboundedSolutionException {
		incrementIterationsCounter();
		java.lang.Integer pivotCol = getPivotColumn(tableau);
		java.lang.Integer pivotRow = getPivotRow(tableau, pivotCol);
		if (pivotRow == null) {
			throw new org.apache.commons.math3.optimization.linear.UnboundedSolutionException();
		}
		double pivotVal = tableau.getEntry(pivotRow, pivotCol);
		tableau.divideRow(pivotRow, pivotVal);
		for (int i = 0; i < tableau.getHeight(); i++) {
			if (i != pivotRow) {
				final double multiplier = tableau.getEntry(i, pivotCol);
				tableau.subtractRow(i, pivotRow, multiplier);
			}
		}
	}

	protected void solvePhase1(final org.apache.commons.math3.optimization.linear.SimplexTableau tableau) throws org.apache.commons.math3.exception.MaxCountExceededException, org.apache.commons.math3.optimization.linear.UnboundedSolutionException, org.apache.commons.math3.optimization.linear.NoFeasibleSolutionException {
		if (tableau.getNumArtificialVariables() == 0) {
			return;
		}
		while (!tableau.isOptimal()) {
			doIteration(tableau);
		} 
		if (!org.apache.commons.math3.util.Precision.equals(tableau.getEntry(0, tableau.getRhsOffset()), 0.0, epsilon)) {
			throw new org.apache.commons.math3.optimization.linear.NoFeasibleSolutionException();
		}
	}

	@java.lang.Override
	public org.apache.commons.math3.optimization.PointValuePair doOptimize() throws org.apache.commons.math3.exception.MaxCountExceededException, org.apache.commons.math3.optimization.linear.UnboundedSolutionException, org.apache.commons.math3.optimization.linear.NoFeasibleSolutionException {
		final org.apache.commons.math3.optimization.linear.SimplexTableau tableau = new org.apache.commons.math3.optimization.linear.SimplexTableau(getFunction(), getConstraints(), getGoalType(), restrictToNonNegative(), epsilon, maxUlps);
		solvePhase1(tableau);
		tableau.dropPhase1Objective();
		while (!tableau.isOptimal()) {
			doIteration(tableau);
		} 
		return tableau.getSolution();
	}
}