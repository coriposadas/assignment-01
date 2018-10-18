/*
 *@author Cori Posadas
*/

public class RunSort implements SortingAlgorithm {

	public void sort(int [] array){

		runSort(array, 16);
	}
 
	/*
	* An in-place sorting method.
	*/
	public void insertionSort(int [] arr, int start, int end){

		for(int i = start + 1; i <= end; i++){
			int temp = arr[i];
			int k = i - 1;
			while (k >= start && arr[k] > temp){
				arr[k+1] = arr[k];
				--k;
			}
			arr[k+1] = temp;
		}
	}

	/*
	* A method to merge two arrays in sorted order.
	*/
	public void merge(int [] targetArr, int [] leftArr, int [] rightArr, int startPos, int leftSize, int rightSize){

			int left = 0;
			int right = 0;
			int target = startPos;

			while (left < leftSize && right < rightSize) {
				if (leftArr[left] < rightArr[right]){
					targetArr[target++] = leftArr[left++];
				}
				else {
					targetArr[target++] = rightArr[right++];
				}
			}
			while (left < leftSize){
				targetArr[target++] = leftArr[left++];
			}
			while (right < rightSize){
				targetArr[target++] = rightArr[right++];
			}
		}

	/*
	 *A method to find a run in the array and returns the run's start index and length.
	*/
	public int [] findRun(int pos, int [] arr, int runSize){

		int [] info = new int [2];		//Array to contain start index and length of run
		int i = pos;
		int count = 0;

		while (i <= arr.length - 1){
			count = 0; 											
			while (i < arr.length - 1 && arr[i] < arr[i+1]){	//Goes through the entire array and increments count if found an ascending group of numbers
				count++;
				i++;
			}
			i++;
		}

		info[0] = i - (count + 1);		//Start index
		info[1] = count + 1;			//Length of run 

		return info;
	}

	/*
	 * A method that returns an array of sorted sequences of runSize or smaller
	 * that were outside the runs already found in the array.
	 * Start = initial position to create for the new runs
	 * End = position before start of run
	 */
	public void sliceAndSort(int start, int end, int [] arr, int runSize){

		int sliceStart;
		int sliceEnd = start - 1;		//Makes sure that the start starts at the start position passed in

		while (sliceEnd <= end) {
			sliceStart = sliceEnd + 1;
			sliceEnd = sliceStart + runSize - 1;
			insertionSort(arr, sliceStart, Math.min(sliceEnd, end));	//Takes the minimum of sliceEnd and end because we increment by runSize
		}
	}

	/*
	 * A method to find natural runs in the array.
	 * Sorts sequences outside of these natural runs with insertion sort.
	 * Merges subsequent runs.
	*/  
	public void runSort(int [] arr, int runSize) { 

		int [] runs = new int [2];
		int findStart = 0;

		while (findStart < arr.length){
			if (findStart + runSize < arr.length){
				runs = findRun(findStart, arr, runSize);	//Finds the runs
			}
			else {											//If no runs are found, the entire array is passed in to sliceAndSort to create runs
				runs[0] = arr.length;
				runs[1] = arr.length - findStart;
			}
			sliceAndSort(findStart, runs[0] -1, arr, runSize);
			findStart = runs[0] + runs[1];
		}

		int starter = 0;
		int i = 0;
		int j = 0;
		int k = 0;
		int [] leftArr = new int [arr.length];
		int [] rightArr = new int [arr.length];

		while (j < arr.length - 1){		//If the entire array is in leftArr, the quit out of the loop
			while (i < arr.length){
				while (i < arr.length){
					leftArr[j] = arr[i];	//Starts to fill the array first
					j++;

					if (i < arr.length - 1 && arr[i] <= arr[i+1]){		//Then checks if the following element is larger
						i++;											//If it is, continue
					}
					else {												//If it is not, then break out of the loop
						break;
					}
				}

				i++;

				while (i < arr.length){ 
					rightArr[k] = arr[i];
					k++;

					if (i < arr.length -1 && arr[i] < arr[i+1]){
						i++;
					}
					else {
						break;
					}
				}

				i++;

				merge (arr, leftArr, rightArr, starter, j, k);

				if (j < arr.length - 1){							//Reset leftArr and rightArr to start at 0 if we still have to merge runs
					j = 0;
					k = 0;
				}

				starter = i;
			}
			
			if (j == 0){											//Reset where to grab the elements in the main array
				i = 0;
				starter = 0;
			}
		}
	}
}

