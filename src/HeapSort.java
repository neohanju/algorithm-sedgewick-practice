import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class HeapSort {
	public static void sort(Comparable[] a) {
		int N = a.length;
		for (int i = N/2; i >= 1; i--)
			sink(a, i, N);
		while (N > 1) {
			exch(a, 1, N--);
			sink(a, 1, N);
		}
	}
	
	private static void sink(Comparable[] a, int k, int N) {
		while (2*k <= N) {
			int j = 2*k;
			if (less(j, j+1)) j++;
			if (!less(k, j)) break;
			exch(a, k, j);
			k = j;
		}
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
