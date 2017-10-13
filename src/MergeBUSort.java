import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class MergeBUSort {
	
	private static Comparable[] aux;	// auxiliary array for merge operation
	
	public static void sort(Comparable[] a) {
		int N = a.length;
		aux = new Comparable[N];
		
		for (int sz = 1; sz <= N; sz=sz+sz)	// sz: size of subarray
			for (int lo = 0; lo < N-sz; lo+=sz+sz)
				merge(a, lo, lo + sz - 1, Math.min(lo+sz+sz-1, N-1));
	}	
	
	public static void merge(Comparable[] a, int lo, int mid, int hi) {
		int i = lo, j = mid + 1;
		
		for (int k = lo; k <= hi; k++)
			aux[k] = a[k];
		
		for (int k = lo; k <= hi; k++)
			if (i > mid)					a[k] = a[j++];
			else if (j > hi)				a[k] = a[i++];
			else if (less(aux[j], aux[i]))	a[k] = a[j++];
			else 							a[k] = a[i++];
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
