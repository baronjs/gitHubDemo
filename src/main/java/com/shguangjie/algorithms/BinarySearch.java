/*
 * Copyright (c) 2019 Vast Pure Technology(广洁科技). All rights reserved.
 *
 */
package com.shguangjie.algorithms;

import java.util.Arrays;

/**
 * <h1>二分查找算法</h1>
 * 思想：二分查找是一种非常高效的算法，又称之为折半查找，顾名思义就是将查找的值和数组的中间值作比较(数组必须有序的)
 * 如果被查找的值小于中间值，就在中间值左侧数组继续查找；如果大于中间值，就在中间值右侧数组中查找；否则中间值就是要找的元素。
 * 二分查找的优缺点：
 * 优点是比较次数少，查找速度快，平均性能好；
 * 其缺点是要求待查表为有序表，且插入删除困难。
 * 因此，折半查找方法适用于不经常变动而查找频繁的有序列表。
 * 时间复杂度：O(log2n)
 *
 * @author baronjs
 * @since JDK1.8.0.181
 */
public class BinarySearch {

    private BinarySearch() {
    }

    private static int indexOf(int[] a, int key) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

    @Deprecated
    public static int rank(int key, int[] a) {
        return indexOf(a, key);
    }

    public static void main(String[] args) {
        int[] whitelist = new int[]{23, 50, 10, 99, 18, 23, 98, 84, 11, 10, 48, 77, 13, 54, 98, 77, 77, 68};
        // 排序
        Arrays.sort(whitelist);
        System.out.println(Arrays.toString(whitelist));
        int key = 11;
        System.out.println(BinarySearch.indexOf(whitelist,key));
        System.out.println(Arrays.binarySearch(whitelist,key));
    }
}