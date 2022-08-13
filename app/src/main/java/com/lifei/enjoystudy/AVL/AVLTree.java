package com.lifei.enjoystudy.AVL;

/**
 * 平衡二叉树，是一颗二叉排序树，两个子树的高度差的绝对值不超过1
 */
public class AVLTree {

    public static class Node{
        int data;

        Node left;
        Node right;
        int height;

        public Node(int data) {
            this.data = data;
        }
    }
    public static int getHeight(Node p){
        return p==null?-1:p.height;
    }

    public static Node insert(Node root,int data){
        if (root==null){
            root=new Node(data);
            return root;
        }
        if (data<=root.data){
            root.left=insert(root.left,data);
            //平衡调整
            if (getHeight(root.left)-getHeight(root.right)>1){
                if (data<=root.left.data){//插入节点再失衡节点的左子树的左边
                    root=LLRotate(root);
                }else {//插入节点再失衡节点的左子树的右边
                    root=LRRotate(root);
                }
            }
        }else {
            root.right=insert(root.right,data);
            //平衡调整
            if (getHeight(root.left)-getHeight(root.right)>1){
                if (data<=root.right.data){//插入节点再失衡节点的右子树的左边
                    root=RLRotate(root);
                }else {//插入节点再失衡节点的子树的右边
                    root=RRRotate(root);
                }
            }
        }
        root.height=Math.max(getHeight(root.left),getHeight(root.right))+1;
        return root;
    }

    public static Node LRRotate(Node p){
        p.left=RRRotate(p.left);
        return LLRotate(p);
    }

    private static Node RLRotate(Node p) {
        p.right=LLRotate(p.right);
        return RRRotate(p);
    }

    /**
     * LL旋转
     *     30            20
     *    / \            / \
     *   20  40  ->     10 30
     *   / \            /  / \
     *  10 25          5  25 40
     *  /
     * 5
     */
    private static Node LLRotate(Node p) {
        Node lsubtree=p.left;//失衡点的左子树的根节点20作为新的节点
        p.left=lsubtree.right;//将新节点的右子树25作为失衡点30的左子树
        lsubtree.right=p;//将失衡点30作为新节点的右子树
        //重新设置失衡点30和新节点20的高度
        p.height=Math.max(getHeight(p.left),getHeight(p.right))+1;
        lsubtree.height=Math.max(getHeight(lsubtree.left),getHeight(lsubtree.right))+1;
        return lsubtree;
    }
    /**
     * RR旋转
     *     20            30
     *    / \            / \
     *   10  30  ->     20 40
     *       / \       / \   \
     *      25 40     10 25  50
     *          \
     *          50
     */
    private static Node RRRotate(Node p) {
        Node rsutree=p.right;
        p.right=rsutree.left;
        rsutree.left=p;
        p.height=Math.max(getHeight(p.left),getHeight(p.right))+1;
        rsutree.height=Math.max(getHeight(rsutree.left),getHeight(rsutree.right))+1;
        return rsutree;
    }


}
