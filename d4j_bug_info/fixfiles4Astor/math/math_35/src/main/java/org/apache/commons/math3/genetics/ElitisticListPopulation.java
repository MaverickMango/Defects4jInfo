package org.apache.commons.math3.genetics;
public class ElitisticListPopulation extends org.apache.commons.math3.genetics.ListPopulation {
	private double elitismRate = 0.9;

	public ElitisticListPopulation(final java.util.List<org.apache.commons.math3.genetics.Chromosome> chromosomes, final int populationLimit, final double elitismRate) {
		super(chromosomes, populationLimit);
		this.elitismRate = elitismRate;
	}

	public ElitisticListPopulation(final int populationLimit, final double elitismRate) {
		super(populationLimit);
		this.elitismRate = elitismRate;
	}

	public org.apache.commons.math3.genetics.Population nextGeneration() {
		org.apache.commons.math3.genetics.ElitisticListPopulation nextGeneration = new org.apache.commons.math3.genetics.ElitisticListPopulation(this.getPopulationLimit(), this.getElitismRate());
		java.util.List<org.apache.commons.math3.genetics.Chromosome> oldChromosomes = this.getChromosomes();
		java.util.Collections.sort(oldChromosomes);
		int boundIndex = ((int) (org.apache.commons.math3.util.FastMath.ceil((1.0 - this.getElitismRate()) * oldChromosomes.size())));
		for (int i = boundIndex; i < oldChromosomes.size(); i++) {
			nextGeneration.addChromosome(oldChromosomes.get(i));
		}
		return nextGeneration;
	}

	public void setElitismRate(final double elitismRate) {
		if ((elitismRate < 0) || (elitismRate > 1)) {
			throw new org.apache.commons.math3.exception.OutOfRangeException(org.apache.commons.math3.exception.util.LocalizedFormats.ELITISM_RATE, elitismRate, 0, 1);
		}
		this.elitismRate = elitismRate;
	}

	public double getElitismRate() {
		return this.elitismRate;
	}
}