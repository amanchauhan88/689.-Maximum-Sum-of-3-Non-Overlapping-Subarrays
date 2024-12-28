import java.util.Arrays;

class Solution {
    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        int n = nums.length;
        int[] sum = new int[n + 1]; // prefix sum
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + nums[i];
        }

        int[] left = new int[n]; // best starting index for first interval
        int[] right = new int[n]; // best starting index for third interval

        // Populate the left array
        int maxLeftSum = sum[k] - sum[0];
        left[k - 1] = 0;
        for (int i = k; i < n; i++) {
            if (sum[i + 1] - sum[i + 1 - k] > maxLeftSum) {
                maxLeftSum = sum[i + 1] - sum[i + 1 - k];
                left[i] = i + 1 - k;
            } else {
                left[i] = left[i - 1];
            }
        }

        // Populate the right array
        int maxRightSum = sum[n] - sum[n - k];
        right[n - k] = n - k;
        for (int i = n - k - 1; i >= 0; i--) {
            if (sum[i + k] - sum[i] >= maxRightSum) {
                maxRightSum = sum[i + k] - sum[i];
                right[i] = i;
            } else {
                right[i] = right[i + 1];
            }
        }

        // Find the best middle interval
        int[] result = new int[3];
        int maxTotalSum = 0;
        for (int i = k; i <= n - 2 * k; i++) {
            int leftIndex = left[i - 1];
            int rightIndex = right[i + k];
            int currentTotalSum = (sum[i + k] - sum[i]) + (sum[leftIndex + k] - sum[leftIndex]) + (sum[rightIndex + k] - sum[rightIndex]);

            if (currentTotalSum > maxTotalSum) {
                maxTotalSum = currentTotalSum;
                result[0] = leftIndex;
                result[1] = i;
                result[2] = rightIndex;
            }
        }

        return result;
    }

}
