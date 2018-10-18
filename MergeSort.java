/*
 *@author Cori Posadas
*/

public class MergeSort implements SortingAlgorithm {

	public void sort(int [] array){

		mergeSort(array);
	}

	public void mergeSort(int [] arr){
		if (arr.length < 2){
			return;
		}
		int mid = arr.length / 2;

		int [] leftArr = new int [mid];
		for (int i = 0; i < mid; i++){
			leftArr[i] = arr[i];
		}

		int [] rightArr = new int [arr.length - mid];
		for (int i = mid; i < arr.length; i++){
			rightArr[i - mid] = arr[i];
		}

		mergeSort (leftArr); 
		mergeSort(rightArr);

		merge (arr, leftArr, rightArr);
	}

	public void merge(int [] targetArr, int [] leftArr, int [] rightArr){

		int left = 0;
		int right = 0;
		int target = 0;

		while (left < leftArr.length && right < rightArr.length) {
			if (leftArr[left] < rightArr[right]){
				targetArr[target++] = leftArr[left++];
			}
			else {
				targetArr[target++] = rightArr[right++];
			}
		}
		while (left < leftArr.length){
			targetArr[target++] = leftArr[left++];
		}
		while (right < rightArr.length){
			targetArr[target++] = rightArr[right++];
		}
	}
}
