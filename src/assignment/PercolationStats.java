package assignment;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {	
	
	public PercolationStats(int n, int trials) {
		// perform trials independent experiments on an n-by-n grid
		int row = 0, col = 0;
		for (int i = 0; i < trials; i++) {
			
			Percolation cur_experiment = new Percolation(n);
			
			while (!cur_experiment.percolates()) {
				row = StdRandom.uniform(n);
				col = StdRandom.uniform(n);
			}
		}		
	}
	
	public double mean() {
		// sample mean of percolation threshold
	}
	
	public double stddev() {
		// sample standard deviation of percolation threshold
	}
	
	public double confidenceLo() {
		// low  endpoint of 95% confidence interval
	}
	
	public double confidenceHi() {
		// high endpoint of 95% confidence interval
	}

	public static void main(String[] args) {
		// test client (described below)
	}
}

//()()
//('')HAANJU.YOO