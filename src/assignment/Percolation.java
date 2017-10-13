package assignment;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {	
	
	private int[][] grid;
	private int num_open_site;
	
	public Percolation(int n) {
		// create n-by-n grid, with all sites blocked
		if (n <= 0)
			throw new IllegalArgumentException("the size of grid must be greater than 0");
		
		this.num_open_site = 0;
		this.grid = new int [n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				this.grid[i][j] = -1;  // -1 means block
	}
	
	public void open(int row, int col) {		
		if (!isOpen(row, col)) {
			this.grid[row][col] = this.grid.length * (row-1) + (col-1);
			this.num_open_site++;
		}
	}
	
	public boolean isOpen(int row, int col) {		
		return this.grid[row][col] >= 0;
	} 
	
	public boolean isFull(int row, int col) {
		// is site (row, col) full?
	}
	
	public int numberOfOpenSites() {
		// number of open sites
		return this.num_open_site;
	}
	
	public boolean percolates() {
		// does the system percolate?
	}
	
	public static void main(String[] args) {
		// test client (optional)
	}
}

//()()
//('')HAANJU.YOO

