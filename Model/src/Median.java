public class Median {
    static int w = 3;
    public static void main(String[] args) {
        System.out.println(median(new int[]{1,42,3}));
    }
    public static int median(int []nums){
        int nums2[]  = nums;
        w = nums.length;
        bubbleSort(nums2);
        return nums2[w/2];
    }

    public static void bubbleSort(int[] array) {
        if (array.length <= 1) {
            return;
        }

        int size = array.length;
        for (int i = size - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (array[j] > array[j + 1]) {
                    exchangeElements(array, j, j + 1);
                }
            }
        }
    }
    public static void exchangeElements(int[] array, int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

}
