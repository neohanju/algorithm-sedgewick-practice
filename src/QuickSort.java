import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class QuickSort {
	
	public static void sort(Comparable[] a) {
		StdRandom.shuffle(a); 	// eliminate dependency on input
		sort(a, 0, a.length-1);
	}
	
	public static void sort(Comparable[] a, int lo, int hi) {
		if (hi <= lo) return;
		int j = partition(a, lo, hi);
		sort(a, lo, j-1);
		sort(a, j+1, hi);
	}
	
	public static int partition(Comparable[] a, int lo, int hi) {
		int i = lo, j = hi+1;
		while (true) {
			while (less(a[++i], a[lo])) if (i == hi) break;
			while (less(a[lo], a[--j])) if (j == lo) break;
			if (j <= i) break;
			exch(a, i, j);
		}
		exch(a, lo, j);
		return j;
	}
	
	private static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}
	
	private static void exch(Comparable[] a, int i, int j) {
		Comparable t = a[i]; a[i] = a[j]; a[j] = t;
	}
	
	private static void show(Comparable[] a) {
		// print the array, on a single line
		for (int i = 0; i < a.length; i++)
			StdOut.print(a[i] + " ");
		StdOut.println();
	}
	
	public static boolean isSorted(Comparable[] a) {
		// test whether the array entries are in order
		for (int i = 1; i < a.length; i++)
			if (less(a[i], a[i-1])) return false;
		return true;
	}
	
	public static void main(String[] args) {
		if (args.length > 0) {
			// read strings from standard input, sort them, and print
			String[] a = StdIn.readAllStrings();
			sort(a);
			assert isSorted(a);
			show(a);
		} else {
			// generate random number array with double type
			int N = 100;
			Double[] a = new Double[N];
			for (int i = 0; i < N; i++)
				a[i] = StdRandom.uniform();
			sort(a);
			assert isSorted(a);
			show(a);
		}
	}
}

