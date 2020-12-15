import java.util.Arrays;

public class ThreeSum {
    public static void main(String args[]) {
        var input = new int[]{1, 3, -4, 5, 6, -7, 8, 9};
        System.out.println(GetThreeSumResult(input));
    }

    public static int GetThreeSumResult(int[] arr) {
        Arrays.sort(arr);
        var result = 0;
        for (var i = 0; i < arr.length - 2; i++) {
            var b = i + 1;
            var c = arr.length - 1;
            while (b < c) {
                var sum = arr[i] + arr[b] + arr[c];
                if (sum == 0) {
                    result++;
                    c--;
                } else if (sum > 0)
                    c--;
                else
                    b++;
            }
        }
        return result;
    }

}
