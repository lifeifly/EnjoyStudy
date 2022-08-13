package com.lifei.enjoystudy.graph;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * 并查集结构
 */
public class UnionFind {
    /**
     * 将value包装成代表点
     * @param <V>
     */
    public static class Node<V>{
        V value;

        public Node(V value) {
            this.value = value;
        }
    }
    public static class UnionSet<V>{
        //key：值 value:包装点,初始化后不改变
        public HashMap<V,Node<V>> nodes;
        //key：包装点 value：根节点
        public HashMap<Node<V>,Node<V>> parents;
        //key:代表点 value:代表所在集合的节点大小
        public HashMap<Node<V>,Integer> sizeMap;

        public UnionSet(List<V> values){
            for (V value:values){
                Node<V> node=new Node<V>(value);
                nodes.put(value,node);
                parents.put(node,node);
                sizeMap.put(node,1);
            }
        }

        /**
         * 从cur一直往上找到不能再找的代表点
         * @param cur
         * @return
         */
        public Node<V> findFather(Node<V> cur){
            Stack<Node<V>> path=new Stack<>();
            while(cur!=parents.get(cur)){
                path.push(cur);
                cur=parents.get(cur);
            }
            //将不是直接挂在代表点的节点直接挂在代表点上
            while(!path.isEmpty()){
                parents.put(path.pop(),cur);
            }
            return cur;
        }

        /**
         * 查询a、b是否再一个集合
         * @param a
         * @param b
         * @return
         */
        public boolean isSameSet(V a,V b){
            if (!nodes.containsKey(a)||!nodes.containsKey(b)){
                return false;
            }
            return findFather(nodes.get(a))==findFather(nodes.get(b));
        }

        /**
         * 将a、b两个集合合并
         * @param a
         * @param b
         */
        public void union(V a,V b){
            if (!nodes.containsKey(a)||!nodes.containsKey(b)){
                return;
            }
            Node<V> aHead=findFather(nodes.get(a));
            Node<V> bHead=findFather(nodes.get(b));
            if (aHead!=bHead){
                //不属于一个集合
                int aSetSize=sizeMap.get(aHead);
                int bSetSize=sizeMap.get(bHead);
                //小的挂在大的集合下面
                if (aSetSize>=bSetSize){
                    parents.put(bHead,aHead);
                    sizeMap.put(aHead,aSetSize+bSetSize);
                    sizeMap.remove(bHead);
                }else {
                    parents.put(aHead,bHead);
                    sizeMap.put(bHead,aSetSize+bSetSize);
                    sizeMap.remove(aHead);
                }
            }
        }
    }
}
