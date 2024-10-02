package ru.vsu.amm.java;



class Solution {
public int searchInsert(int[] nums, int target) {
    if (nums.length == 0) {
        return 0;
    }
    int left = 0;
    int right = nums.length - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    return left;
}

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums1 = {1, 3, 5, 6};
        int target1 = 5;
        int result1 = solution.searchInsert(nums1, target1);
        System.out.println("Пример 1: " + result1); // Output: 2

        int[] nums2 = {1, 3, 5, 6};
        int target2 = 2;
        int result2 = solution.searchInsert(nums2, target2);
        System.out.println("Пример 2: " + result2); // Output: 1

        int[] nums3 = {1, 3, 5, 6};
        int target3 = 7;
        int result3 = solution.searchInsert(nums3, target3);
        System.out.println("Пример 3: " + result3); // Output: 4
    }
}