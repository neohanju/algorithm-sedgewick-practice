import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class MergeSort {
	
	private static Comparable[] aux;	// auxiliary array for merge operation
	
	public static void sort(Comparable[] a) {
		aux = new Comparable[a.length];
		sort(a, 0, a.length-1);
	}
	
	public static void sort(Comparable[] a, int lo, int hi) {
		if (hi <= lo) return;
		int mid = lo + (hi - lo)/2;
		sort(a, lo, mid);		// sort left half
		sort(a, mid+1, hi);		// sort right half
		merge(a, lo, mid, hi);	// merge results
	}
	
	public static void merge(Comparable[] a, int lo, int mid, int hi) {
		int i = lo, j = mid + 1;
		
		for (int k = lo; k < hi; k++)
			aux[k] = a[k];
		
		for (int k = lo; k < hi; k++)
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
		// read strings from standard input, sort them, and print
		String[] a = In.readStrings();
		sort(a);
		assert isSorted(a);
		show(a);
	}
}
