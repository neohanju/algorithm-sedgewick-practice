/**
 * Author : Haanju Yoo
 * Date   : 2017.10.20
 *  NOTE: std.dev must be double.NaN when the number of trial is 1
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {	
	
	private double[] pcThresholds;
	private int T;
	
	public PercolationStats(int n, int trials) {
		/**
		 * perform trials independent experiments on an n-by-n grid
		 * 
		 * @param  n       size of percolation (n >= 0)
		 * @param  trials  total number of experiments (trials >= 0)
		 * @return none
		 */
		if (n <= 0)
			throw new IllegalArgumentException("too small n");
		if (trials <= 0)
			throw new IllegalArgumentException("too small number of trials");
		
		T = trials;
		pcThresholds = new double [T];
		double numSites = n*n;		
		for (int i = 0; i < T; i++) {			
			Percolation curPc = new Percolation(n);			
			while (!curPc.percolates()) {
				// TODO: pick a numbers at uniform distribution and skip
				// when the picked site is not blocked
				int row = StdRandom.uniform(n) + 1;
				int col = StdRandom.uniform(n) + 1;
				curPc.open(row, col);
			}
			pcThresholds[i] = curPc.numberOfOpenSites() / numSites;
		}
	}
	
	public double mean() {
		/**
		 * @return sample mean of percolation threshold
		 */
		return StdStats.mean(pcThresholds);
	}
	
	public double stddev() {
		/**
		 * @return sample standard deviation of percolation threshold
		 */
		if (T > 1)
			return StdStats.stddev(pcThresholds);
		else
			return Double.NaN;
	}
	
	public double confidenceLo() {
		/**
		 * @return low  endpoint of 95% confidence intervals
		 */
		return this.mean() - 1.96 * this.stddev() / Math.sqrt(T);
	}
	
	public double confidenceHi() {
		/**
		 * @return high endpoint of 95% confidence interval
		 */
		return this.mean() + 1.96 * this.stddev() / Math.sqrt(T);
	}

	public static void main(String[] args) {
		// test client (described below)
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
