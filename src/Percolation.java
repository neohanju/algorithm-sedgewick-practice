

import edu.princeton.cs.algs4.QuickFindUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {	
	
	private int N;
	private boolean[][] grid;
	private int num_open_site;
	private QuickFindUF UFModule;
	private int[][] neighborOffset = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
	
	public Percolation(int n) {
		// create n-by-n grid, with all sites blocked
		if (n <= 0)
			throw new IllegalArgumentException("the size of grid must be greater than 0");
		
		this.num_open_site = 0;
		this.N = n;
		this.grid = new boolean [N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				this.grid[i][j] = false;
		
		UFModule = new QuickFindUF(N*N+2);
	}
	
	public void open(int row, int col) {		
		if (!inBound(row, col))
			throw new IllegalArgumentException("tried to access the element at the out of bound");
		
		if (!isOpen(row, col)) {
			this.grid[row][col] = true;
			this.num_open_site++;
			int pos1D = idx2Dto1D(row, col);
			
			// union with opened neighbor sites
			int r = 0, c = 0;
			for (int i = 0; i < neighborOffset.length; i++) {
				r = row + neighborOffset[i][1];
				c = col + neighborOffset[i][0];
				if (inBound(r, c) && isOpen(r, c))
					UFModule.union(pos1D, idx2Dto1D(r, c));
			}
			
			// union with source or sink
			if (0 == row) 			UFModule.union(0, pos1D);
			else if (N-1 == row) 	UFModule.union(pos1D, UFModule.count()-1);
		}
	}
	
	public boolean isOpen(int row, int col) {
		if (!inBound(row, col))
			throw new IllegalArgumentException("tried to access the element at the out of bound");
		return this.grid[row][col];
	} 
	
	public boolean isFull(int row, int col) {
		// is site (row, col) full?
		if (!inBound(row, col))
			throw new IllegalArgumentException("tried to access the element at the out of bound");
		return UFModule.connected(idx2Dto1D(row, col), UFModule.count()-1);
	}
	
	public int numberOfOpenSites() {
		// number of open sites
		return this.num_open_site;
	}
	
	public boolean percolates() {
		// does the system percolate?
		return UFModule.connected(0, UFModule.count()-1);
	}
	
	private int idx2Dto1D(int row, int col) {
		// start with 1
		return row * N + col + 1;
	}
	
	private boolean inBound(int row, int col) {
		if (row < 0 || row >= N || col < 0 || col >= N) return false;
		return true;
	}
	
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

