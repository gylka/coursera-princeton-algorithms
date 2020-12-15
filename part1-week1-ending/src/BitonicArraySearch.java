public class BitonicArraySearch {
    public static void main(String args[]) {
        var input = new int[] {1, 4, 6, 7, 8, 10, 12, 16, 14, 9, 8};
        System.out.println(getIndexOfBitonicMax(input, 0, input.length - 1));
        System.out.println(bitonicArraySearch(input, 6));
        System.out.println(bitonicArraySearch(input, 14));
    }

    static int bitonicArraySearch(int[] array, int value) {
        var indexOfMax = getIndexOfBitonicMax(array, 0, array.length - 1);
        if(value == array[indexOfMax])
            return indexOfMax;
        var index = binarySearch(array, value, 0, indexOfMax - 1, true);
        if (index != -1)
            return index;
        return binarySearch(array, value, indexOfMax + 1, array.length - 1, false);
    }

    static int getIndexOfBitonicMax(int[] array, int lower, int upper) {
        if (lower == upper)
            return lower;
        var middle = (lower + upper) / 2;
        if (array[middle] > array[middle + 1])
            return getIndexOfBitonicMax(array, lower, middle);
        return getIndexOfBitonicMax(array, middle + 1, upper);
    }

    static int binarySearch(int[] array, int value, int lower, int upper, boolean isBeforeMax) {
        while (lower <= upper) {
            var middle = lower + (upper - lower) / 2;
            if (array[middle] == value)
                return middle;
            if ((isBeforeMax && (array[middle] > value)) || (!isBeforeMax && (array[middle] < value)))
                upper = middle - 1;
            else
                lower = middle + 1;
        }
        return -1;
    }
}
