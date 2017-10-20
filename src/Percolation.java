/**
 * Author : Haanju Yoo
 * Date   : 2017.10.20
 *  Percolation data abstraction for the first programming assignment of 
 *  'Algorithm' in Coursera. All indices starts from 1 to N
 */

import algs4.QuickFindUF;
import algs4.StdOut;

public class Percolation {	
	
	private int N;                // column size of grid
	private boolean[][] grid;     // grid of sites
	private int numOpened;        // # of opened sites
	private QuickFindUF UFModule; // union-find module for connection check
	
	private int SOURCE_SITE_INDEX;
	private int SINK_SITE_INDEX;
	
	//--------------------------------------------------------
	// API
	//--------------------------------------------------------
	public Percolation(int n) {
		// create n-by-n grid, with all sites blocked
		if (n <= 0)
			throw new IllegalArgumentException("Invalid grid size! the size must be a positive number.");
		
		this.numOpened = 0;
		this.N = n;
		this.grid = new boolean [N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				this.grid[i][j] = false;
		
		UFModule = new QuickFindUF(N*N+2);
		SOURCE_SITE_INDEX = N*N;
		SINK_SITE_INDEX = N*N + 1;
	}
	
	public void open(int row, int col) {
		/**
		 * Open the sites. It also connects the opened site with its neighbor
		 * sites when they are opened. Then, when the target site is at a top
		 * or bottom column, connect the site with source or sink site.
		 * 
		 * @param  row  row index of a target site
		 * @param  col  column index of a target site
		 * @return      none
		 */
		isValidIndex(row, col);		
		if (!isOpen(row, col)) {
			this.grid[row][col] = true;
			this.numOpened++;
			
			// union with opened neighbor sites
			if (isValidIndex(row-1, col)) this.connect(row, col, row-1, col);
			if (isValidIndex(row+1, col)) this.connect(row, col, row+1, col);
			if (isValidIndex(row, col-1)) this.connect(row, col, row, col-1);
			if (isValidIndex(row, col+1)) this.connect(row, col, row, col+1);
			
			// union with source or sink (auxiliary) site
			int idx = this.xyTo1D(row, col);
			if      (0   == row) this.UFModule.union(SOURCE_SITE_INDEX, idx);
			else if (N-1 == row) this.UFModule.union(idx, SINK_SITE_INDEX);
		}
	}
	
	public boolean isOpen(int row, int col) {
		isValidIndex(row, col);
		return this.grid[row][col];
	} 
	
	public boolean isFull(int row, int col) {
		// is site (row, col) full?
		isValidIndex(row, col);
		int idx = xyTo1D(row, col);
		return UFModule.connected(SOURCE_SITE_INDEX, idx) 
				&& UFModule.connected(idx, SINK_SITE_INDEX);
	}
	
	public int numberOfOpenSites() {
		// number of open sites
		return this.numOpened;
	}
	
	public boolean percolates() {
		// does the system percolate?
		return UFModule.connected(SOURCE_SITE_INDEX, SINK_SITE_INDEX);
	}
	
	//--------------------------------------------------------
	// private methods
	//--------------------------------------------------------
	private int xyTo1D(int row, int col) {
		// valid row in [1..N] and col in [1..N]
		return N*(row-1) + col-1;
	}
	
	private boolean isValidIndex(int row, int col) {
		if (row < 1 || row > N) {
			throw new IndexOutOfBoundsException("row index out of bounds");			
		} else if (col < 1 || col > N) {
			throw new IndexOutOfBoundsException("col index out of bounds");
		}
		return true;
	}
	
	private void connect(int row1, int col1, int row2, int col2) {
		/**
		 * Connect the sites.
		 * 
		 * @param  row1  row index of the first site
		 * @param  col1  column index of the first site
		 * @param  row2  row index of the second site
		 * @param  col2  column index of the second site
		 * @return       none
		 */
		int idx1 = xyTo1D(row1, col1), idx2 = xyTo1D(row2, col2);
		if (this.UFModule.connected(idx1, idx2))
			return;
		this.UFModule.union(idx1, idx2);
	}
	
	//--------------------------------------------------------
	// dummy client code
	//--------------------------------------------------------
	public static void main(String[] args) {
		// test client (optional)
		int N = 20;
		Percolation pc = new Percolation(N);
		for (int i = 0; i < N-1; i++) {
			pc.open(i, 0);
		}
		pc.open(N-2, 1);
		pc.open(N-1, 1);
		StdOut.print("Is full? " + pc.isFull(N-1, 1) + "\n");
		StdOut.print("Percolated? " + pc.percolates());
	}
}

//()()
//('')HAANJU.YOO

