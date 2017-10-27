/**
 * Author : Haanju Yoo
 * Date   : 2017.10.20
 *  Percolation data abstraction for the first programming assignment of 
 *  'Algorithm' in Coursera. All indices start from 1.
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {	
	
	private int N;  // column size of grid
	private boolean[][] grid;  // grid of sites
	private int numOpened;  // # of opened sites
	private WeightedQuickUnionUF UFModule; 
	                        // union-find module for connection check
	
	private int SOURCE_SITE_INDEX;  // auxiliary index for source site
	private int SINK_SITE_INDEX;  // auxiliary index for sink site
	private static int [][] NEIGHBOR_OFFSETS = 
		{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};  // offsets for accessing neighbors
	
	//--------------------------------------------------------
	// API
	//--------------------------------------------------------
	public Percolation(int n) {
		/**
		 * Create n-by-n grid, with all sites blocked.
		 * 
		 * @param   n     size of grid. the grid will be N x N
		 * @return  none
		 */
		if (n <= 0)
			throw new IllegalArgumentException(
					"Invalid grid size! the size must be a positive number.");
		
		this.numOpened = 0;
		this.N = n;
		this.grid = new boolean [N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				this.grid[i][j] = false;
		
		UFModule = new WeightedQuickUnionUF(N*N+2);
		SOURCE_SITE_INDEX = N*N;
		SINK_SITE_INDEX = N*N + 1;
	}
	
	public void open(int row, int col) {
		/**
		 * Open site (row, col) if it is not open already. It also connects 
		 * the opened site with its neighbor sites when they are opened. 
		 * When the target site is at a top/bottom column, connect the site 
		 * with a source/sink site.
		 * 
		 * @param  row  row index of a target site. valid at [1..N]
		 * @param  col  column index of a target site. valid at [1..N]
		 * @return none
		 */
		checkIndex(row, col);		
		if (isOpen(row, col))
			return;

		this.grid[row-1][col-1] = true;
		this.numOpened++;
		
		// union with opened neighbor sites
		int neighborRow = 0, neighborCol = 0;
		for (int i = 0; i < NEIGHBOR_OFFSETS.length; i++) {
			neighborRow = row + NEIGHBOR_OFFSETS[i][0];
			neighborCol = col + NEIGHBOR_OFFSETS[i][1];
			if (neighborRow < 1 || neighborRow > N 
					|| neighborCol < 1 || neighborCol > N)
				continue;
			if (this.isOpen(neighborRow, neighborCol))
				this.connect(row, col, neighborRow, neighborCol);
		}
		
		// union with source or sink (auxiliary) site
		int idx = this.xyTo1D(row, col);
		if (1 == row) this.UFModule.union(SOURCE_SITE_INDEX, idx);
		if (N == row) this.UFModule.union(idx, SINK_SITE_INDEX);
	}
	
	public boolean isOpen(int row, int col) {
		/**
		 * Examine whether the target site is opened or not
		 * 
		 * @param  row      row index of the target site
		 * @param  col      column index of the target site
		 * @return boolean  true: target site is opened / false: not opened
		 */
		checkIndex(row, col);
		return this.grid[row-1][col-1];
	} 
	
	public boolean isFull(int row, int col) {
		/**
		 * Examine whether the target site is full or not
		 * 
		 * @param  row      row index of the target site
		 * @param  col      column index of the target site
		 * @return boolean  true: target site is full / false: not full
		 */
		checkIndex(row, col);
		return UFModule.connected(SOURCE_SITE_INDEX, xyTo1D(row, col));
	}
	
	public int numberOfOpenSites() {
		/**
		 * Return the number of open sites
		 * 
		 * @return  int  the number of open sites
		 */
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
		/**
		 * Return 1D array index from 2D coordinates.
		 * 
		 * @param   row   row index of the target site
		 * @param   col   column index of the target site
		 * @return  int   1D array index
		 */
		return N*(row-1) + col-1;
	}
		
	private void checkIndex(int row, int col) {
		/**
		 * Check the validity of the target index. Valid row in [1..N] and 
		 * col in [1..N]
		 * 
		 * @param   row   row index of the target site
		 * @param   col   column index of the target site
		 * @return  int   1D array index
		 */
		if (row < 1 || row > N) {
			throw new IndexOutOfBoundsException("row index out of bounds");			
		} else if (col < 1 || col > N) {
			throw new IndexOutOfBoundsException("col index out of bounds");
		}
	} 
	
	private void connect(int row1, int col1, int row2, int col2) {
		/**
		 * Connect two sites.
		 * 
		 * @param   row1   row index of the first site
		 * @param   col1   column index of the first site
		 * @param   row2   row index of the second site
		 * @param   col2   column index of the second site
		 * @return  none
		 */
		this.UFModule.union(xyTo1D(row1, col1), xyTo1D(row2, col2));
	}
	
	
	//--------------------------------------------------------
	// dummy client code
	//--------------------------------------------------------
	public static void main(String[] args) {
		/**
		 * test client (optional)
		 * 
		 * @param  args  arguments. [input file path, (test row), (test col)]
		 * @return none
		 */
		
		// test with file input
		In in = new In(args[0]);
		int[] whilelist = in.readAllInts();
		int N = whilelist[0];
		StdOut.println("Size: " + N);
		
		Percolation pc = new Percolation(N);
		for (int i = 1; i < whilelist.length; i+=2) {
			int row = whilelist[i];
			int col = whilelist[i+1];
			StdOut.println("Open [" + row + ", " + col + "]");
			pc.open(row, col);
		}
		StdOut.println("Percolated? " + pc.percolates());
				
		// check a specific site
		if (args.length < 3)
			return;
		
		int testRow = Integer.parseInt(args[1]), testCol = Integer.parseInt(args[2]);
		StdOut.println("[" + testRow + ", " + testCol + "] is opened? " + pc.isOpen(testRow, testCol));
		StdOut.println("[" + testRow + ", " + testCol + "] is full? " + pc.isFull(testRow, testCol));
	}
}

//()()
//('')HAANJU.YOO
// javac -cp ".;..\lib\algs4.jar" Percolation.java
// java -cp ".;..\lib\algs4.jar" Percolation ..\etc\percolation-testing\input50.txt

