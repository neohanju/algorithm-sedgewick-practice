/**
 * Author : Haanju Yoo
 * Date   : 2017.10.20
 *  NOTE: std.dev must be double.NaN when the number of trial is 1
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {	
	
	private double[] pcThresholds;  // to record thresholds
	private int T;  // number of experiments (i.e. # of trials)
	private double mean_ = 0.0;  // mean of thresholds
	private double stddev_ = Double.NaN;  // std. dev of thresholds
	private final double CONFIDENCE_95 = 1.96;  // coef for confidence interval
	
	public PercolationStats(int n, int trials) {
		/**
		 * perform trials independent experiments on an n-by-n grid
		 * 
		 * @param  n       size of system (n >= 0)
		 * @param  trials  total number of experiments (trials >= 0)
		 * @return none
		 */
		if (n <= 0)
			throw new IllegalArgumentException("too small n");
		if (trials <= 0)
			throw new IllegalArgumentException("too small number of trials");
		
		T = trials;
		this.pcThresholds = new double [T];
		double numSites = n*n;
		for (int i = 0; i < T; i++) {			
			Percolation curPc = new Percolation(n);			
			while (!curPc.percolates()) {
				int row = StdRandom.uniform(n) + 1;
				int col = StdRandom.uniform(n) + 1;
				curPc.open(row, col);
			}
			this.pcThresholds[i] = curPc.numberOfOpenSites() / numSites;
		}
		
		this.mean_ = StdStats.mean(this.pcThresholds);
		if (T > 1)
			this.stddev_ = StdStats.stddev(pcThresholds); 
	}
	
	public double mean() {
		/**
		 * @return sample mean of percolation threshold
		 */
		return this.mean_;
	}
	
	public double stddev() {
		/**
		 * @return   double   sample standard deviation of percolation threshold
		 */
		return this.stddev_;
	}
	
	public double confidenceLo() {
		/**
		 * @return   double   low end point of 95% confidence intervals
		 */
		return this.mean_ - CONFIDENCE_95 * this.stddev_ / Math.sqrt(T);
	}
	
	public double confidenceHi() {
		/**
		 * @return   double   high end point of 95% confidence interval
		 */
		return this.mean_ + CONFIDENCE_95 * this.stddev_ / Math.sqrt(T);
	}

	public static void main(String[] args) {
		/**
		 * test client.
		 * @param  args[]  [0]: size of system, [1]: # of trials
		 * @return none  
		 */
		if (args.length < 2)
			throw new IllegalArgumentException("Insufficient number of args");
		
		int n = Integer.parseInt(args[0]);
		int trials = Integer.parseInt(args[1]);
		PercolationStats pcStats = new PercolationStats(n, trials);
		StdOut.printf("mean                    = %f\n", pcStats.mean());
		StdOut.printf("stddev                  = %f\n", pcStats.stddev());
		StdOut.printf("95%% confidence interval = [%f, %f]\n", 
				pcStats.confidenceLo(), pcStats.confidenceHi());
	}
}

//()()
//('')HAANJU.YOO
