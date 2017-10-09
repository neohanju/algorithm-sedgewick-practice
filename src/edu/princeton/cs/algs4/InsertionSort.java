package edu.princeton.cs.algs4;

public class InsertionSort {
	public static void sort(Comparable[] a) {
		int N = a.length;
		for (int i = 1; i < N; i++)
			for (int j = i; j > 1; j--)
				if (less(a[j], a[j-1])) exch(a, j, j-1);
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
