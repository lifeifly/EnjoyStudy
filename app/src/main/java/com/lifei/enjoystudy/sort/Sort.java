package com.lifei.enjoystudy.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Sort {
    /**
     * 冒泡排序
     *
     * @param arr
     */
    public static void sortBubble(int[] arr) {
        int n = arr.length;
        //第一层控制比较次数,只需比较n-1次
        for (int i = 0; i < n - 1; i++) {
            //第二层控制两两比较,获取最大值就放最后,因为是j与j+1比较，需要多减1
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    /**
     * 选择排序
     *
     * @param arr
     */
    public static void selectSort(int[] arr) {
        //外层控制需要确定的位置
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    swap(arr, i, j);
                }
            }
        }
    }

    /**
     * 插入排序,认为前0..i是有序的，然后从i+1开始插入，然后不断比较排序
     *
     * @param arr
     */
    public static void insertSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {//外层控制第几个需要插入的数
            for (int j = i; j > 0; j--) {
                if (arr[j] < arr[j-1]) {
                    swap(arr, j, j-1);
                }
            }
        }
    }

    /**
     * 希尔排序：每隔一个距离的元素作为一组，最后的KNUTH算法距离h=3h+1
     * @param arr
     */
    public static void shellSort(int[] arr){
        //求最大h，3h+1=mArr.length()
        int h = 1;
        while (3 * h + 1 <= arr.length) {
            h = 3 * h + 1;
        }
        for (int gap = h; gap > 0; gap = (gap - 1) / 3) {//外层循环间隔
            for (int i = gap; i < arr.length; i += gap) {//外层循环需要插入的元素
                for (int j = i; j > 0; j -= gap) {//内层循环比较和交换
                    if (arr[j - gap] > arr[j]) {
                        swap(arr, j - gap, j);
                    }
                }

            }
        }
    }
    /**
     * 快排：先再中间找一个轴，小于轴的放左边，大于轴的放右边，然后再分辨对轴的两边进行此操作
     * @param arr
     * @param left
     * @param right
     */
    public static void quickSort(int[] arr,int left,int right){
        if (left>=right)return;;

        int p=quickRecursiveSort(arr,left,right);
        quickSort(arr,left,p-1);
        quickSort(arr,p+1,right);
    }
    public static int quickRecursiveSort(int[] arr,int left,int right){
        //以right为轴
        int pivot=arr[right];
        //荷兰国旗
        int small=left-1;//小于区
        int big=right;//大于区
        int index=left;
        while (index<big) {
            if (arr[index]>pivot){
                //与大于区的前一个位置交换
                swap(arr,index,--big);
            }else if (arr[index]<pivot){
                //与小于区的后一个位置交换
                swap(arr,index,++small);
                index++;
            }else {
                index++;
            }
        }
        //将pivot移到对应的位置,
        swap(arr,big,right);
        return big;
    }

    /**
     * 归并排序：将数组分成两部分，分别排序，排序后再对这两部分进行归并
     * @param arr
     * @param left
     * @param right
     */
    public static void mergeSort(int[] arr,int left,int right){
        mergeRecursiveSort(arr,left,right);
    }

    private static void mergeRecursiveSort(int[] arr, int left, int right) {
        if (left>=right)return;

        //1.计算出中间位置
        int middle = (left + right) / 2;
        //2.对左边进行排序
        mergeRecursiveSort(arr, left, middle);
        //3.对右边进行排序
        mergeRecursiveSort(arr,middle+1,right);
        //4.合并
        merge(arr,left,middle,right);
    }

    private static void merge(int[] arr, int left, int middle, int right) {
        int[] result = new int[right - left + 1];
        int start = left;
        int end = middle + 1;
        int index = 0;
        while (start<=middle&&end<=right){
            if (arr[start]<=arr[end]){
                result[index++]=arr[start];
                start++;
            }else {
                result[index++] = arr[end];
                end++;
            }
        }
        //左边还没比较完
        if (start<=middle){
            while (start <= middle) {
                result[index++] = arr[start++];
            }
        }
        //右边未比较完
        if (end <= right) {
            while (end <= right) {
                result[index++] = arr[end++];
            }
        }
        //将排好序的结果数组复制到原数组
        for (int i = 0, j = left; i < result.length; i++, j++) {
            arr[j] = result[i];
        }
    }

    /**
     * 堆排序:将数组作为一个小根堆，每次组成小根堆后，就将左边界往后移
     * leftChild=n*2+1
     * rightChild=n*2+2
     *
     * 结构中最后一个带有孩子的节点的下标为(（start+end）/2)向上取整-1
     */
    public static void heapSort(int[] arr){
        for (int i = arr.length-1; i >0 ; i--) {
            buildHeapAndSwap(arr,i);
        }
    }

    private static void buildHeapAndSwap(int[] arr, int end) {
        //最后一个父节点
        int lastFather=(0+end)%2==0?(0+end)/2-1:(0+end)/2;
        //从最后一个父节点往前枚举每个父节点
        for (int i = lastFather; i >=0 ; i--) {
            //右子节点
            int rightChild=i*2+2;
            if (rightChild<=end){
                if (arr[rightChild]>arr[i]){
                    swap(arr,rightChild,i);
                }
            }
            int leftChild=i*2+1;
            if (arr[leftChild]>arr[i]){
                swap(arr,leftChild,i);
            }
        }
        swap(arr,0,end);
    }

    /**
     * 计数排序:对一个范围内数字进行排序，具体的数字作为下标，数字的个数作为元素
     * @param arr
     */
    public static int[] calSort(int[] arr){
        //[0,10000)范围进行排序
        int[] result = new int[arr.length];
        //1.创建一个10000个长度的数组
        int[] bucket = new int[arr.length];
        //2.遍历所有元素，将每个数字对应桶的元素相加
        for (int i = 0; i < arr.length; i++) {
            bucket[arr[i]]++;
        }
        //3.保证排序是稳定的，需要进行累加数组,从下标1的元素开始，arr[i]=arr[i]+arr[i-1],这样对应的元素-1就是每种数字最后一个数字的下标
        for (int i = 1; i < bucket.length; i++) {
            bucket[i] = bucket[i] + bucket[i - 1];
        }
        //4.倒序放置每个元素
        for (int i = arr.length-1; i >=0; i--) {
            result[--bucket[arr[i]]]=arr[i];
        }
        return result;
    }
    /**
     * 基数排序,先按个位数进行计数排序，再按十位，再按百位等等
     */
    public int[] baseSort(int[] arr) {
        //1.找到最大数，取最高位
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        //用来存放各个位计数排序的桶,位数只有0-9，所有桶的容量为10
        int[] bucket = new int[10];
        //临时结果数组
        int[] result = new int[10000];
        //2.以位数为循环数，进行外层循环
        for (int i = 0; i < String.valueOf(max).length(); i++) {
            System.out.println(Arrays.toString(arr) + "------------");
            //先除divisor再%10得到的就是各个位的值
            int divisor = (int) Math.pow(10, i);
            System.out.println(Arrays.toString(bucket));
            //获取各个数的位数，放到不同的桶里
            for (int j = 0; j < arr.length; j++) {
                int index = arr[j] / divisor % 10;
                bucket[index]++;
            }
            System.out.println(Arrays.toString(bucket));
            //累加数组，为了稳定
            for (int j = 1; j < bucket.length; j++) {
                bucket[j] = bucket[j] + bucket[j - 1];
            }
            System.out.println(Arrays.toString(bucket));
            //倒序放置
            for (int j = arr.length - 1; j >= 0; j--) {
                int index = arr[j] / divisor % 10;
                result[--bucket[index]] = arr[j];
            }
            System.out.println(Arrays.toString(result));
            //完成一轮计数排序，情空数组,将临时数组copy到结果数组
            Arrays.fill(bucket, 0);
            System.arraycopy(result, 0, arr, 0, result.length);
        }
        return result;
    }

    public static void bubbleSort1(int[] arr){
        //每次从前往后两两比较，找出最大值放在后面，总共需要循环n-1次
        for (int i=0;i<arr.length-1;i++){
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j+1]>arr[j]){
                    swap(arr,j,j+1);
                }
            }
        }
    }

    /**
     * 堆排序，每次构建一个大根堆，然后把最大数放到最后
     * @param arr
     */
    public static void heapSort1(int[] arr){
        for (int i = arr.length-1; i >0 ; i--) {
            buildHeapAndSort(arr, i);
        }
    }

    private static void buildHeapAndSort(int[] arr, int right) {
        //最后一个父节点位置
        int lastFatherIndex=(0 +right)%2==0?((0 +right)/2-1):((0 +right)/2);
        for (int i = lastFatherIndex; i >=0 ; i--) {
            int rightChildIndex=i*2+2;
            if (rightChildIndex<=right&&arr[rightChildIndex]>arr[i]){
                swap(arr,rightChildIndex,i);
            }
            int leftChildIndex=i*2+1;
            if (arr[leftChildIndex]>arr[i]){
                swap(arr,leftChildIndex,i);
            }
        }
        swap(arr, 0,right);
    }

    private static void topK(){

    }
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 7, 2, 5, 9, 8,0,4,6};
        heapSort1(arr);
        System.out.println(Arrays.toString(arr));
    }
}
