public class SortDoubleArray {

    public static void quickSort(double[] arr, int left, int right) {
        if (left < right) {
            int partitionIndex = partition(arr, left, right);
            quickSort(arr, left, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, right);
        }
    }

    private static int partition(double[] arr, int left, int right) {
        double pivot = arr[right];
        int i = left - 1;
        for (int j = left; j < right; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, right);
        return i + 1;
    }

    private static void swap(double[] arr, int i, int j) {
        double temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void sort(double[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }
    public static void sortMultipleArrays(double[]... arrays){
        for (double[] arr: arrays) {
            sort(arr);
        }
        System.out.println("All arrays sorted!");
    }
}
