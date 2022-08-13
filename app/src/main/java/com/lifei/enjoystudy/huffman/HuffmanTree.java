package com.lifei.enjoystudy.huffman;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HuffmanTree {
    public static class Node<E> {
        E data;//数据
        int weight;//权值
        Node<E> left;
        Node<E> right;
        String code;//huffman编码
        public Node(E data, int weight) {
            this.data = data;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    ", weight=" + weight +
                    '}';
        }
    }

    /**
     * 构成哈夫曼树：带权路径和最小，左节点的权重小于等于右节点的权重
     * <p>
     * 1.每次将权重最小的两个节点合并
     * 2.
     */
    public static <E> Node<E> createHuffman(List<Node<E>> list) {
        NodeComparator<E> comparator=new NodeComparator<>();
        //如果集合中有两个及以上的节点
        while (list.size() > 1) {
            Collections.sort(list, comparator);
            Node<E> left=list.get(0);
            Node<E> right=list.get(1);
            Node<E> parent=new Node<>(null,left.weight+right.weight);
            parent.left=left;
            parent.right=right;

            list.remove(0);
            list.remove(0);

            list.add(parent);
        }
        return list.get(0);
    }

   public static class NodeComparator<E> implements Comparator<Node<E>>{

       @Override
       public int compare(Node<E> o1, Node<E> o2) {
           return o1.weight-o2.weight;
       }
   }
}
