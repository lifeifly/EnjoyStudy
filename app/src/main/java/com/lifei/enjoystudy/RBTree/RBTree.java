package com.lifei.enjoystudy.RBTree;

public class RBTree<T extends Comparable<T>> {
    //根节点
    private RBNode<T> root;

    private static final boolean RED=false;
    private static final boolean BLACK=true;
    public static class RBNode<T extends Comparable<T>>{
        boolean color;
        T key;
        RBNode<T> left;
        RBNode<T> right;
        RBNode<T> parent;

        public RBNode(boolean color, T key, RBNode<T> left, RBNode<T> right, RBNode<T> parent) {
            this.color = color;
            this.key = key;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        public T getKey() {
            return key;
        }

        @Override
        public String toString() {
            return "RBNode{" +
                    "color=" + color +
                    ", key=" + key +
                    '}';
        }
    }
}
