

import java.util.Random;

/*
 *Test
 * A class which manages various sorting algorithms and reports the timing of them.
 * This main class contains some basic code to get started.
 * Taken from Practice Assignment Test code but modified for Assignment 1.
 */
public class Test {
	
	protected int [] arr;                  // This is the array to be sorted.
	protected String populationStrategy;   // How should the array be populated? 
	protected SortingFactory factory;      // This creates different sorting algorithms.
	protected String [] algorithms = {"runsort", "mergesort"}; // Algos to use.

	
	/**
	 * Default Constructor
	 */
	public Test() {
		populationStrategy = "random";
		createNewArray(50000);
		factory = new SortingFactory(false);
	}
	
	
	/**
	 * Constructor which sets the size of the array
	 * @param arraySize
	 */
	public Test(int arraySize) {
		populationStrategy = "random";
		createNewArray(arraySize);
		factory = new SortingFactory(false); // Do not use a default value for the algorithm name.
	}
	
	
	/**
	 * Constructor which sets array size AND uses one of two strategies for populating the array.
	 * @param arraySize
	 * @param populationStrategy
	 */
	public Test(int arraySize, String populationStrategy) {
		this.populationStrategy = populationStrategy;
		createNewArray(arraySize);
		factory = new SortingFactory(false); // Do not use a default value for the algorithm name.
	}
	
	
	/**
	 * Sets the array and populates it according to the population strategy.
	 * @param size
	 */
	protected void createNewArray(int size) {
		arr = new int[size];
		populateArray();
	}
	
	
	/**
	 * Changes the size of the array.
	 * @param newSize
	 */
	public void changeArraySize(int newSize) {
		createNewArray(newSize);
	}
	
	
	/**
	 * Populates the array according to the variable "populationStrategy," defaulting to "random."
	 */
	protected void populateArray() {
		if (populationStrategy.contains("increasing")) {
			populateArrayIncreasing();
		}
		// else
		populateArrayRandomly();
	}
	
	
	/**
	 * Populates the array so that the values are sorted order (increasing).
	 */
	protected void populateArrayIncreasing() {
		Random r = new Random();
		int limit = (Integer.MAX_VALUE) / arr.length;
		arr[0] = Integer.MIN_VALUE + r.nextInt(limit);
		for (int i = 1; i < arr.length; i++) {
			arr[i] = arr[i-1] + r.nextInt(limit);
		}
	}
	
	
	/**
	 * Populates the array with random values.
	 */
	protected void populateArrayRandomly() {
		Random r = new Random();
		for (int i = 0; i < arr.length; i++) {
			arr[i] = r.nextInt(100);
		}
	}
	
	
	/**
	 * Checks whether the array is sorted.
	 * @return true if sorted; false otherwise.
	 */
	protected boolean isSorted(int [] arr) {
		for (int i = 0; i < arr.length-1; i++) {
			if (arr[i] > arr[i+1])
				return false;
		}
		return true;
	}
	
	
	/**
	 * Prints the array size and whether sorted or not.
	 */
	public void printStatus() {
		System.out.print(arr.length + "\t");
		if (isSorted(arr))
			System.out.println("[OK]");
		else
			System.out.println("[XX] -- not sorted");
	}
	
	
	/**
	 * Prints the array size and whether sorted or not.
	 * @param The array to be considered.
	 */
	public void printStatus(int [] arr) {
		System.out.print(arr.length + "\t");
		if (isSorted(arr))
			System.out.println("[OK]");
		else
			System.out.println("[XX] -- not sorted");
	}
	
	
	/**
	 * Makes a copy of the array. This helps to compare sorting algorithms.
	 * @return a copy of the internal array.
	 */
	public int [] copyArray() {
		int [] copy = new int[arr.length];
		System.arraycopy(arr, 0, copy, 0, arr.length);
		return copy;
	}
	
	
	/**
	 * Iterates over the array variable "algorithms", instantiates each and determines the timing.
	 * Sends that to stdout. 
	 */
	public void printSortingTiming() {
		// Objective:
		// 1) Feed "algorithms" variable to the factory in order to get a sorting algorithm:
		for (String algo : algorithms) {
			try {
				SortingAlgorithm sort = factory.getSortingAlgorithm(algo);
				System.out.print(algo + "\t");
				// For each algorithm:
				// a) Copy the array
				int [] copy = copyArray();
				// b) Have the algorithm sort the copy ... while timing it.
				long start = System.currentTimeMillis();
				sort.sort(copy);
				long total_time = System.currentTimeMillis() - start;
				System.out.print(total_time + " ms.\t");
				if (total_time < 1000) {
					System.out.print("\t");
				}
				// c) Check for correctness
				printStatus(copy);
			}
			catch (Exception e) {
				System.out.println("Unable to instantiate sorting algorithm " + algo);
			}
		}
	}

	
	/**
	 * Tries to sort with different array sizes
	 * @param args
	 */
	public static void main(String[] args) {
		
		Test timing = new Test();
		int [] sizes = {50000, 100000, 150000, 200000, 250000, 300000};
		
		for (int size : sizes) {
			timing.changeArraySize(size);
			timing.printSortingTiming();
			System.out.println("----------------------------------------------------");
		}

	}

}