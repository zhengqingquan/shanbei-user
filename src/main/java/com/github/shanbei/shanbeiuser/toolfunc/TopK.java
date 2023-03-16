package com.github.shanbei.shanbeiuser.toolfunc;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Top-K算法:可以在大数据集中找到最大或最小的K个元素。
 * 使用优先级队列PriorityQueue，而PriorityQueue底层为最小堆（Min-Heap）数据结构来实现的。
 *
 * @author zhengqingquan
 */
public class TopK {

    public static int[] topK(int[] nums, int k) {
        // 定义优先队列充当最小堆
        PriorityQueue<Integer> queue = new PriorityQueue<>(k);
        // 遍历数组
        for (int num : nums) {
            if (queue.size() < k) {
                // 保证堆中永远都有且只有k个元素
                queue.offer(num);
            } else {
                // 与堆顶元素比较进行更换
                if (num > queue.peek()) {
                    // 该方法会检索并删除PriorityQueue队列的头部元素，并将其作为方法的返回值返回。
                    // 如果队列为空，则该方法返回null。
                    queue.poll();
                    // 添加指定的元素到队列末尾，并返回true。如果队列已满，则返回false。
                    queue.offer(num);
                }
            }
        }

        // 出队将结果放入数组中返回
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = queue.poll();
        }
        return result;
    }

    public static void main(String[] args) {
        // 定义模拟数据
        int[] nums = {3, 5, 1, 7, 8, 2, 9, 4, 6};
        // 定义需要找的top k
        int k = 9;
        // 拿到top k数组
        int[] result = topK(nums, k);
        System.out.println(Arrays.toString(result));
    }
}